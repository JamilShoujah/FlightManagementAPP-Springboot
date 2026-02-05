package jamil.FlightFoodService.services;

import jamil.FlightFoodService.models.FoodOption;
import jamil.FlightFoodService.repositories.FoodOptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodOptionService {

    private final FoodOptionRepository foodOptionRepository;

    public FoodOptionService(FoodOptionRepository foodOptionRepository) {
        this.foodOptionRepository = foodOptionRepository;
    }

    public List<FoodOption> getAllOptions() {
        return foodOptionRepository.findAll();
    }

    public Optional<FoodOption> getOptionById(Integer id) {
        return foodOptionRepository.findById(id);
    }

    public FoodOption saveOption(FoodOption option) {
        return foodOptionRepository.save(option);
    }

    public void deleteOption(Integer id) {
        foodOptionRepository.deleteById(id);
    }
}
