package com.wthing.parking.repositories;

import com.wthing.parking.dto.CarDto;
import com.wthing.parking.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
    Car findByUserUserId(Long userId);
}
