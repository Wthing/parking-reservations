package com.wthing.parking.dto;

import com.wthing.parking.enums.SubTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SubscriptionDto {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private SubTypeEnum type;
}
