package cost;

public class LeftToDistribute {

    private OrderLine orderLine;
    private long quantityLeft;
    private double weightLeft;


    public LeftToDistribute(OrderLine orderLine, double weightLeft) {
        super();
        this.orderLine = orderLine;
        this.weightLeft = weightLeft;
    }

    public LeftToDistribute(OrderLine orderLine, long quantityLeft) {
        super();
        this.orderLine = orderLine;
        this.quantityLeft = quantityLeft;
    }

    public OrderLine getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(OrderLine orderLine) {
        this.orderLine = orderLine;
    }

    public long getQuantityLeft() {
        return quantityLeft;
    }

    public void setQuantityLeft(long quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    public double getWeightLeft() {
        return weightLeft;
    }

    public void setWeightLeft(double weightLeft) {
        this.weightLeft = weightLeft;
    }

    @Override
    public String toString() {
        return "LeftToDistribute [orderLine=" + orderLine + ", quantityLeft=" + quantityLeft + ", weightLeft="
                + weightLeft + "]";
    }


}
