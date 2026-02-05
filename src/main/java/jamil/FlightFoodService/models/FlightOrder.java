package jamil.FlightFoodService.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flight_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderedFoodItem> itemsRequested;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;
}
