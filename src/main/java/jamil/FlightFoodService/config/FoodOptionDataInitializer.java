package jamil.FlightFoodService.config;

import jamil.FlightFoodService.models.FoodOption;
import jamil.FlightFoodService.repositories.FoodOptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FoodOptionDataInitializer {

    private static final Logger log = LoggerFactory.getLogger(FoodOptionDataInitializer.class);

    @Bean
    ApplicationRunner seedFoodOptions(FoodOptionRepository foodOptionRepository) {
        return args -> {
            List<FoodOption> defaults = List.of(
                    new FoodOption(1, "Grilled Ribeye", "Beef"),
                    new FoodOption(2, "Beef Stroganoff", "Beef"),
                    new FoodOption(3, "Roast Chicken", "Chicken"),
                    new FoodOption(4, "Grilled Chicken Skewers", "Chicken"),
                    new FoodOption(5, "Grilled Salmon", "Fish"),
                    new FoodOption(6, "Baked Cod", "Fish"),
                    new FoodOption(7, "Vegetable Lasagna", "Vegetarian"),
                    new FoodOption(8, "Stuffed Peppers", "Vegetarian"),
                    new FoodOption(9, "Vegan Burger", "Vegan"),
                    new FoodOption(10, "Quinoa Salad", "Vegan")
            );

            int inserted = 0;
            for (FoodOption option : defaults) {
                if (!foodOptionRepository.existsById(option.getId())) {
                    foodOptionRepository.save(option);
                    inserted++;
                }
            }

            if (inserted > 0) {
                log.info("Inserted {} missing default food options", inserted);
            }
        };
    }
}
