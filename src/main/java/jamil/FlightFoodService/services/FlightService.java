package jamil.FlightFoodService.services;

import jamil.FlightFoodService.models.Flight;
import jamil.FlightFoodService.repositories.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // Get all flights
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Get flight by ID
    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    // Create or update flight
    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    // Delete flight
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
}
