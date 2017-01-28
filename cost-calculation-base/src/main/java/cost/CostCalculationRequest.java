package cost;

import java.util.ArrayList;
import java.util.List;

public class CostCalculationRequest {
    private Order order;

    private Trip trip;

    private List<Pallet> pallets = new ArrayList<Pallet>();
    private List<CostElement> costElements = new ArrayList<CostElement>();

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public List<Pallet> getPallets() {
        return pallets;
    }

    public void setPallets(List<Pallet> pallets) {
        this.pallets = pallets;
    }

    public List<CostElement> getCostElements() {
        return costElements;
    }

    public void setCostElements(List<CostElement> costElements) {
        this.costElements = costElements;
    }


}
