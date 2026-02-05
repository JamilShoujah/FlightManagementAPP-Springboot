package jamil.FlightFoodService.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food_options")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodOption {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // Beef, Chicken, etc.
}
