package jamil.FlightFoodService.services;

import jamil.FlightFoodService.models.FlightOrder;
import jamil.FlightFoodService.models.OrderedFoodItem;
import jamil.FlightFoodService.repositories.FlightOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<FlightOrder> getAllOrders() {
        return flightOrderRepository.findAllWithFlightAndItems();
    }

    // Get order by ID
    @Transactional(readOnly = true)
    public Optional<FlightOrder> getOrderById(Long id) {
        return flightOrderRepository.findByIdWithFlightAndItems(id);
    }

    // Save or update an order
    @Transactional
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

    @Transactional
    public Optional<FlightOrder> updateOrder(Long id, FlightOrder incomingOrder) {
        return flightOrderRepository.findByIdWithFlightAndItems(id).map(existingOrder -> {
            if (incomingOrder.getStatus() != null) {
                existingOrder.setStatus(incomingOrder.getStatus());
            }
            existingOrder.setLastUpdated(LocalDateTime.now());

            if (existingOrder.getItemsRequested() == null) {
                existingOrder.setItemsRequested(new ArrayList<>());
            }

            if (incomingOrder.getItemsRequested() == null) {
                existingOrder.getItemsRequested().clear();
                return existingOrder;
            }

            // Replace children atomically to avoid duplicated rows on repeated updates.
            existingOrder.getItemsRequested().clear();
            for (OrderedFoodItem incomingItem : incomingOrder.getItemsRequested()) {
                OrderedFoodItem newItem = new OrderedFoodItem();
                newItem.setFoodId(incomingItem.getFoodId());
                newItem.setQuantity(incomingItem.getQuantity());
                newItem.setOrder(existingOrder);
                existingOrder.getItemsRequested().add(newItem);
            }

            return existingOrder;
        });
    }

    // Delete order by ID
    @Transactional
    public void deleteOrder(Long id) {
        flightOrderRepository.deleteById(id);
    }
}
