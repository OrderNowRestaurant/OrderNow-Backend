package ordernow.backend.ordernow_backend.responses.order;

import java.util.List;

import ordernow.backend.ordernow_backend.dtos.OrderResponseDTO;
import ordernow.backend.ordernow_backend.responses.BaseResponse;

public class OrderResponse extends BaseResponse {
    private List<OrderResponseDTO> orderList;

    public OrderResponse(List<OrderResponseDTO> orderList, String message) {
        super(message);
        this.orderList = orderList;
    }

    public List<OrderResponseDTO> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderResponseDTO> orderList) {
        this.orderList = orderList;
    }

}
