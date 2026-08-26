package ordernow.backend.ordernow_backend.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.dtos.OrderResponseDTO;
import ordernow.backend.ordernow_backend.entities.Dish;
import ordernow.backend.ordernow_backend.entities.Order;
import ordernow.backend.ordernow_backend.entities.OrderItem;
import ordernow.backend.ordernow_backend.entities.ServiceTable;
import ordernow.backend.ordernow_backend.exceptions.ResourceNotFoundException;
import ordernow.backend.ordernow_backend.repositories.DishRepository;
import ordernow.backend.ordernow_backend.repositories.OrderRepository;
import ordernow.backend.ordernow_backend.repositories.OrderItemRepository;
import ordernow.backend.ordernow_backend.repositories.TableServiceRepository;
import ordernow.backend.ordernow_backend.requests.order.CreateNewOrderRequest;
import ordernow.backend.ordernow_backend.requests.order.RemoveOrderRequest;
import ordernow.backend.ordernow_backend.responses.order.OrderResponse;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private AuthService authService;
    private OrderHandler orderHandler;
    private TableServiceRepository tableServiceRepository;
    private DishRepository dishRepository;
    private OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, AuthService authService, OrderHandler orderHandler, TableServiceRepository tableServiceRepository, DishRepository dishRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.authService = authService;
        this.orderHandler = orderHandler;
        this.tableServiceRepository = tableServiceRepository;
        this.dishRepository = dishRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public OrderResponse getAllOrders() {
        List<Order> orderList = orderRepository.findByRestaurant(authService.getAuthenticatedUser().getRestaurant());

        if (orderList == null || orderList.isEmpty()) {
            throw new ResourceNotFoundException("No tienes órdenes en la cola.");
        }

        List<OrderResponseDTO> dtoList = orderList.stream()
                .map(OrderResponseDTO::fromEntity)
                .toList();

        return new OrderResponse(dtoList, "Estas son los pedidos.");
    }

    public OrderResponse createOrder(CreateNewOrderRequest createNewOrderRequest) {
        ServiceTable serviceTable = tableServiceRepository.findByQrToken(createNewOrderRequest.getQrToken());

        if (serviceTable == null) {
            throw new ResourceNotFoundException("La mesa no existe");
        }

        Order newOrder = new Order(serviceTable, serviceTable.getRestaurant());

        if (createNewOrderRequest.getOrderList() != null && !createNewOrderRequest.getOrderList().isEmpty()) {
            for (OrderItem eachOrderItem : createNewOrderRequest.getOrderList()) {

                String dishName = eachOrderItem.getDish().getName();

                Dish dish = dishRepository.findByNameAndRestaurantId(dishName, serviceTable.getRestaurant().getIdRestaurant())
                        .orElseThrow(() -> new ResourceNotFoundException("No se encontró el plato: " + dishName));

                OrderItem orderItem = new OrderItem();

                orderItem.setOrder(newOrder);
                orderItem.setDish(dish);
                orderItem.setQuantity(eachOrderItem.getQuantity());

                newOrder.addItem(orderItem);
            }
        } else {
            throw new IllegalArgumentException("La orden debe incluir al menos un plato");
        }

        Order savedOrder = orderRepository.save(newOrder);

        Long restaurantId = serviceTable.getRestaurant().getIdRestaurant();

        orderHandler.notifyNewOrder(restaurantId, savedOrder);

        return new OrderResponse(null, "Se ha creado la orden correctamente.");
    }

    @Transactional
    public OrderResponse removeOrder(RemoveOrderRequest removeOrderRequest) {
        OrderResponseDTO orderRequest = removeOrderRequest.getOrder();

        if (orderRequest == null || orderRequest.idOrder() == null) {
            throw new ResourceNotFoundException("La orden no existe");
        }

        Long restaurantId = authService.getAuthenticatedUser().getRestaurant().getIdRestaurant();
        Order order = this.orderRepository
                .findByIdOrderAndRestaurant_IdRestaurant(orderRequest.idOrder(), restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("La orden no existe"));

        OrderResponseDTO deletedOrder = OrderResponseDTO.fromEntity(order);
        orderItemRepository.deleteAllByOrder(order);
        this.orderRepository.delete(order);

        return new OrderResponse(List.of(deletedOrder), "Se ha eliminado la orden correctamente.");
    }
}
