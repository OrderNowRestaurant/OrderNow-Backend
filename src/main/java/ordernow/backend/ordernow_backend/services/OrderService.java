package ordernow.backend.ordernow_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.entities.Order;
import ordernow.backend.ordernow_backend.entities.OrderItem;
import ordernow.backend.ordernow_backend.entities.ServiceTable;
import ordernow.backend.ordernow_backend.exceptions.ResourceNotFoundException;
import ordernow.backend.ordernow_backend.repositories.OrderRepository;
import ordernow.backend.ordernow_backend.repositories.TableServiceRepository;
import ordernow.backend.ordernow_backend.requests.order.CreateNewOrderRequest;
import ordernow.backend.ordernow_backend.responses.order.OrderResponse;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private AuthService authService;
    private OrderHandler orderHandler;
    private TableServiceRepository tableServiceRepository;

    public OrderService(OrderRepository orderRepository, AuthService authService, OrderHandler orderHandler, TableServiceRepository tableServiceRepository) {
        this.orderRepository = orderRepository;
        this.authService = authService;
        this.orderHandler = orderHandler;
        this.tableServiceRepository = tableServiceRepository;
    }

    public OrderResponse getAllOrders() {
        List<Order> orderList = orderRepository.findAllByRestaurant(this.authService.getAuthenticatedUser().getRestaurant());

        if(orderList == null || orderList.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado órdenes");
        }

        return new OrderResponse(orderList, "hola");
    }

    public OrderResponse createOrder(CreateNewOrderRequest createNewOrderRequest) {
        ServiceTable serviceTable = tableServiceRepository.findByQrToken(createNewOrderRequest.getQrToken());

        if (serviceTable == null) {
            throw new ResourceNotFoundException("La mesa no existe");
        }

        Order newOrder = new Order(serviceTable, serviceTable.getRestaurant());

        if (createNewOrderRequest.getOrderList() != null && !createNewOrderRequest.getOrderList().isEmpty()) {

            for (OrderItem eachOrderItem : createNewOrderRequest.getOrderList()) {
                
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(newOrder);
                orderItem.setDish(eachOrderItem.getDish());
                orderItem.setQuantity(eachOrderItem.getQuantity());

                newOrder.addItem(orderItem);
            }
        } else {
            throw new IllegalArgumentException("La orden debe incluir al menos un plato");
        }

        try {
            Order savedOrder = orderRepository.save(newOrder);

            orderHandler.notifyNewOrder(authService.getUsername(), savedOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new OrderResponse(List.of(newOrder), "Se ha creado la orden corréctamente.");
    }
}
