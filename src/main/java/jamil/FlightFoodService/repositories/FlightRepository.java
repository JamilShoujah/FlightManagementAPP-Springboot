package jamil.FlightFoodService.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import jamil.FlightFoodService.models.Flight;
import jamil.FlightFoodService.dto.FlightResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
            select new jamil.FlightFoodService.dto.FlightResponse(
                f.id,
                f.brand,
                f.planeType,
                f.crewCount,
                f.seats,
                f.preferredFood,
                f.arrivalDate,
                f.departureDate,
                f.departureTime,
                f.foodRequested
            )
            from Flight f
            order by f.departureDate asc, f.departureTime asc
            """)
    List<FlightResponse> findAllFlightResponses();

    @Query("""
            select new jamil.FlightFoodService.dto.FlightResponse(
                f.id,
                f.brand,
                f.planeType,
                f.crewCount,
                f.seats,
                f.preferredFood,
                f.arrivalDate,
                f.departureDate,
                f.departureTime,
                f.foodRequested
            )
            from Flight f
            where f.id = :id
            """)
    Optional<FlightResponse> findFlightResponseById(@Param("id") Long id);
}
