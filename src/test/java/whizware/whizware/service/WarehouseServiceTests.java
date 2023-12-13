package whizware.whizware.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.warehouse.WarehouseRequest;
import whizware.whizware.dto.warehouse.WarehouseResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.repository.LocationRepository;
import whizware.whizware.repository.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static whizware.whizware.util.TestUtilities.*;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTests {

    @InjectMocks
    WarehouseService warehouseService;

    @Mock
    WarehouseRepository warehouseRepository;

    @Mock
    LocationRepository locationRepository;

    @Test
    void testGetAllWarehouses() {
        List<Warehouse> mockData = new ArrayList<>(
                List.of(
                        generateWarehouse(1L, "A", generateLocation(1L, "Jakarta")),
                        generateWarehouse(1L, "A", generateLocation(2L, "Bekasi"))
                )
        );

        String expectedMessage = "berhasil";
        List<WarehouseResponse> expectedData = new ArrayList<>(
                List.of(
                        generateWarehouseResponse(mockData.get(0).getId(), mockData.get(0).getName(), mockData.get(0).getLocation().getId()),
                        generateWarehouseResponse(mockData.get(1).getId(), mockData.get(1).getName(), mockData.get(1).getLocation().getId())
                )
        );
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();
        when(warehouseRepository.findAll()).thenReturn(mockData);

        BaseResponse actualResponse = warehouseService.getAllWarehouses();
        List<WarehouseResponse> actualData = (List<WarehouseResponse>) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.size(), actualData.size());
    }

    @Test
    void testGetWarehouseWithValidId() {
        Long id = 1L;
        Warehouse mockData = generateWarehouse(id, "A", generateLocation(1L, "Jakarta"));

        String expectedMessage = "berhasil";
        WarehouseResponse expectedData = generateWarehouseResponse(mockData.getId(), mockData.getName(), mockData.getLocation().getId());
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();
        when(warehouseRepository.findById(id)).thenReturn(Optional.of(mockData));

        BaseResponse actualResponse = warehouseService.getWarehouseById(id);
        WarehouseResponse actualData = (WarehouseResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getName(), actualData.getName());
        Assertions.assertEquals(expectedData.getLocationId(), actualData.getLocationId());
    }

    @Test
    void testGetWarehouseWithInvalidId() {
        Long id = 1L;

        String expectedMessage = "Warehouse with ID " + id + " not found";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();
        when(warehouseRepository.findById(id)).thenReturn(Optional.empty());

        BaseResponse actualResponse = warehouseService.getWarehouseById(id);

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertNull(actualResponse.getData());
    }

    @Test
    void testSaveWarehouse() {
        Long id = 1L;
        WarehouseRequest request = generateWarehouseRequest("A", 2L);
        Location mockLocation = generateLocation(request.getLocationId(), "Jakarta");
        Warehouse mockData = generateWarehouse(id, request.getName(), mockLocation);

        String expectedMessage = "Warehouse successfully added";
        WarehouseResponse expectedData = generateWarehouseResponse(
                mockData.getId(),
                mockData.getName(),
                mockData.getLocation().getId()
        );
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();
        when(locationRepository.findById(request.getLocationId())).thenReturn(Optional.of(mockLocation));
        when(warehouseRepository.save(any())).thenReturn(mockData);

        BaseResponse actualResponse = warehouseService.saveWarehouse(request);
        WarehouseResponse actualData = (WarehouseResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getName(), actualData.getName());
        Assertions.assertEquals(expectedData.getLocationId(), actualData.getLocationId());
    }

    @Test
    void testSaveWarehouseWithInvalidLocationId() {
        Long id = 1L;
        WarehouseRequest request = generateWarehouseRequest("A", 2L);
        Location mockLocation = generateLocation(request.getLocationId(), "Jakarta");
        Warehouse mockData = generateWarehouse(id, request.getName(), mockLocation);

        String expectedMessage = "Location with ID " + request.getLocationId() + " is not found";
        WarehouseResponse expectedData = generateWarehouseResponse(
                mockData.getId(),
                mockData.getName(),
                mockData.getLocation().getId()
        );
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(locationRepository.findById(request.getLocationId())).thenReturn(Optional.empty());

        BaseResponse actualResponse = warehouseService.saveWarehouse(request);

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertNull(actualResponse.getData());
    }

    @Test
    void testUpdateWarehouse() {
        Long id = 1L;
        WarehouseRequest request = generateWarehouseRequest("A", 2L);
        Location mockLocation = generateLocation(request.getLocationId(), "Jakarta");
        Warehouse mockData = generateWarehouse(id, "B", mockLocation);
        Warehouse mockDataUpdated = generateWarehouse(id, request.getName(), mockLocation);

        String expectedMessage = "Warehouse successfully updated";
        WarehouseResponse expectedData = generateWarehouseResponse(
                mockDataUpdated.getId(),
                mockDataUpdated.getName(),
                mockDataUpdated.getLocation().getId()
        );
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();
        when(warehouseRepository.findById(id)).thenReturn(Optional.of(mockData));
        when(locationRepository.findById(request.getLocationId())).thenReturn(Optional.of(mockLocation));
        when(warehouseRepository.save(any())).thenReturn(mockDataUpdated);

        BaseResponse actualResponse = warehouseService.updateWarehouse(id, request);
        WarehouseResponse actualData = (WarehouseResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getName(), actualData.getName());
        Assertions.assertEquals(expectedData.getLocationId(), actualData.getLocationId());
    }

    @Test
    void testUpdateWarehouseWithInvalidWarehouseId() {
        Long id = 1L;
        WarehouseRequest request = generateWarehouseRequest("A", 2L);

        String expectedMessage = "Warehouse with ID " + id + " not found";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();
        when(warehouseRepository.findById(id)).thenReturn(Optional.empty());

        BaseResponse actualResponse = warehouseService.updateWarehouse(id, request);

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertNull(actualResponse.getData());
    }

    @Test
    void testUpdateWarehouseWithInvalidLocationId() {
        Long id = 1L;
        WarehouseRequest request = generateWarehouseRequest("A", 2L);
        Location mockLocation = generateLocation(1L, "Jakarta");
        Warehouse mockData = generateWarehouse(id, "B", mockLocation);

        String expectedMessage = "Location with ID " + id + " not found";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();
        when(warehouseRepository.findById(id)).thenReturn(Optional.of(mockData));
        when(locationRepository.findById(request.getLocationId())).thenReturn(Optional.empty());

        BaseResponse actualResponse = warehouseService.updateWarehouse(id, request);

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertNull(actualResponse.getData());
    }

    @Test
    void testDeleteWarehouse() {
        Long id = 1L;
        Location mockLocation = generateLocation(2L, "Jakarta");
        Warehouse mockData = generateWarehouse(id, "B", mockLocation);

        String expectedMessage = "Warehouse successfully deleted";
        Warehouse expectedData = generateWarehouse(
                mockData.getId(),
                mockData.getName(),
                mockData.getLocation()
        );
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();
        when(warehouseRepository.findById(id)).thenReturn(Optional.of(mockData));

        BaseResponse actualResponse = warehouseService.deleteWarehouse(id);
        Warehouse actualData = (Warehouse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getName(), actualData.getName());
        Assertions.assertEquals(expectedData.getLocation().getId(), actualData.getLocation().getId());
    }

    @Test
    void testDeleteWarehouseWithInvalidId() {
        Long id = 1L;

        String expectedMessage = "Warehouse with ID " + id + " not found";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();
        when(warehouseRepository.findById(id)).thenReturn(Optional.empty());

        BaseResponse actualResponse = warehouseService.deleteWarehouse(id);

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertNull(actualResponse.getData());
    }

}
