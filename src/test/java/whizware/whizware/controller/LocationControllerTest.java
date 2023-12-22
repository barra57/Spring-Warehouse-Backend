package whizware.whizware.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.location.LocationRequest;
import whizware.whizware.dto.location.LocationResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.service.LocationService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
//import static whizware.whizware.util.TestUtilities.generateLocation;

class LocationControllerTest {
    @InjectMocks
    LocationController locationController;

    @Mock
    LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);}

    @Test
    void testAddLocation() {
        LocationRequest request = new LocationRequest("Serang");
        LocationResponse data = new LocationResponse(4L, "Serang");
        BaseResponse response = BaseResponse.builder()
                .message("Successfully added data!")
                .data(data)
                .build();

        when(locationService.addLocation(request)).thenReturn(response);
        ResponseEntity<BaseResponse> responseEntity = locationController.addLoc(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    void getAllLocationTest() {
        LocationResponse response1 = new LocationResponse(1L, "Tangerang");
        LocationResponse response2 = new LocationResponse(2L, "Jakarta Timur");
        LocationResponse response3 = new LocationResponse(3L, "Jakarta Barat");

        List<LocationResponse> data = Arrays.asList(response1, response2, response3);

        BaseResponse response = BaseResponse.builder()
                .message("Success get all data")
                .data(data)
                .build();

        when(locationService.getAll()).thenReturn(response);
        ResponseEntity<BaseResponse> actualResponse = locationController.getAll();

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(response, actualResponse.getBody());
    }

    @Test
    void getLocationByIdTest() {
        LocationResponse data = LocationResponse.builder()
                .id(1L)
                .name("Jakarta Barat")
                .build();

        BaseResponse response = BaseResponse.builder()
                .message("Succes get data with ID")
                .data(data)
                .build();

        when(locationService.getLocById(data.getId())).thenReturn(response);

        ResponseEntity<BaseResponse> actualResponse = locationController.getById(data.getId());

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(response, actualResponse.getBody());
    }

    @Test
    void updateLocationTest() {
        Long id = 2L;

        LocationRequest request = new LocationRequest("Bandung");

        LocationResponse data = LocationResponse.builder()
                .id(id)
                .name(request.getName())
                .build();

        BaseResponse response = BaseResponse.builder()
                .message("Success update data!")
                .data(data)
                .build();

        when(locationService.updateLocation(id, request)).thenReturn(response);

        ResponseEntity<BaseResponse> actualResponse = locationController.updateLoc(id, request);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(response, actualResponse.getBody());
    }

    @Test
    void deleteLocation() {
        Long id = 1L;

        Location location = new Location();
        location.setId(id);
        location.setName("Tangerang");

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success delete data with ID " + id)
                .data(null)
                .build();

        when(locationService.deleteLocation(id)).thenReturn(expectedResponse);
        ResponseEntity<BaseResponse> actualResponse = locationController.deleteLocation(id);

        assertNull(expectedResponse.getData());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse.getMessage(), actualResponse.getBody().getMessage());
    }

}
