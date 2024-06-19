package com.wthing.parking.services.implementations;

import com.wthing.parking.dto.ReservationDto;
import com.wthing.parking.enums.ParkingSpotStatusEnum;
import com.wthing.parking.enums.ReservationStatusEnum;
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

        if (user != null && parkingSpot != null && ParkingSpotStatusEnum.FREE.equals(parkingSpot.getStatus())) {
            reservation.setUser(user);
            reservation.setParkingSpot(parkingSpot);
            reservation.setStartTime(reservationDto.getStartTime());
            reservation.setEndTime(reservationDto.getEndTime());
            reservation.setStatus(ReservationStatusEnum.RESERVED);

            parkingSpot.setStatus(ParkingSpotStatusEnum.RESERVED);
            parkingSpotRepo.save(parkingSpot);

            return reservationRepo.save(reservation);
        }
        return null;
    }

    @Override
    public Reservation startReservation(Long reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException(RESERVATION_NOT_FOUND));

        if (reservation != null && ReservationStatusEnum.RESERVED.equals(reservation.getStatus())) {
            reservation.setStatus(ReservationStatusEnum.ACTIVE);

            ParkingSpot parkingSpot = reservation.getParkingSpot();
            parkingSpot.setStatus(ParkingSpotStatusEnum.OCCUPIED);
            parkingSpotRepo.save(parkingSpot);

            return reservationRepo.save(reservation);
        }

        return null;
    }

    @Override
    public Reservation endReservation(Long reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException(RESERVATION_NOT_FOUND));

        if (reservation != null && ReservationStatusEnum.ACTIVE.equals(reservation.getStatus())) {
            reservation.setStatus(ReservationStatusEnum.COMPLETED);

            ParkingSpot parkingSpot = reservation.getParkingSpot();
            parkingSpot.setStatus(ParkingSpotStatusEnum.FREE);
            parkingSpotRepo.save(parkingSpot);

            return reservationRepo.save(reservation);
        }

        return null;
    }

    @Override
    public ReservationDto getById(Long reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException(RESERVATION_NOT_FOUND));

        return Mappers.mapToReservationDto(reservation);
    }

    @Override
    public void deleteById(Long reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException(RESERVATION_NOT_FOUND));

        if (reservation != null) {
            ParkingSpot parkingSpot = reservation.getParkingSpot();
            if (ReservationStatusEnum.RESERVED.equals(reservation.getStatus())) {
                reservation.setStatus(ReservationStatusEnum.CANCELED);

                parkingSpot.setStatus(ParkingSpotStatusEnum.FREE);
                parkingSpotRepo.save(parkingSpot);
            }
            reservationRepo.deleteById(reservationId);
        }
    }

    @Override
    public List<ReservationDto> getActiveReservations() {
        List<Reservation> active = reservationRepo.findByStatus(ReservationStatusEnum.ACTIVE);

        return active.stream()
                .map(Mappers::mapToReservationDto)
                .collect(Collectors.toList());
    }

    @Override
    public Reservation resolveIssue(Long reservationId, ReservationStatusEnum newStatus) {
        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException(RESERVATION_NOT_FOUND));

        if (reservation != null) {
            reservation.setStatus(newStatus);
            if (ReservationStatusEnum.CANCELED.equals(newStatus)) {
                ParkingSpot parkingSpot = parkingSpotRepo.findById(reservation.getParkingSpot().getParkingSpotId())
                        .orElseThrow(() -> new IllegalArgumentException(SPOT_NOT_FOUND));
                if (parkingSpot != null) {
                    parkingSpot.setStatus(ParkingSpotStatusEnum.FREE);
                    parkingSpotRepo.save(parkingSpot);
                }
            }
            reservationRepo.save(reservation);
        }
        return null;
    }

    @Override
    public Reservation canselReservation(Long reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException(RESERVATION_NOT_FOUND));

        if (reservation != null) {
            reservation.setStatus(ReservationStatusEnum.CANCELED);
            ParkingSpot parkingSpot = parkingSpotRepo.findById(reservation.getParkingSpot().getParkingSpotId())
                    .orElseThrow(() -> new IllegalArgumentException(SPOT_NOT_FOUND));
            if (parkingSpot != null) {
                parkingSpot.setStatus(ParkingSpotStatusEnum.FREE);
                parkingSpotRepo.save(parkingSpot);
            }

            return reservationRepo.save(reservation);
        }

        return null;
    }
}
