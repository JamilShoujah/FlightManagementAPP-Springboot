package jamil.FlightFoodService.repositories;

import jamil.FlightFoodService.models.FlightOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightOrderRepository extends JpaRepository<FlightOrder, Long> {
    // Custom queries can go here
}
