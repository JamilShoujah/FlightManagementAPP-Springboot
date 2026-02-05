package jamil.FlightFoodService.controllers;

import jamil.FlightFoodService.models.OrderedFoodItem;
import jamil.FlightFoodService.services.OrderedFoodItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordered-items")
public class OrderedFoodItemController {

    private final OrderedFoodItemService itemService;

    public OrderedFoodItemController(OrderedFoodItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<OrderedFoodItem> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    public OrderedFoodItem createItem(@RequestBody OrderedFoodItem item) {
        return itemService.saveItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
