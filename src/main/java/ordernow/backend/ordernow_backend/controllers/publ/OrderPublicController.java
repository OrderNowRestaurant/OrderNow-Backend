package ordernow.backend.ordernow_backend.controllers.publ;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ordernow.backend.ordernow_backend.requests.order.CreateNewOrderRequest;
import ordernow.backend.ordernow_backend.responses.order.OrderResponse;
import ordernow.backend.ordernow_backend.services.OrderService;

@RestController
@RequestMapping("/api/public/order")
public class OrderPublicController {

    private OrderService orderService;

    public OrderPublicController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public OrderResponse createOrder(@RequestBody CreateNewOrderRequest createNewOrderRequest) {
        return this.orderService.createOrder(createNewOrderRequest);
    }
}
