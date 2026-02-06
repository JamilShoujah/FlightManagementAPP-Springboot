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

    public List<FlightOrder> getAllOrders() {
        return flightOrderRepository.findAll();
    }

    public Optional<FlightOrder> getOrderById(Long id) {
        return flightOrderRepository.findById(id);
    }

    public FlightOrder saveOrder(FlightOrder order) {
        // Ensure lastUpdated is set
        if (order.getLastUpdated() == null) {
            order.setLastUpdated(LocalDateTime.now());
        }

        // Ensure itemsRequested is initialized
        if (order.getItemsRequested() == null) {
            order.setItemsRequested(new ArrayList<>());
        }

        // Link each OrderedFoodItem to its parent order
        for (OrderedFoodItem item : order.getItemsRequested()) {
            item.setOrder(order);
        }

        return flightOrderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        flightOrderRepository.deleteById(id);
    }
}
