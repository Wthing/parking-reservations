package com.wthing.parking.controllers;

import com.wthing.parking.dto.ParkingSpotDto;
import com.wthing.parking.models.ParkingSpot;
import com.wthing.parking.services.implementations.ParkingServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-spots")
@RequiredArgsConstructor
@Tag(name = "Управление парковочными местами")
public class ParkingSpotController {
    @Autowired
    private ParkingServiceImpl parkingService;

    @GetMapping
    public List<ParkingSpotDto> getAllParkingSpots() {
        return parkingService.getAllSpots();
    }

    @GetMapping("/{id}")
    public ParkingSpotDto getParkingSpotById(@PathVariable Long id) {
        return parkingService.getById(id);
    }

    @PostMapping
    public ParkingSpot createParkingSpot(@RequestBody ParkingSpotDto parkingSpot) {
        return parkingService.create(parkingSpot);
    }

    @PutMapping("/{id}")
    public ParkingSpot updateParkingSpot(@PathVariable Long id, @RequestBody ParkingSpotDto parkingSpotDetails) {
        return parkingService.updateParkingSpot(id, parkingSpotDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteParkingSpot(@PathVariable Long id) {
        parkingService.deleteSpotById(id);
    }
}
