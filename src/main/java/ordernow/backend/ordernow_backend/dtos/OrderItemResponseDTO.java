package ordernow.backend.ordernow_backend.dtos;

import ordernow.backend.ordernow_backend.entities.OrderItem;

public record OrderItemResponseDTO(
    String name,
    Integer quantity
) {
    public static OrderItemResponseDTO fromEntity(OrderItem item) {
        return new OrderItemResponseDTO(
            item.getDish() != null ? item.getDish().getName() : null,
            item.getQuantity()
        );
    }
}

