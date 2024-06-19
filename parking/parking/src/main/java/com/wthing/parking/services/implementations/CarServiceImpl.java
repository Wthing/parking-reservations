package com.wthing.parking.services.implementations;

import com.wthing.parking.constants.Messages;
import com.wthing.parking.dto.CarDto;
import com.wthing.parking.mappers.Mappers;
import com.wthing.parking.models.Car;
import com.wthing.parking.models.User;
import com.wthing.parking.repositories.CarRepo;
import com.wthing.parking.repositories.UserRepo;
import com.wthing.parking.services.interfaces.CarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.wthing.parking.constants.Messages.*;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepo carRepo;
    private final UserRepo userRepo;

    public CarServiceImpl(CarRepo carRepo, UserRepo userRepo) {
        this.carRepo = carRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Car save(Car car) {
        return carRepo.save(car);
    }

    @Override
    public Car create(CarDto carDto) {
        Car car = new Car();

        User user = userRepo.findById(carDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        car.setUser(user);
        car.setLicensePlate(carDto.getLicensePlate());
        car.setModel(carDto.getModel());

        return carRepo.save(car);
    }

    @Override
    public CarDto getCarByUser(User user) {
        Long userId = user.getUserId();
        Car car = carRepo.findByUserUserId(userId);

        return Mappers.mapToCarDto(car);
    }

    @Override
    public List<CarDto> getAllCars() {
        List<Car> cars = carRepo.findAll();

        return cars.stream()
                .map(Mappers::mapToCarDto)
                .collect(Collectors.toList());
    }
}
