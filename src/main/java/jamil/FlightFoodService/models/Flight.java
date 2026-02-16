package jamil.FlightFoodService.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(name = "plane_type", nullable = false)
    private String planeType;

    @Column(name = "crew_count", nullable = false)
    private Integer crewCount;

    @Column(nullable = false)
    private Integer seats;

    @Column(name = "preferred_food", nullable = false)
    private String preferredFood;

    @Column(name = "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    @Column(name = "food_requested", nullable = false)
    private Boolean foodRequested;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FlightOrder> orders;
}
