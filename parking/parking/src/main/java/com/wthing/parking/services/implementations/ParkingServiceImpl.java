package com.wthing.parking.services.implementations;

import com.wthing.parking.dto.ParkingSpotDto;
import com.wthing.parking.enums.ParkingSpotStatusEnum;
import com.wthing.parking.mappers.Mappers;
import com.wthing.parking.models.ParkingSpot;
import com.wthing.parking.repositories.ParkingSpotRepo;
import com.wthing.parking.services.interfaces.ParkingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.wthing.parking.constants.Messages.SPOT_NOT_FOUND;

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
    public ParkingSpot create(ParkingSpotDto parkingSpotDto) {
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setStatus(ParkingSpotStatusEnum.FREE);
        parkingSpot.setLocation(parkingSpotDto.getLocation());
        parkingSpot.setNumber(parkingSpotDto.getNumber());

        return parkingSpotRepo.save(parkingSpot);
    }

    @Override
    public ParkingSpot updateParkingSpot(Long spotId, ParkingSpotDto parkingSpotDto) {
        ParkingSpot parkingSpot = parkingSpotRepo.findById(spotId)
                .orElseThrow(() -> new IllegalArgumentException(SPOT_NOT_FOUND));

        if (parkingSpot != null) {
            parkingSpot.setNumber(parkingSpotDto.getNumber());
            parkingSpot.setLocation(parkingSpotDto.getLocation());
            parkingSpot.setStatus(parkingSpotDto.getStatus());

            parkingSpotRepo.save(parkingSpot);
        }

        return null;
    }

    @Override
    public ParkingSpot save(ParkingSpotDto parkingSpotDto) {
        return parkingSpotRepo.save(Mappers.toParkingSpot(parkingSpotDto));
    }

    @Override
    public void deleteSpotById(Long parkingSpotId) {
        parkingSpotRepo.deleteById(parkingSpotId);
    }

    @Override
    public List<ParkingSpotDto> getAllAvailable() {
        List<ParkingSpot> available = parkingSpotRepo.findByStatus(ParkingSpotStatusEnum.FREE);

        return available.stream()
                .map(Mappers::mapToParkingSpotDto)
                .collect(Collectors.toList());
    }

    @Override
    public String checkStatus(Long parkingSpotId) {
        ParkingSpot parkingSpot = parkingSpotRepo.findById(parkingSpotId)
                .orElseThrow(() -> new IllegalArgumentException(SPOT_NOT_FOUND));

        return parkingSpot != null ? parkingSpot.getStatus().toString() : SPOT_NOT_FOUND;
    }
}
