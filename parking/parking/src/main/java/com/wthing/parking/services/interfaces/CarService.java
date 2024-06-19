package com.wthing.parking.services.interfaces;

import com.wthing.parking.dto.CarDto;
import com.wthing.parking.models.Car;
import com.wthing.parking.models.User;

import java.util.List;

public interface CarService {
    Car save(Car car);

    Car create(CarDto carDto);

    CarDto getCarByUser(User user);

    List<CarDto> getAllCars();
}
