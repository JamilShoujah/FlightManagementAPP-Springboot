package jamil.FlightFoodService.controllers;

import jamil.FlightFoodService.models.FoodOption;
import jamil.FlightFoodService.services.FoodOptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food-options")
public class FoodOptionController {

    private final FoodOptionService foodOptionService;

    public FoodOptionController(FoodOptionService foodOptionService) {
        this.foodOptionService = foodOptionService;
    }

    @GetMapping
    public List<FoodOption> getAllOptions() {
        return foodOptionService.getAllOptions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodOption> getOptionById(@PathVariable Integer id) {
        return foodOptionService.getOptionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public FoodOption createOption(@RequestBody FoodOption option) {
        return foodOptionService.saveOption(option);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Integer id) {
        foodOptionService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }
}
