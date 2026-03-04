package jamil.FlightFoodService.services;

import jamil.FlightFoodService.models.FlightOrder;
import jamil.FlightFoodService.models.OrderedFoodItem;
import jamil.FlightFoodService.repositories.FlightRepository;
import jamil.FlightFoodService.repositories.FlightOrderRepository;
import jamil.FlightFoodService.repositories.FoodOptionRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightOrderService {

    private final FlightOrderRepository flightOrderRepository;
    private final FlightRepository flightRepository;
    private final FoodOptionRepository foodOptionRepository;

    public FlightOrderService(
            FlightOrderRepository flightOrderRepository,
            FlightRepository flightRepository,
            FoodOptionRepository foodOptionRepository
    ) {
        this.flightOrderRepository = flightOrderRepository;
        this.flightRepository = flightRepository;
        this.foodOptionRepository = foodOptionRepository;
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
        normalizeAndValidateOrder(order);

        try {
            return flightOrderRepository.save(order);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unable to save order due to database constraint: " + conciseRootCause(ex),
                    ex
            );
        }
    }

    private void normalizeAndValidateOrder(FlightOrder order) {
        Long flightId = order.getFlightId();
        if (flightId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "flightId is required");
        }

        order.setFlight(flightRepository.findById(flightId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Unknown flightId: " + flightId
                )));

        if (order.getStatus() == null || order.getStatus().isBlank()) {
            order.setStatus("PENDING");
        }

        if (order.getLastUpdated() == null) {
            order.setLastUpdated(LocalDateTime.now());
        }

        if (order.getItemsRequested() == null) {
            order.setItemsRequested(new ArrayList<>());
        }

        if (order.getItemsRequested().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "At least one item is required to create an order"
            );
        }

        // Validate and link each item to parent order
        for (OrderedFoodItem item : order.getItemsRequested()) {
            if (item.getFoodId() == null || item.getFoodId() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Each item must include a valid foodId");
            }
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Each item must include a quantity greater than zero");
            }
            if (!foodOptionRepository.existsById(item.getFoodId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Unknown foodId: " + item.getFoodId() + ". Seed food_options before creating orders."
                );
            }
            item.setOrder(order);
        }
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
                if (incomingItem.getFoodId() == null || incomingItem.getFoodId() <= 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Each item must include a valid foodId");
                }
                if (incomingItem.getQuantity() == null || incomingItem.getQuantity() <= 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Each item must include a quantity greater than zero");
                }
                if (!foodOptionRepository.existsById(incomingItem.getFoodId())) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Unknown foodId: " + incomingItem.getFoodId() + ". Seed food_options before updating orders."
                    );
                }

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

    private String conciseRootCause(Throwable throwable) {
        Throwable cursor = throwable;
        while (cursor.getCause() != null) {
            cursor = cursor.getCause();
        }
        return cursor.getMessage() != null ? cursor.getMessage() : cursor.getClass().getSimpleName();
    }
}
