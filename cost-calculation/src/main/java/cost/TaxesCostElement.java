package cost;

public class TaxesCostElement implements CostElement {
    private double amount;
    private City city;

    private Pallet pallet;

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

    public Pallet getPallet() {
        return pallet;
    }

    public void setPallet(Pallet pallet) {
        this.pallet = pallet;
    }

    @Override
    public String toString() {
        return "TaxesCostElement [amount=" + amount + ", city=" + city + ", pallet="
                + pallet + "]";
    }

}
