package cost;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id;
    private List<OrderLine> orderLines = new ArrayList();


    public Order(String id) {
        super();
        this.id = id;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public List<OrderLine> getOrderLines() {
        return orderLines;
    }


    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }


}
