package com.wthing.parking.services;

import com.wthing.parking.dto.ParkingSpotDto;
import com.wthing.parking.enums.ParkingSpotStatusEnum;
import com.wthing.parking.models.ParkingSpot;
import com.wthing.parking.repositories.ParkingSpotRepo;
import com.wthing.parking.services.implementations.ParkingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.wthing.parking.constants.Messages.SPOT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ParkingServiceTest {

    @Mock
    private ParkingSpotRepo parkingSpotRepo;

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_ValidId_ReturnsParkingSpotDto() {
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setNumber(1);
        parkingSpot.setLocation("Location1");
        parkingSpot.setStatus(ParkingSpotStatusEnum.FREE);

        when(parkingSpotRepo.findById(1L)).thenReturn(Optional.of(parkingSpot));

        ParkingSpotDto parkingSpotDto = parkingService.getById(1L);

        assertNotNull(parkingSpotDto);
        assertEquals(1, parkingSpotDto.getNumber());
        assertEquals("Location1", parkingSpotDto.getLocation());
        assertEquals(ParkingSpotStatusEnum.FREE, parkingSpot.getStatus());
    }

    @Test
    void getById_InvalidId_ThrowsException() {
        when(parkingSpotRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> parkingService.getById(1L));
        assertEquals(SPOT_NOT_FOUND, exception.getMessage());
    }

    @Test
    void getAllSpots_ReturnsListOfParkingSpotDtos() {
        ParkingSpot parkingSpot1 = new ParkingSpot();
        parkingSpot1.setNumber(1);
        parkingSpot1.setLocation("Location1");
        parkingSpot1.setStatus(ParkingSpotStatusEnum.FREE);

        ParkingSpot parkingSpot2 = new ParkingSpot();
        parkingSpot2.setNumber(2);
        parkingSpot2.setLocation("Location2");
        parkingSpot2.setStatus(ParkingSpotStatusEnum.OCCUPIED);

        List<ParkingSpot> parkingSpots = Arrays.asList(parkingSpot1, parkingSpot2);

        when(parkingSpotRepo.findAll()).thenReturn(parkingSpots);

        List<ParkingSpotDto> parkingSpotDtos = parkingService.getAllSpots();

        assertNotNull(parkingSpotDtos);
        assertEquals(2, parkingSpotDtos.size());
    }

    @Test
    void create_ValidParkingSpotDto_ReturnsParkingSpot() {
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setNumber(1);
        parkingSpotDto.setLocation("Location1");

        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setNumber(1);
        parkingSpot.setLocation("Location1");
        parkingSpot.setStatus(ParkingSpotStatusEnum.FREE);

        when(parkingSpotRepo.save(any(ParkingSpot.class))).thenReturn(parkingSpot);

        ParkingSpot createdParkingSpot = parkingService.create(parkingSpotDto);

        assertNotNull(createdParkingSpot);
        assertEquals(1, createdParkingSpot.getNumber());
        assertEquals("Location1", createdParkingSpot.getLocation());
        assertEquals(ParkingSpotStatusEnum.FREE, createdParkingSpot.getStatus());
    }

    @Test
    void updateParkingSpot_InvalidId_ThrowsException() {
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setNumber(1);
        parkingSpotDto.setLocation("Location1");
        parkingSpotDto.setStatus(ParkingSpotStatusEnum.OCCUPIED);

        when(parkingSpotRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> parkingService.updateParkingSpot(1L, parkingSpotDto));
        assertEquals(SPOT_NOT_FOUND, exception.getMessage());
    }

    @Test
    void save_ValidParkingSpotDto_ReturnsParkingSpot() {
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setNumber(1);
        parkingSpotDto.setLocation("Location1");

        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setNumber(1);
        parkingSpot.setLocation("Location1");
        parkingSpot.setStatus(ParkingSpotStatusEnum.FREE);

        when(parkingSpotRepo.save(any(ParkingSpot.class))).thenReturn(parkingSpot);

        ParkingSpot savedParkingSpot = parkingService.save(parkingSpotDto);

        assertNotNull(savedParkingSpot);
        assertEquals(1, savedParkingSpot.getNumber());
        assertEquals("Location1", savedParkingSpot.getLocation());
        assertEquals(ParkingSpotStatusEnum.FREE, savedParkingSpot.getStatus());
    }

    @Test
    void deleteSpotById_ValidId() {
        Long spotId = 1L;
        parkingService.deleteSpotById(spotId);
        verify(parkingSpotRepo, times(1)).deleteById(spotId);
    }

    @Test
    void getAllAvailable_ReturnsListOfAvailableParkingSpots() {
        ParkingSpot parkingSpot1 = new ParkingSpot();
        parkingSpot1.setNumber(1);
        parkingSpot1.setLocation("Location1");
        parkingSpot1.setStatus(ParkingSpotStatusEnum.FREE);

        ParkingSpot parkingSpot2 = new ParkingSpot();
        parkingSpot2.setNumber(2);
        parkingSpot2.setLocation("Location2");
        parkingSpot2.setStatus(ParkingSpotStatusEnum.FREE);

        List<ParkingSpot> availableSpots = Arrays.asList(parkingSpot1, parkingSpot2);

        when(parkingSpotRepo.findByStatus(ParkingSpotStatusEnum.FREE)).thenReturn(availableSpots);

        List<ParkingSpotDto> availableSpotDtos = parkingService.getAllAvailable();

        assertNotNull(availableSpotDtos);
        assertEquals(2, availableSpotDtos.size());
    }

    @Test
    void checkStatus_ValidId_ReturnsStatus() {
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setNumber(1);
        parkingSpot.setLocation("Location1");
        parkingSpot.setStatus(ParkingSpotStatusEnum.FREE);

        when(parkingSpotRepo.findById(1L)).thenReturn(Optional.of(parkingSpot));

        String status = parkingService.checkStatus(1L);

        assertEquals(ParkingSpotStatusEnum.FREE.toString(), status);
    }

    @Test
    void checkStatus_InvalidId_ThrowsException() {
        when(parkingSpotRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> parkingService.checkStatus(1L));
        assertEquals(SPOT_NOT_FOUND, exception.getMessage());
    }
}
