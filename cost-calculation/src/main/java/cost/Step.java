package cost;

public class Step {
    public static int Ship_TransportType = 1;
    public static int train_TransportType = 2;
    public static int truck_TransportType = 3;

    private City stepStart;
    private City stepEnd;
    private double distance;
    private int transportType;


    public Step(City stepStart, City stepEnd, double distance, int transportType) {
        super();
        this.stepStart = stepStart;
        this.stepEnd = stepEnd;
        this.distance = distance;
        this.transportType = transportType;
    }

    public City getStepStart() {
        return stepStart;
    }

    public void setStepStart(City stepStart) {
        this.stepStart = stepStart;
    }

    public City getStepEnd() {
        return stepEnd;
    }

    public void setStepEnd(City stepEnd) {
        this.stepEnd = stepEnd;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getTransportType() {
        return transportType;
    }

    public void setTransportType(int transportType) {
        this.transportType = transportType;
    }

    @Override
    public String toString() {
        return "Step [stepStart=" + stepStart + ", stepEnd=" + stepEnd + ", distance=" + distance + ", transportType="
                + transportType + "]";
    }


}
