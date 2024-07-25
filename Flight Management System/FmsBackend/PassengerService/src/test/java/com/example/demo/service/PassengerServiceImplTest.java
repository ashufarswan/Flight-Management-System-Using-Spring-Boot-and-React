package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.models.Passenger;
import com.example.demo.repository.PassengerRepository;



@ExtendWith(MockitoExtension.class)
public class PassengerServiceImplTest {

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerServiceImpl passengerService;

    private Passenger samplePassenger;

    @BeforeEach
    public void setUp() {
        // Sample Passenger
        samplePassenger = new Passenger();
        samplePassenger.setPassengerId(1);
        samplePassenger.setFirstName("John");
        samplePassenger.setLastName("Doe");
        samplePassenger.setEmailId("john.doe@example.com");
        samplePassenger.setPhoneNumber("1234567890");
        samplePassenger.setBookingIdList(new ArrayList<>(Arrays.asList(123, 456)));

    }

    @Test
    public void testSavePassenger_Success() {
    	when(passengerRepository.save(any(Passenger.class))).thenReturn(samplePassenger);
        Passenger savedPassenger =  passengerService.savePassenger(samplePassenger);
        assertEquals(samplePassenger.getFirstName(), savedPassenger.getFirstName());
        assertEquals(samplePassenger.getLastName(), savedPassenger.getLastName());
 
    }

    @Test
    public void testFetchPassengerById_ExistingPassenger_Success_For_First_And_Last_Name() {
      when(passengerRepository.findById(1)).thenReturn(Optional.of(samplePassenger));

        Passenger fetchedPassenger = passengerService.fetchPassengerById(1);
        assertEquals(samplePassenger.getFirstName(), fetchedPassenger.getFirstName());
        assertEquals(samplePassenger.getLastName(), fetchedPassenger.getLastName());
       
    }

    @Test
    public void testFetchPassengerById_NonExistingPassenger_ExceptionThrown() {
    	when(passengerRepository.findById(999)).thenReturn(Optional.empty());
    	ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            passengerService.fetchPassengerById(999);
        });
        assertEquals("Passenger not Found with id 999", exception.getMessage());
    }


    @Test
    public void testUpdatePassenger_ExistingPassenger_Success_With_First_Last_Name() {
        // Mock repository behavior for findById and save
        when(passengerRepository.findById(1)).thenReturn(Optional.of(samplePassenger));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(samplePassenger);

        // Create updated details
        Passenger updatedPassenger = new Passenger();
        updatedPassenger.setFirstName("UpdatedFirstName");
        updatedPassenger.setLastName("UpdatedLastName");

        // Call the service method
        Passenger result = passengerService.updatePassenger(1, updatedPassenger);

        // Verify the updated passenger
        assertEquals(updatedPassenger.getFirstName(), result.getFirstName());
        assertEquals(updatedPassenger.getLastName(), result.getLastName());
    }


    @Test
    public void testDeletePassenger_ExistingPassenger_Success_With_verify() {
        // Mock repository behavior for findById and deleteById
        when(passengerRepository.findById(1)).thenReturn(Optional.of(samplePassenger));
        doNothing().when(passengerRepository).deleteById(anyInt());

        // Call the service method
        passengerService.deletePassenger(1);

        verify(passengerRepository).deleteById(1);
    }

    @Test
    public void testFetchAllPassengers_Success_With_Verify_Size() {
        // Mock repository behavior for findAll
        when(passengerRepository.findAll()).thenReturn(List.of(samplePassenger));

        // Call the service method
        List<Passenger> passengers = passengerService.fetchAllPassengers();

        // Verify the list of passengers
        assertEquals(1, passengers.size()); // Assuming one sample passenger is returned
        
    }
    
    @Test
    public void testSavePassenger_NewPassenger_Success() {
        // Mock repository behavior for save
        when(passengerRepository.save(any(Passenger.class))).thenReturn(samplePassenger);

        // Call the service method
        Passenger savedPassenger = passengerService.savePassenger(samplePassenger);

        // Verify the saved passenger
        assertEquals(samplePassenger.getFirstName(), savedPassenger.getFirstName());
        assertEquals(samplePassenger.getLastName(), savedPassenger.getLastName());
       
    }

    @Test
    public void testSavePassenger_ExistingPassenger_DuplicateResourceException() {
        // Mock repository behavior for findById
        when(passengerRepository.findById(1)).thenReturn(Optional.of(samplePassenger));

        // Call the service method that should throw exception
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class, () -> {
            passengerService.savePassenger(samplePassenger);
        });

        assertEquals("Passenger with Id 1 already exists", exception.getMessage());
    }

    @Test
    public void testFetchPassengerById_ExistingPassenger_Success() {
        // Mock repository behavior for findById
        when(passengerRepository.findById(1)).thenReturn(Optional.of(samplePassenger));

        // Call the service method
        Passenger fetchedPassenger = passengerService.fetchPassengerById(1);

        // Verify the fetched passenger
        assertEquals(samplePassenger.getFirstName(), fetchedPassenger.getFirstName());
        assertEquals(samplePassenger.getLastName(), fetchedPassenger.getLastName());
        assertEquals(samplePassenger.getGender(), fetchedPassenger.getGender());
        assertEquals(samplePassenger.getEmailId(), fetchedPassenger.getEmailId());
        assertEquals(samplePassenger.getNationality(), fetchedPassenger.getNationality());
        assertEquals(samplePassenger.getPassportIdNumber(), fetchedPassenger.getPassportIdNumber());
        assertEquals(samplePassenger.getPhoneNumber(), fetchedPassenger.getPhoneNumber());
        
    }

    @Test
    public void testFetchPassengerById_NonExistingPassenger_ResourceNotFoundException() {
        // Mock repository behavior for findById
        when(passengerRepository.findById(999)).thenReturn(Optional.empty());

        // Call the service method that should throw exception
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            passengerService.fetchPassengerById(999);
        });

        assertEquals("Passenger not Found with id 999", exception.getMessage());
    }

    @Test
    public void testUpdatePassenger_ExistingPassenger_Success() {
        // Mock repository behavior for findById and save
        when(passengerRepository.findById(1)).thenReturn(Optional.of(samplePassenger));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(samplePassenger);

        // Create updated details
        Passenger updatedPassenger = new Passenger();
        updatedPassenger.setFirstName("UpdatedFirstName");
        updatedPassenger.setLastName("UpdatedLastName");

        // Call the service method
        Passenger result = passengerService.updatePassenger(1, updatedPassenger);

        // Verify the updated passenger
        assertEquals(updatedPassenger.getFirstName(), result.getFirstName());
        assertEquals(updatedPassenger.getLastName(), result.getLastName());
        // Add assertions for other fields
    }

    @Test
    public void testUpdatePassenger_NonExistingPassenger_ResourceNotFoundException() {
        // Mock repository behavior for findById
        when(passengerRepository.findById(999)).thenReturn(Optional.empty());

        // Call the service method that should throw exception
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            passengerService.updatePassenger(999, new Passenger());
        });

        assertEquals("Passenger not found with id: 999", exception.getMessage());
    }

    
    
    
    
    @Test
    public void testFetchByBookingId_ValidBookingId_Success() {
        // Mock repository behavior for findByBookingId
        when(passengerRepository.findByBookingId(123)).thenReturn(Arrays.asList(samplePassenger));

        // Call the service method
        List<Passenger> result = passengerService.fetchByBookingId(123);

        // Verify the result
        assertEquals(1, result.size());
        assertEquals(samplePassenger.getPassengerId(), result.get(0).getPassengerId());
      
    }

    @Test
    public void testFetchByBookingId_NoMatchingBookingId_EmptyList() {
        // Mock repository behavior for findByBookingId
        when(passengerRepository.findByBookingId(999)).thenReturn(new ArrayList<>());

        // Call the service method
        List<Passenger> result = passengerService.fetchByBookingId(999);

        // Verify the result
        assertEquals(0, result.size());
    }

    @Test
    public void testDeleteBookingId_ValidPassengerIdAndBookingId_Success() {
        // Mock repository behavior for findById and save
        when(passengerRepository.findById(1)).thenReturn(Optional.of(samplePassenger));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(samplePassenger);

        // Call the service method
        passengerService.deleteBookingId(1, 123);

        // Verify the updated passenger
        assertEquals(1, samplePassenger.getBookingIdList().size());
        assertEquals(Arrays.asList(456), samplePassenger.getBookingIdList());
    }

    @Test
    public void testDeleteBookingId_InvalidPassengerId_ResourceNotFoundException() {
        // Mock repository behavior for findById
        when(passengerRepository.findById(999)).thenReturn(Optional.empty());

        // Call the service method that should throw exception
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            passengerService.deleteBookingId(999, 123);
        });

        assertEquals("Passenger not found with id: 999", exception.getMessage());
    }

    @Test
    public void testAddBookingId_ValidPassengerIdAndBookingId_Success() {
        // Mock repository behavior for findById and save
        when(passengerRepository.findById(1)).thenReturn(Optional.of(samplePassenger));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(samplePassenger);

        // Call the service method
        Passenger result = passengerService.addBookingId(1, 789);

        // Verify the updated passenger
        assertEquals(3, result.getBookingIdList().size()); // Assuming there were already two booking IDs
        assertEquals(Arrays.asList(123, 456, 789), result.getBookingIdList());
    }

    @Test
    public void testAddBookingId_InvalidPassengerId_ResourceNotFoundException() {
        // Mock repository behavior for findById
        when(passengerRepository.findById(999)).thenReturn(Optional.empty());

        // Call the service method that should throw exception
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            passengerService.addBookingId(999, 789);
        });

        assertEquals("Passenger not found with id: 999", exception.getMessage());
    }
  

}
