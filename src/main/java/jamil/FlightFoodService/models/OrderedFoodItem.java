package jamil.FlightFoodService.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ordered_food_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedFoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private FlightOrder order;

    @Column(name = "food_id", nullable = false)
    private Integer foodId;

    @Column(nullable = false)
    private Integer quantity;
}
