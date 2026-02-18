package jamil.FlightFoodService.services;

import jamil.FlightFoodService.models.Flight;
import jamil.FlightFoodService.dto.FlightResponse;
import jamil.FlightFoodService.repositories.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // Get all flights
    @Transactional(readOnly = true)
    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAllFlightResponses();
    }

    // Get flight by ID
    @Transactional(readOnly = true)
    public Optional<FlightResponse> getFlightById(Long id) {
        return flightRepository.findFlightResponseById(id);
    }

    // Create or update flight
    @Transactional
    public FlightResponse saveFlight(Flight flight) {
        Flight saved = flightRepository.save(flight);
        return FlightResponse.fromEntity(saved);
    }

    // Delete flight
    @Transactional
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
}
