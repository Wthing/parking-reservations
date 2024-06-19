package com.wthing.parking.services.implementations;

import com.wthing.parking.dto.ParkingSpotDto;
import com.wthing.parking.mappers.Mappers;
import com.wthing.parking.models.ParkingSpot;
import com.wthing.parking.repositories.ParkingSpotRepo;
import com.wthing.parking.services.interfaces.ParkingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.wthing.parking.constants.Messages.*;

@Service
public class ParkingServiceImpl implements ParkingService {
    private final ParkingSpotRepo parkingSpotRepo;

    public ParkingServiceImpl(ParkingSpotRepo parkingSpotRepo) {
        this.parkingSpotRepo = parkingSpotRepo;
    }

    @Override
    public ParkingSpotDto getById(Long parkingSpotId) {
        ParkingSpot parkingSpot = parkingSpotRepo.findById(parkingSpotId)
                .orElseThrow(() -> new IllegalArgumentException(SPOT_NOT_FOUND));

        return Mappers.mapToParkingSpotDto(parkingSpot);
    }

    @Override
    public List<ParkingSpotDto> getAllSpots() {
        List<ParkingSpot> parkingSpots = parkingSpotRepo.findAll();

        return parkingSpots.stream()
                .map(Mappers::mapToParkingSpotDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParkingSpot save(ParkingSpotDto parkingSpotDto) {
        return parkingSpotRepo.save(Mappers.toParkingSpot(parkingSpotDto));
    }

    @Override
    public void deleteSpotById(Long parkingSpotId) {
        parkingSpotRepo.deleteById(parkingSpotId);
    }
}
