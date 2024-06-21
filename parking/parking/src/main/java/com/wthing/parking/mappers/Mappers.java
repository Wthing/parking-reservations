package com.wthing.parking.mappers;

import com.wthing.parking.dto.*;
import com.wthing.parking.models.*;
import com.wthing.parking.repositories.UserRepo;

public class Mappers {

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(userDto.getFirstName());
        userDto.setLastName(userDto.getLastName());
        userDto.setIin(userDto.getIin());
        userDto.setRole(user.getRoles());

        return userDto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setIin(userDto.getIin());
        user.setRoles(userDto.getRole());

        return user;
    }

    public static CarDto mapToCarDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setUserId(car.getUser().getUserId());
        carDto.setLicensePlate(car.getLicensePlate());
        carDto.setModel(car.getModel());

        return carDto;
    }
    public static ParkingSpotDto mapToParkingSpotDto(ParkingSpot parkingSpot) {
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setNumber(parkingSpot.getNumber());
        parkingSpotDto.setLocation(parkingSpot.getLocation());

        return parkingSpotDto;
    }

    public static ParkingSpot toParkingSpot(ParkingSpotDto parkingSpotDto) {
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setNumber(parkingSpotDto.getNumber());
        parkingSpot.setLocation(parkingSpotDto.getLocation());

        return parkingSpot;
    }

    public static ReservationDto mapToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setUserId(reservation.getUser().getUserId());
        reservationDto.setParkingSpotId(reservation.getParkingSpot().getParkingSpotId());
        reservationDto.setStartTime(reservation.getStartTime());
        reservationDto.setEndTime(reservation.getEndTime());
        reservationDto.setStatus(reservation.getStatus());

        return reservationDto;
    }

    public static SubscriptionDto mapToSubscriptionDto(Subscription subscription) {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(subscription.getUser().getUserId());
        subscriptionDto.setStartDate(subscription.getStartDate());
        subscriptionDto.setEndDate(subscription.getEndDate());
        subscriptionDto.setType(subscription.getType());

        return subscriptionDto;
    }

    public static NotificationDto mapToNotificationDto(Notification notification) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setUserId(notification.getUser().getUserId());
        notificationDto.setMessage(notification.getMessage());

        return notificationDto;
    }
}
