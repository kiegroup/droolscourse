package cost;

public class HandlingCostElement implements CostElement {

    private double amount;
    private City city;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }


    @Override
    public String toString() {
        return "HandlingCostElement [amount=" + amount + ", city=" + city + "]";
    }


}
