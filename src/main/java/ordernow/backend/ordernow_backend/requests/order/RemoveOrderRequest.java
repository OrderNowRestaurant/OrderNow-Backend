package ordernow.backend.ordernow_backend.requests.order;

import ordernow.backend.ordernow_backend.dtos.OrderResponseDTO;

public class RemoveOrderRequest {
    private OrderResponseDTO order;

    public OrderResponseDTO getOrder() {
        return order;
    }

    public void setOrder(OrderResponseDTO order) {
        this.order = order;
    }
}
