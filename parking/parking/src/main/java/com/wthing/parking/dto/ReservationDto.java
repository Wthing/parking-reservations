package com.wthing.parking.dto;

import com.wthing.parking.enums.ReservationStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDto {
    private Long userId;
    private Long parkingSpotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ReservationStatusEnum status;
}
