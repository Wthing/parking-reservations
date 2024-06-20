package com.wthing.parking.controllers;

import com.wthing.parking.dto.ReservationDto;
import com.wthing.parking.models.Reservation;
import com.wthing.parking.services.implementations.ReservationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Управление бронированиями")
public class ReservationController {

    @Autowired
    private ReservationServiceImpl reservationService;

    @Operation(summary = "Получить список всех бронирований")
    @GetMapping
    public List<ReservationDto> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @Operation(summary = "Получить список всех действующих бронирований")
    @GetMapping("/active")
    public List<ReservationDto> getActiveReservations() {
        return reservationService.getActiveReservations();
    }

    @Operation(summary = "Получить бронирование по ID")
    @GetMapping("/{id}")
    public ReservationDto getReservationById(@PathVariable Long id) {
        return reservationService.getById(id);
    }

    @Operation(summary = "Создать бронь")
    @PostMapping
    public Reservation createReservation(ReservationDto reservationDto) {
        return reservationService.create(reservationDto);
    }

    @Operation(summary = "Активировать бронь")
    @PostMapping("/start/{id}")
    public Reservation startReservation(@PathVariable Long id) {
        return reservationService.startReservation(id);
    }

    @Operation(summary = "Завершить бронь")
    @PostMapping("/end/{id}")
    public Reservation endReservation(@PathVariable Long id) {
        return reservationService.endReservation(id);
    }

    @Operation(summary = "Удалить бронь")
    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
    }

    @Operation(summary = "Отменить бронь")
    @PostMapping("/cancel/{id}")
    public Reservation cancelReservation(@PathVariable Long id) {
        return reservationService.canselReservation(id);
    }
}
