package com.wthing.parking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDto {
    private Long userId;
    private String licensePlate;
    private String model;
}
