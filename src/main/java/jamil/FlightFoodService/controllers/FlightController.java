package jamil.FlightFoodService.controllers;

import jamil.FlightFoodService.models.Flight;
import jamil.FlightFoodService.dto.FlightResponse;
import jamil.FlightFoodService.services.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    // Get all flights
    @GetMapping
    public List<FlightResponse> getAllFlights() {
        return flightService.getAllFlights();
    }

    // Get flight by ID
    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new flight
    @PostMapping
    public FlightResponse createFlight(@RequestBody Flight flight) {
        return flightService.saveFlight(flight);
    }

    // Update flight
    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        return flightService.getFlightById(id)
                .map(existing -> {
                    flight.setId(existing.id());
                    return ResponseEntity.ok(flightService.saveFlight(flight));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete flight
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}
