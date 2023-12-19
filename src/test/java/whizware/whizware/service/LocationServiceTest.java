package whizware.whizware.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.location.LocationRequest;
import whizware.whizware.dto.location.LocationResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.repository.LocationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static whizware.whizware.util.TestUtilities.generateLocation;


class LocationServiceTest {
    @InjectMocks
    LocationService locationService;

    @Mock
    LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addLocationTest() {
        Long id = 1L;

        LocationRequest request = new LocationRequest("Serang");

        Location loc = new Location();
        loc.setId(id);
        loc.setName(request.getName());

        LocationResponse data = LocationResponse.builder()
                .id(id)
                .name(loc.getName())
                .build();

        BaseResponse response = BaseResponse.builder()
                .message("Successfully added data!")
                .data(data)
                .build();


        BaseResponse actualResponse = locationService.addLocation(request);
        LocationResponse actualData = (LocationResponse) actualResponse.getData();

        Assertions.assertEquals(response.getMessage(), actualResponse.getMessage());
        // Assertions.assertEquals(data.getId(), actualData.getId());
        Assertions.assertEquals(data.getName(), actualData.getName());
    }

    @Test
    void updateLocationTest() {
        Long id = 1L;
        LocationRequest request = new LocationRequest("Bali");

        Location mockData = new Location();
        mockData.setId(id);
        mockData.setName("Semarang");

        Location updateData = new Location();
        updateData.setId(id);
        updateData.setName(request.getName());

        LocationResponse expectedData = LocationResponse.builder()
                .id(id)
                .name(updateData.getName())
                .build();

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success update data!")
                .data(expectedData)
                .build();

        when(locationRepository.findById(id)).thenReturn(Optional.of(mockData));
        when(locationRepository.save(updateData)).thenReturn(updateData);

        BaseResponse actualResponse = locationService.updateLocation(id, request);
        LocationResponse actualData = (LocationResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getName(), actualData.getName());
    }

    @Test
    void updateLocationWithInvalidIdTest() {
        Long id = 1L;
        LocationRequest request = new LocationRequest("Bali");

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Fail update data with ID " + id)
                .data(null)
                .build();
        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        BaseResponse actualResponse = locationService.updateLocation(id, request);

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertNull(actualResponse.getData());
    }

    @Test
    void getAllLocationTest() {
        List<Location> mockData = new ArrayList<>(
                List.of(
                        generateLocation(1L, "Jakarta"),
                        generateLocation(2L, "Bekasi")
                )
        );

        LocationResponse response1 = new LocationResponse(mockData.get(0).getId(), mockData.get(0).getName());
        LocationResponse response2 = new LocationResponse(mockData.get(1).getId(), mockData.get(1).getName());

        List<LocationResponse> expectedData = new ArrayList<>();
        expectedData.add(response1);
        expectedData.add(response2);

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success get all data")
                .data(expectedData)
                .build();

        when(locationRepository.findAll()).thenReturn(mockData);

        BaseResponse actualResponse = locationService.getAll();
        List<LocationResponse> actualData = (List<LocationResponse>) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.size(), actualData.size());
    }

    @Test
    void getLocationWithIDTest() {
        Long id = 1L;
        Location mockData = generateLocation(id, "Tangerang");

        LocationResponse expectedData = LocationResponse.builder()
                .id(mockData.getId())
                .name(mockData.getName())
                .build();

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success get location with ID " + id + "!")
                .data(expectedData)
                .build();

        when(locationRepository.findById(id)).thenReturn(Optional.of(mockData));

        BaseResponse actualResponse = locationService.getLocById(id);
        LocationResponse actualData = (LocationResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getName(), actualData.getName());
    }

    @Test
    void deleteLocationTest() {
        Long id = 1L;
        Location mockData = generateLocation(id, "Jakarta");

        Location expectedData = generateLocation(
                mockData.getId(),
                mockData.getName()
        );

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success delete data with ID " + id)
                .data(expectedData)
                .build();
        when(locationRepository.findById(id)).thenReturn(Optional.of(mockData));

        BaseResponse actualResponse = locationService.deleteLocation(id);
        Location actualData = (Location) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getName(), actualData.getName());
    }

}