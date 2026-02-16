package jamil.FlightFoodService.services;

import jamil.FlightFoodService.models.FlightOrder;
import jamil.FlightFoodService.models.OrderedFoodItem;
import jamil.FlightFoodService.repositories.FlightOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightOrderService {

    private final FlightOrderRepository flightOrderRepository;

    public FlightOrderService(FlightOrderRepository flightOrderRepository) {
        this.flightOrderRepository = flightOrderRepository;
    }

    // Get all orders
    public List<FlightOrder> getAllOrders() {
        return flightOrderRepository.findAll();
    }

    // Get order by ID
    public Optional<FlightOrder> getOrderById(Long id) {
        return flightOrderRepository.findById(id);
    }

    // Save or update an order
    public FlightOrder saveOrder(FlightOrder order) {
        if (order.getLastUpdated() == null) {
            order.setLastUpdated(LocalDateTime.now());
        }

        if (order.getItemsRequested() == null) {
            order.setItemsRequested(new ArrayList<>());
        }

        // Link each item to parent order
        for (OrderedFoodItem item : order.getItemsRequested()) {
            item.setOrder(order);
        }

        return flightOrderRepository.save(order);
    }

    // Delete order by ID
    public void deleteOrder(Long id) {
        flightOrderRepository.deleteById(id);
    }
}
