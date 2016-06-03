package cost;

public class TransportCostElement implements CostElement {
    private double amount;
    private Step step;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }


    @Override
    public String toString() {
        return "TransportCostElement [amount=" + amount + ", step=" + step + "]";
    }

}
