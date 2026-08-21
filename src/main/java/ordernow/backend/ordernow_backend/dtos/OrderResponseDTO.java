package ordernow.backend.ordernow_backend.dtos;

import java.util.List;

import ordernow.backend.ordernow_backend.entities.Order;
import ordernow.backend.ordernow_backend.entities.OrderItem;
import ordernow.backend.ordernow_backend.entities.ServiceTable;

public record OrderResponseDTO(List<OrderItem> dishList, ServiceTable serviceTable) {
     public static OrderResponseDTO fromEntity(Order order) {
        return new OrderResponseDTO(
            order.getItems(),
            order.getServiceTable()
        );
    }
}
