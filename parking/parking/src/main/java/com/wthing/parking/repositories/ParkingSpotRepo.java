package com.wthing.parking.repositories;

import com.wthing.parking.enums.ParkingSpotStatusEnum;
import com.wthing.parking.models.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepo extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findByStatus(ParkingSpotStatusEnum status);
}
