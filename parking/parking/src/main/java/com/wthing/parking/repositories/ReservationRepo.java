package com.wthing.parking.repositories;

import com.wthing.parking.enums.ReservationStatusEnum;
import com.wthing.parking.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStatus(ReservationStatusEnum status);
}
