package com.wthing.parking.services.interfaces;

import com.wthing.parking.dto.ReservationDto;
import com.wthing.parking.enums.ReservationStatusEnum;
import com.wthing.parking.models.Reservation;

import java.util.List;

public interface ReservationService {
    List<ReservationDto> getAllReservations();

    Reservation save(Reservation reservation);

    Reservation create(ReservationDto reservationDto);

    Reservation startReservation(Long reservationId);

    Reservation endReservation(Long reservationId);

    ReservationDto getById(Long reservationId);

    void deleteById(Long reservationId);

    List<ReservationDto> getActiveReservations();

    Reservation resolveIssue(Long reservationId, ReservationStatusEnum newStatus);

    Reservation canselReservation(Long reservationId);
}
