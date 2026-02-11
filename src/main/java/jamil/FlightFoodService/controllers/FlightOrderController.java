package jamil.FlightFoodService.controllers;

import jamil.FlightFoodService.models.FlightOrder;
import jamil.FlightFoodService.services.FlightOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

@RestController
@RequestMapping("/orders")
public class FlightOrderController {

    private final FlightOrderService orderService;

    public FlightOrderController(FlightOrderService orderService) {
        this.orderService = orderService;
    }

    // GET all orders
    @GetMapping
    public List<FlightOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    // GET order by ID
    @GetMapping("/{id}")
    public ResponseEntity<FlightOrder> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create new order
    @PostMapping
    public FlightOrder createOrder(@RequestBody FlightOrder order) {
        return orderService.saveOrder(order);
    }

    // PUT update existing order
    @PutMapping("/{id}")
    public ResponseEntity<FlightOrder> updateOrder(
            @PathVariable Long id,
            @RequestBody FlightOrder updatedOrder
    ) {
        return orderService.getOrderById(id)
                .map(existingOrder -> {
                    updatedOrder.setId(existingOrder.getId());
                    updatedOrder.setFlight(existingOrder.getFlight());
                    updatedOrder.setLastUpdated(java.time.LocalDateTime.now());

                    if (updatedOrder.getItemsRequested() != null) {
                        updatedOrder.getItemsRequested().forEach(item -> item.setOrder(updatedOrder));
                    }

                    FlightOrder saved = orderService.saveOrder(updatedOrder);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
