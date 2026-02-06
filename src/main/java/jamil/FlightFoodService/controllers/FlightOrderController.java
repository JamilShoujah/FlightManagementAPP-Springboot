package jamil.FlightFoodService.controllers;

import jamil.FlightFoodService.models.FlightOrder;
import jamil.FlightFoodService.services.FlightOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class FlightOrderController {

    private final FlightOrderService orderService;

    public FlightOrderController(FlightOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<FlightOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightOrder> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public FlightOrder createOrder(@RequestBody FlightOrder order) {
        return orderService.saveOrder(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
