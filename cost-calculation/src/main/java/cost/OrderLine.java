package cost;

public class OrderLine {

    private int numberItems;
    private double weight;
    private Product product;


    public OrderLine(int numberItems, Product product) {
        super();
        this.numberItems = numberItems;
        this.product = product;
    }

    public OrderLine(double weight, Product product) {
        super();
        this.weight = weight;
        this.product = product;
    }

    public int getNumberItems() {
        return numberItems;
    }

    public void setNumberItems(int numberItems) {
        this.numberItems = numberItems;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "OrderLine [numberItems=" + numberItems + ", weight=" + weight + ", product=" + product + "]";
    }

}
