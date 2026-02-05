package jamil.FlightFoodService.repositories;

import jamil.FlightFoodService.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    // You can add custom queries if needed
}
