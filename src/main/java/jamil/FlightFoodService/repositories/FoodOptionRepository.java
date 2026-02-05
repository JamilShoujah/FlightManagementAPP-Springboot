package jamil.FlightFoodService.repositories;

import jamil.FlightFoodService.models.FoodOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodOptionRepository extends JpaRepository<FoodOption, Integer> {
}
