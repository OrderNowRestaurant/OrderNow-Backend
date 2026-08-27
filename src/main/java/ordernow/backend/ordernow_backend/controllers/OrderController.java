package ordernow.backend.ordernow_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ordernow.backend.ordernow_backend.requests.order.RemoveOrderRequest;
import ordernow.backend.ordernow_backend.responses.order.OrderResponse;
import ordernow.backend.ordernow_backend.services.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping("/get")
    public OrderResponse getAllOrders() {
        return this.orderService.getAllOrders();
    }

    @PostMapping("/remove")
    public OrderResponse removeOrder(@RequestBody RemoveOrderRequest order) {
        return this.orderService.removeOrder(order);
    }
}
