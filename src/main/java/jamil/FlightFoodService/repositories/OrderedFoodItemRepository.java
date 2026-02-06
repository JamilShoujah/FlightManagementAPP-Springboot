package jamil.FlightFoodService.repositories;

import jamil.FlightFoodService.models.OrderedFoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedFoodItemRepository extends JpaRepository<OrderedFoodItem, Long> {
}
