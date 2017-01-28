package cost;

import java.util.HashMap;
import java.util.Map;

public class Pallet {
    private boolean isFull = false;
    private double heightLeft = 2.0;
    private double width = 0.8;
    private double depth = 1.2;
    private Map<Product, Long> contentQuantity = new HashMap<Product, Long>();
    private Map<Product, Double> contentWeight = new HashMap<Product, Double>();
    private int palletType;

    public Map<Product, Long> getContentQuantity() {
        return contentQuantity;
    }

    public Map<Product, Double> getContentWeight() {
        return contentWeight;
    }

    public void addContent(Product product, Long quantity) {
        if (contentQuantity.containsKey(product) == false) {
            contentQuantity.put(product, quantity);
        } else {
            Long oldQuantity = contentQuantity.get(product);
            oldQuantity = oldQuantity + quantity;
        }

    }

    public void addContent(Product product, Double weight) {
        if (contentWeight.containsKey(product) == false) {
            contentWeight.put(product, weight);
        } else {
            Double oldQuantity = contentWeight.get(product);
            oldQuantity = oldQuantity + weight;
        }

    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean isFull) {
        this.isFull = isFull;
    }

    public double getHeightLeft() {
        return heightLeft;
    }

    public void setHeightLeft(double heightLeft) {
        this.heightLeft = heightLeft;
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

    public int getPalletType() {
        return palletType;
    }

    public void setPalletType(int palletType) {
        this.palletType = palletType;
    }

    @Override
    public String toString() {
        return "Pallet [isFull=" + isFull + ", heightLeft=" + heightLeft + ", width=" + width + ", depth=" + depth
                + ", palletType=" + palletType + "]";
    }

}
