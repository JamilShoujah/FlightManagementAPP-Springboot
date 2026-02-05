package jamil.FlightFoodService.services;

import jamil.FlightFoodService.models.OrderedFoodItem;
import jamil.FlightFoodService.repositories.OrderedFoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderedFoodItemService {

    private final OrderedFoodItemRepository orderedFoodItemRepository;

    public OrderedFoodItemService(OrderedFoodItemRepository orderedFoodItemRepository) {
        this.orderedFoodItemRepository = orderedFoodItemRepository;
    }

    public List<OrderedFoodItem> getAllItems() {
        return orderedFoodItemRepository.findAll();
    }

    public OrderedFoodItem saveItem(OrderedFoodItem item) {
        return orderedFoodItemRepository.save(item);
    }

    public void deleteItem(Long id) {
        orderedFoodItemRepository.deleteById(id);
    }
}
