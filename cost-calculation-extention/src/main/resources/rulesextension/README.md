# Rules

We shall use a Ruleflow and implement the rules in 3 steps : 
* Distribute the products in the pallet
* Calculate the costs
* Calculate and Display the results

This is done in the P1.bpmn2. 

![](ruleflow.png)

## Distribute the products in the pallet

This rules will be executed in the rule step "distribution". So we shall add the keyworruleflow-group "distribution".

There are 3 types of product : those we have to put individually, the bulk and the others.

### Initialize a counter for each order line

First we shall the rule that created the LeftToDistribute object. 
For each orderline we create and link to the orderline element 
```
rule "Create Counter leftToDistribute quantity"
	ruleflow-group "distribution"
    when
  		$elt : OrderLine( numberItems >0 )
  		not (LeftToDistribute(orderLine==$elt))
    then
  	LeftToDistribute e= new LeftToDistribute($elt,$elt.getNumberItems());
  	insert(e);
end
rule "Create Counter leftToDistribute weight"
	ruleflow-group "distribution"
    when
  		$elt : OrderLine( weight  >0 )
  		not (LeftToDistribute(orderLine==$elt))
    then
  	LeftToDistribute e= new LeftToDistribute($elt,$elt.getWeight());
  	insert(e);
end

```
So here the rule above for each OrderLine that is not yet connected to a LeftToDistribute fact. 
Then we create it and initialize the counter with the number of elements in the line.
We have one rule when items are 

### individual items

For each product of type individual, we shall put it on the pallet. 
And in the then part, we create the pallet, decrease the number of element left and update the objects?

```
rule "Create PalletForIndividual"
	ruleflow-group "distribution"
    when
    	$c : CostCalculationRequest()
    	$p : Product(transportType==Product.transportType_individual)
  		$elt : OrderLine(product==$p  )		
  		$l : LeftToDistribute(quantityLeft> 0,orderLine==$elt)
    then
  		Pallet pp = new Pallet();
  		pp.setPalletType($p.getTransportType());
  		pp.addContent($p,new Long(1));
  		insert(pp);
  		$c.getPallets().add(pp);
  		$l.setQuantityLeft($l.getQuantityLeft()-1);
  		update($l);
end
```

### bulk item

For the bulk product, we shall do the same way. 
The first rule files 1400kg on each pallet.
The second rule is if there is less than 1400kg left, then we put the rest in the pallet.

```
rule "Create PalletForBulkMore1400Kg"
	ruleflow-group "distribution"
    when
    	$c : CostCalculationRequest()
    	$p : Product(transportType==Product.transportType_bulkt)
  		$elt : OrderLine(product==$p  )		
  		$l : LeftToDistribute(weightLeft > 1400,orderLine==$elt)
    then
  		Pallet pp = new Pallet();
  		pp.setPalletType($p.getTransportType());
  		pp.addContent($p,new Long(1400));
  		insert(pp);
  		$c.getPallets().add(pp);
  		$l.setWeightLeft($l.getWeightLeft()-1400);
  		update($l);
end
rule "Create PalletForBulkless1400Kg"
	ruleflow-group "distribution"
    when
    	$c : CostCalculationRequest()
    	$p : Product(transportType==Product.transportType_bulkt)
  		$elt : OrderLine(product==$p  )		
  		$l : LeftToDistribute(weightLeft <= 1400,weightLeft > 0,orderLine==$elt)
    then
  		Pallet pp = new Pallet();
  		pp.setPalletType($p.getTransportType());
  		pp.addContent($p,$l.getWeightLeft());
  		insert(pp);
  		$c.getPallets().add(pp);
  		$l.setWeightLeft(0);
  		update($l);
end
```

### Distribute the other product type.
We shall first create a rule that create a new Pallet if none is available

```
rule  "Create Empty Pallet"
	ruleflow-group "distribution"
when
	not (Pallet (palletType == Product.transportType_pallet,full ==false))
then
	Pallet p = new Pallet();
	p.setPalletType(Product.transportType_pallet);
	insert (p);
end

```

As we are going to fill the pallet in height, we shall declare a pallet as full if the place left in height is smaller than the smallest product in height.

```
rule "GetSmallestHeight"
	ruleflow-group "distribution"
    when   
    	accumulate( Product( $h : height ,transportType==Product.transportType_pallet );
                $min : min( $h );
                true )
               
    then
    	CalculatedElement elt = new CalculatedElement();
    	elt.setKey("minValue.product");   	
    	elt.setDoubleValue((Double)$min);
    	insert(elt);
end

```

And then the rule that sets the pallet as filled (full has the true value). We only set the value of a pallet that has been filled by one type of product by adding constranint heightLeft!= 2.0.

```
rule "FillPalletIfNoProductGoesIn"
	ruleflow-group "distribution"
    when   
    	CalculatedElement($min : doubleValue ,key=="minValue.product")
        $p : Pallet(heightLeft!= 2.0,$hl : heightLeft < $min ,full ==false,palletType == Product.transportType_pallet) 
    then
    	$p.setFull(true);
    	System.out.println("MinValue="+$min);
    	update($p);
end

```

Then the rule that fills the pallet with product depending on the size of the product.

```
rule "Create PalletFortransportType_pallet"
	ruleflow-group "distribution"
    when
    	$c : CostCalculationRequest()
    	$pp : Pallet($hl : heightLeft ,full ==false,palletType == Product.transportType_pallet)
    	$p : Product(height <= $hl,transportType==Product.transportType_pallet)
  		$elt : OrderLine(product==$p  )		
  		$l : LeftToDistribute(quantityLeft > 0,orderLine==$elt)
    then
  		long a = (long) Math.round($pp.getWidth()/$p.getWidth());
  		long b = (long) Math.round($pp.getDepth()/$p.getDepth());
  		long n = Math.min(a*b,$l.getQuantityLeft());
  		$pp.addContent($p,n);
  		$pp.setHeightLeft($pp.getHeightLeft()-$p.getHeight());
  		update($pp);
  		$l.setQuantityLeft($l.getQuantityLeft()-n);
  		update($l);
end
```

## Calculate the costs
Now we take each cost and add a CostElement depending on the number of pallets we created in previous step

### Transport cost
For each step type we calculate the cost

```
rule "boatTransportCost"
	ruleflow-group "calculate"
    when
  		$c : CostCalculationRequest(  )
  		$s : Step(transportType ==Step.Ship_TransportType)	
    then
  		TransportCostElement t = new TransportCostElement(); 		
  		t.setStep($s);
  		t.setAmount($c.getPallets().size()*$s.getDistance()*0.2);
  		$c.getCostElements().add(t);
  		insert(t);
end

rule "trainTransportCost"
	ruleflow-group "calculate"
    when
  		$c : CostCalculationRequest(  )
  		$s : Step(transportType ==Step.train_TransportType)  	
    then
  		TransportCostElement t = new TransportCostElement();		
  		t.setStep($s);
  		t.setAmount($c.getPallets().size()*$s.getDistance()*0.5);
  		$c.getCostElements().add(t);
  		insert(t);
end

rule "truckTransportCost"
	ruleflow-group "calculate"
    when
  		$c : CostCalculationRequest(  )
  		$s : Step(transportType ==Step.truck_TransportType)
    then
  		TransportCostElement t = new TransportCostElement();
  		t.setStep($s);
  		t.setAmount($c.getPallets().size()*$s.getDistance()*1.0);
  		$c.getCostElements().add(t);
  		insert(t);
end
```
### Taxes Cost
For each City we go through, we calculate the cost (Shangai and Rotterdam)
As sometimes it depends on the weight, we have to sum the total amount of weigh on all pallets.
Here we chose not to put all calculation in the drools session, so we do the sum in the them part of the rules. 
Se here how we obtain the list of Pallet we want in an ArrayList. In the then part we then iterate on the founded pallet and in the pallet we go through all the products to sum the weight.
```
rule "ShangaiTaxesCost_bulk"
	ruleflow-group "calculate"
    when
  		$c : CostCalculationRequest(  )
  		$ci : City(name==City.ShangaiCityName)
  		$listPallet : ArrayList( )
              from collect(  Pallet(palletType == Product.transportType_bulkt) ) 	
    then
  		TaxesCostElement t = new TaxesCostElement();
  		t.setCity($ci);
  		double totalWeight=0;
  		for (Object oo :  $listPallet){
  			Pallet pp = (Pallet)oo;
  			for (Double d : pp.getContentWeight().values()){
  				totalWeight=totalWeight+d;
  			}
  		}
  		t.setAmount(totalWeight*0.02);
  		$c.getCostElements().add(t);
  		insert(t);
end

rule "ShangaiTaxesCost_notbulk"
	ruleflow-group "calculate"
    when
  		$c : CostCalculationRequest(  )
  		$ci : City(name==City.ShangaiCityName)
  	    $listPallet : ArrayList( )
              from collect(  Pallet(palletType != Product.transportType_bulkt) )
    then
  		TaxesCostElement t = new TaxesCostElement();
  		t.setCity($ci);
  		double totalWeight=0;
  		for (Object oo :  $listPallet){
  			Pallet pp = (Pallet)oo;
  			for (Product p : pp.getContentQuantity().keySet()){
  				totalWeight=totalWeight+p.getWeight()* pp.getContentQuantity().get(p);
  			}
  		}
  		t.setAmount(totalWeight*0.05);
  		$c.getCostElements().add(t);
  		insert(t);
end

rule "RotterdamTaxesCost_bulk_weight"
	ruleflow-group "calculate"
    when
  		$c : CostCalculationRequest(  )
  		$ci : City(name==City.RotterdamCityName)
   		$listPallet : ArrayList( )
              from collect(  Pallet(palletType == Product.transportType_bulkt) ) 	
    then
  		TaxesCostElement t = new TaxesCostElement();
  		t.setCity($ci);
  		double totalWeight=0;
  		for (Object oo :  $listPallet){
  			Pallet pp = (Pallet)oo;
  			for (Double d : pp.getContentWeight().values()){
  				totalWeight=totalWeight+d;
  			}
  		}
  		t.setAmount(totalWeight*0.05);
  		$c.getCostElements().add(t);
  		insert(t);
end
rule "RotterdamTaxesCost_notbulk_weight"
	ruleflow-group "calculate"
    when
  		$c : CostCalculationRequest(  )
  		$ci : City(name==City.RotterdamCityName)
     	$listPallet : ArrayList( )
              from collect(  Pallet(palletType != Product.transportType_bulkt) ) 	
    then
  		TaxesCostElement t = new TaxesCostElement();
  		t.setCity($ci);
  		double totalWeight=0;
  		for (Object oo :  $listPallet){
  			Pallet pp = (Pallet)oo;
  			for (Product p : pp.getContentQuantity().keySet()){
  				totalWeight=totalWeight+p.getWeight()* pp.getContentQuantity().get(p);
  			}
  		}
  		t.setAmount(totalWeight*0.05);
  		$c.getCostElements().add(t);
  		insert(t);
end
```

### Handling Cost and taxes on the Handling
Here we admit that it takes 12 hours to do the handling.
Based on that we then see how many pallets we have to handle per hour and then depending on the what a person can handle per hour, we calculate the number of persons we need.
```
rule "ShangaiHandlingCost"
	ruleflow-group "calculate"
    when
  		$c : CostCalculationRequest(  )
  		$ci : City(name==City.ShangaiCityName)
    then
      	int n = $c.getPallets().size();
  		int nbrePalletPerHour = Math.round(  n/12)+1;
  		int nbrePerson =  Math.round( nbrePalletPerHour/13)+1;
  		HandlingCostElement t = new HandlingCostElement();
  		t.setCity($ci);
  		t.setAmount(nbrePerson*12*20.0);
  		$c.getCostElements().add(t);
  		insert(t);
end

rule "RotterdamHandlingCostAndPersonTaxes"
	ruleflow-group "calculate"
    when
  		$c : CostCalculationRequest(  )
  		$ci : City(name==City.RotterdamCityName)
    then
      	int n = $c.getPallets().size();
  		int nbrePalletPerHour = Math.round(  n/12)+1;
  		int nbrePerson =  Math.round( nbrePalletPerHour/60)+1;
  		HandlingCostElement t = new HandlingCostElement();
  		t.setCity($ci);
  		t.setAmount(nbrePerson*12*45.0);
  		$c.getCostElements().add(t);
  		insert(t);
  		TaxesCostElement t1 = new TaxesCostElement();
  		t1.setAmount(nbrePerson*1.0);
  		$c.getCostElements().add(t1);
  		insert(t1);
end

rule "TournaiHandlingCostAndPersonTaxes"
	ruleflow-group "calculate"
    when
  		$c : CostCalculationRequest(  )
  		$ci : City(name==City.TournaiCityName)
    then
      	int n = $c.getPallets().size();
  		int nbrePalletPerHour = Math.round(  n/12)+1;
  		int nbrePerson =  Math.round( nbrePalletPerHour/40)+1;
  		HandlingCostElement t = new HandlingCostElement();
  		t.setCity($ci);
  		t.setAmount(nbrePerson*12*67.0);
  		$c.getCostElements().add(t);
  		insert(t);
  		TaxesCostElement t1 = new TaxesCostElement();
  		t1.setAmount(nbrePerson*2.0);
  		$c.getCostElements().add(t1);
  		insert(t1);
end

rule "LilleHandlingCostAndPersonTaxes"
	ruleflow-group "calculate"
    when
  		$c : CostCalculationRequest(  )
  		$ci : City(name==City.LilleCityName)
    then
      	int n = $c.getPallets().size();
  		int nbrePalletPerHour = Math.round(  n/12)+1;
  		int nbrePerson =  Math.round( nbrePalletPerHour/30)+1;
  		HandlingCostElement t = new HandlingCostElement();
  		t.setCity($ci);
  		t.setAmount(nbrePerson*12*79.0);
  		$c.getCostElements().add(t);
  		insert(t);
  		TaxesCostElement t1 = new TaxesCostElement();
  		t1.setAmount(nbrePerson*30.0);
  		$c.getCostElements().add(t1);
  		insert(t1);
end
```


## Sum the results
We use the accumulate function to show the results. We just show the result in the console. On a real project, the results could habe stored in a data structure.

```
rule "CalculateTotal"
ruleflow-group "total"
	when
	$c : CostCalculationRequest(  )
	$totalBoatTransport : Number( doubleValue > 100 )
             from accumulate( $s : Step(transportType ==Step.Ship_TransportType) &&
                              TransportCostElement(step ==$s, $value : amount  ),
                              init( double total = 0; ),
                              action( total += $value; ),
                              reverse( total -= $value; ),
                              result( total ) )
	$totalTrainTransport : Number( doubleValue > 100 )
            from accumulate( $s : Step(transportType ==Step.train_TransportType) &&
                            TransportCostElement(step ==$s, $value : amount  ),
                             init( double total = 0; ),
                             action( total += $value; ),
                             reverse( total -= $value; ),
                             result( total ) )
	$totalTruckTransport : Number( doubleValue > 100 )
           from accumulate( $s : Step(transportType ==Step.truck_TransportType) &&
                            TransportCostElement(step ==$s, $value : amount  ),
                            init( double total = 0; ),
                            action( total += $value; ),
                            reverse( total -= $value; ),
                            result( total ) )
	
	
	
	$totalTransport : Number( doubleValue > 100 )
             from accumulate(  TransportCostElement( $value : amount  ),
                              init( double total = 0; ),
                              action( total += $value; ),
                              reverse( total -= $value; ),
                              result( total ) )
    $totalTaxes : Number( doubleValue > 100 )
             from accumulate(  TaxesCostElement( $value : amount  ),
                              init( double total = 0; ),
                              action( total += $value; ),
                              reverse( total -= $value; ),
                              result( total ) )
     $totalHandling : Number( doubleValue > 100 )
             from accumulate(  HandlingCostElement( $value : amount  ),
                              init( double total = 0; ),
                              action( total += $value; ),
                              reverse( total -= $value; ),
                              result( total ) )
	then
		System.out.println("NumberOfPallets="+$c.getPallets().size());
		System.out.println("TotalShipTransport="+$totalBoatTransport);
		System.out.println("TotalTrainTransport="+$totalTrainTransport);
		System.out.println("TotalTruckTransport="+$totalTruckTransport);
		System.out.println("TotalTransport="+$totalTransport);
		System.out.println("TotalTaxes="+$totalTaxes);
		System.out.println("TotalHandling="+$totalHandling);
end;
```

