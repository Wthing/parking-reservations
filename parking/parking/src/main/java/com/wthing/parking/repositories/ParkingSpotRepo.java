package com.wthing.parking.repositories;

import com.wthing.parking.models.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepo extends JpaRepository<ParkingSpot, Long> {
}
