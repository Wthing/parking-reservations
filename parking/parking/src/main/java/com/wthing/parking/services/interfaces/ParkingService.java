package com.wthing.parking.services.interfaces;

import com.wthing.parking.dto.ParkingSpotDto;
import com.wthing.parking.models.ParkingSpot;

import java.util.List;

public interface ParkingService {
    ParkingSpotDto getById(Long parkingSpotId);

    List<ParkingSpotDto> getAllSpots();

    ParkingSpot create(ParkingSpotDto parkingSpotDto);

    ParkingSpot updateParkingSpot(Long spotId, ParkingSpotDto parkingSpotDto);

    ParkingSpot save(ParkingSpotDto parkingSpotDto);

    void deleteSpotById(Long parkingSpotId);

    List<ParkingSpotDto> getAllAvailable();

    String checkStatus(Long parkingSpotId);
}
