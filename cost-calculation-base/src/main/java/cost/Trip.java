package cost;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    private String id;
    private List<Step> steps = new ArrayList<Step>();

    public Trip(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Trip [id=" + id + "]";
    }


}
