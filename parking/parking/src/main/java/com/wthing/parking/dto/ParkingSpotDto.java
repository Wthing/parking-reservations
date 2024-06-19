package com.wthing.parking.dto;

import com.wthing.parking.enums.ParkingSpotStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingSpotDto {
    private Integer number;
    private String location;
    private ParkingSpotStatusEnum status;
}
