package ordernow.backend.ordernow_backend.dtos;

import java.util.List;
import ordernow.backend.ordernow_backend.entities.Order;

public record OrderResponseDTO(
    Long idOrder,
    String serviceTableName,
    List<OrderItemResponseDTO> dishList
) {
    public static OrderResponseDTO fromEntity(Order order) {
        return new OrderResponseDTO(
            order.getIdOrder(),
            order.getServiceTable() != null ? order.getServiceTable().getName() : null,
            order.getItems().stream().map(OrderItemResponseDTO::fromEntity).toList()
        );
    }
}