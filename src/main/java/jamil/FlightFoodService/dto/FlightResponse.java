package jamil.FlightFoodService.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jamil.FlightFoodService.models.Flight;

public record FlightResponse(
        Long id,
        String brand,
        String planeType,
        Integer crewCount,
        Integer seats,
        String preferredFood,
        LocalDate arrivalDate,
        LocalDate departureDate,
        LocalTime departureTime,
        Boolean foodRequested) {

    public static FlightResponse fromEntity(Flight flight) {
        return new FlightResponse(
                flight.getId(),
                flight.getBrand(),
                flight.getPlaneType(),
                flight.getCrewCount(),
                flight.getSeats(),
                flight.getPreferredFood(),
                flight.getArrivalDate(),
                flight.getDepartureDate(),
                flight.getDepartureTime(),
                flight.getFoodRequested());
    }
}
