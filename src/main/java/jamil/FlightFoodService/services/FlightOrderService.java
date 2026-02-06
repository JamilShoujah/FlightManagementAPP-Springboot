package jamil.FlightFoodService.services;

import jamil.FlightFoodService.models.FlightOrder;
import jamil.FlightFoodService.repositories.FlightOrderRepository;
import org.springframework.stereotype.Service;

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
        return flightOrderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        flightOrderRepository.deleteById(id);
    }
}
