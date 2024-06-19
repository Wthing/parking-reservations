package com.wthing.parking.services.implementations;

import com.wthing.parking.dto.ReservationDto;
import com.wthing.parking.mappers.Mappers;
import com.wthing.parking.models.ParkingSpot;
import com.wthing.parking.models.Reservation;
import com.wthing.parking.models.User;
import com.wthing.parking.repositories.ParkingSpotRepo;
import com.wthing.parking.repositories.ReservationRepo;
import com.wthing.parking.repositories.UserRepo;
import com.wthing.parking.services.interfaces.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.wthing.parking.constants.Messages.*;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepo reservationRepo;
    private final UserRepo userRepo;
    private final ParkingSpotRepo parkingSpotRepo;

    public ReservationServiceImpl(ReservationRepo reservationRepo, UserRepo userRepo, ParkingSpotRepo parkingSpotRepo) {
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo;
        this.parkingSpotRepo = parkingSpotRepo;
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationRepo.findAll();

        return reservations.stream()
                .map(Mappers::mapToReservationDto)
                .collect(Collectors.toList());
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepo.save(reservation);
    }

    @Override
    public Reservation create(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();

        User user = userRepo.findById(reservationDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        ParkingSpot parkingSpot = parkingSpotRepo.findById(reservationDto.getParkingSpotId())
                .orElseThrow(() -> new IllegalArgumentException(SPOT_NOT_FOUND));

        reservation.setUser(user);
        reservation.setParkingSpot(parkingSpot);
        reservation.setStartTime(reservationDto.getStartTime());
        reservation.setEndTime(reservationDto.getEndTime());
        reservation.setStatus(reservationDto.getStatus());

        return reservationRepo.save(reservation);
    }

    @Override
    public ReservationDto getById(Long reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException(RESERVATION_NOT_FOUND));

        return Mappers.mapToReservationDto(reservation);
    }

    @Override
    public void deleteById(Long reservationId) {
        reservationRepo.deleteById(reservationId);
    }
}
