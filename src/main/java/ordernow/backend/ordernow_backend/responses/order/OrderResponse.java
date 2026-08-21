package ordernow.backend.ordernow_backend.responses.order;

import java.util.List;

import ordernow.backend.ordernow_backend.entities.Order;
import ordernow.backend.ordernow_backend.responses.BaseResponse;

public class OrderResponse extends BaseResponse {
    private List<Order> orderList;

    public OrderResponse(List<Order> orderList, String message) {
        super(message);
        this.orderList = orderList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
