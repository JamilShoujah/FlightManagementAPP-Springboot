package jamil.FlightFoodService.repositories;

import java.util.List;
import java.util.Optional;

import jamil.FlightFoodService.models.Flight;
import jamil.FlightFoodService.models.FlightOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightOrderRepository extends JpaRepository<FlightOrder, Long> {

    @Query("""
            select distinct o
            from FlightOrder o
            left join fetch o.flight
            left join fetch o.itemsRequested
            order by o.lastUpdated desc
            """)
    List<FlightOrder> findAllWithFlightAndItems();

    @EntityGraph(attributePaths = {"flight", "itemsRequested"})
    @Query("select o from FlightOrder o where o.id = :id")
    Optional<FlightOrder> findByIdWithFlightAndItems(@Param("id") Long id);

    boolean existsByFlight(Flight flight);
}
