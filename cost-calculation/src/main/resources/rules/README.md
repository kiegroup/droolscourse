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

TODO

## Sum the results

TODO
