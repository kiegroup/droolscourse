package cost;

public class Product {

    public static int transportType_pallet = 1;
    public static int transportType_individual = 2;
    public static int transportType_bulkt = 3;
    private String name;
    private double height;
    private double width;
    private double depth;
    private double weight;
    private int transportType;


    public Product(String name, double height, double width, double depth, double weight, int transportType) {
        super();
        this.name = name;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.weight = weight;
        this.transportType = transportType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getTransportType() {
        return transportType;
    }

    public void setTransportType(int transportType) {
        this.transportType = transportType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [name=" + name + ", height=" + height + ", width=" + width + ", depth=" + depth + ", weight="
                + weight + ", transportType=" + transportType + "]";
    }


}
