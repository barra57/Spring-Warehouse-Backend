package whizware.whizware.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
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

    private Warehouse warehouse1;
    private Warehouse warehouse2;

    private WarehouseResponse warehouseResponse1;
    private WarehouseResponse warehouseResponse2;

    private WarehouseRequest warehouseRequest;

    private Location location;

    private Long id;

    @BeforeEach
    void setUp() {
        this.warehouse1 = new Warehouse(1L, "A", new Location(1L, "Jakarta"));
        this.warehouse2 = new Warehouse(2L, "B", new Location(2L, "Bekasi"));

        this.warehouseRequest = new WarehouseRequest("A", 1L);

        this.warehouseResponse1 = new WarehouseResponse(1L, "A", 1L);
        this.warehouseResponse2 = new WarehouseResponse(2L, "B", 2L);

        this.location = new Location(1L, "Jakarta");

        this.id = warehouse1.getId();
    }

    @Test
    void testGetAllWarehouses() {
        List<Warehouse> mockData = new ArrayList<>( );
        mockData.add(warehouse1);
        mockData.add(warehouse2);

        String expectedMessage = "Success";
        List<WarehouseResponse> expectedData = new ArrayList<>( );
        expectedData.add(warehouseResponse1);
        expectedData.add(warehouseResponse2);

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
    void testGetAllWarehousesWithEmptyList() {
        List<Warehouse> mockData = new ArrayList<>();

        when(warehouseRepository.findAll()).thenReturn(mockData);

        Assertions.assertThrows(NoContentException.class, () -> warehouseService.getAllWarehouses()); // This will throw
    }

    @Test
    void testGetWarehouseWithValidId() {
        String expectedMessage = "Success";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(warehouseResponse1)
                .build();
        when(warehouseRepository.findById(warehouse1.getId())).thenReturn(Optional.of(warehouse1));

        BaseResponse actualResponse = warehouseService.getWarehouseById(warehouse1.getId());
        WarehouseResponse actualData = (WarehouseResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(warehouseResponse1.getId(), actualData.getId());
        Assertions.assertEquals(warehouseResponse1.getName(), actualData.getName());
        Assertions.assertEquals(warehouseResponse1.getLocationId(), actualData.getLocationId());
    }

    @Test
    void testGetWarehouseWithInvalidId() {
        when(warehouseRepository.findById(warehouse1.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> warehouseService.getWarehouseById(id));
    }

    @Test
    void testSaveWarehouse() {
        String expectedMessage = "Warehouse successfully added";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(warehouseResponse1)
                .build();
        when(locationRepository.findById(warehouseRequest.getLocationId())).thenReturn(Optional.of(location));
        when(warehouseRepository.save(any())).thenReturn(warehouse1);

        BaseResponse actualResponse = warehouseService.saveWarehouse(warehouseRequest);
        WarehouseResponse actualData = (WarehouseResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(warehouseResponse1.getId(), actualData.getId());
        Assertions.assertEquals(warehouseResponse1.getName(), actualData.getName());
        Assertions.assertEquals(warehouseResponse1.getLocationId(), actualData.getLocationId());
    }

    @Test
    void testSaveWarehouseWithInvalidLocationId() {
        WarehouseRequest request = warehouseRequest;
        when(locationRepository.findById(request.getLocationId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> warehouseService.saveWarehouse(request));
    }

    @Test
    void testUpdateWarehouse() {
        String expectedMessage = "Warehouse successfully updated";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(warehouseResponse2)
                .build();
        when(warehouseRepository.findById(warehouse2.getId())).thenReturn(Optional.of(warehouse2));
        when(locationRepository.findById(warehouse2.getLocation().getId())).thenReturn(Optional.of(location));
        when(warehouseRepository.save(any())).thenReturn(warehouse1);

        BaseResponse actualResponse = warehouseService.updateWarehouse(warehouse2.getId(), warehouseRequest);
        WarehouseResponse actualData = (WarehouseResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(warehouseResponse1.getId(), actualData.getId());
        Assertions.assertEquals(warehouseResponse1.getName(), actualData.getName());
        Assertions.assertEquals(warehouseResponse1.getLocationId(), actualData.getLocationId());
    }

    @Test
    void testUpdateWarehouseWithInvalidWarehouseId() {
        WarehouseRequest request = generateWarehouseRequest("A", 2L);

        when(warehouseRepository.findById(warehouse1.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> warehouseService.updateWarehouse(id, request));
    }

    @Test
    void testUpdateWarehouseWithInvalidLocationId() {
        when(warehouseRepository.findById(warehouse1.getId())).thenReturn(Optional.of(warehouse1));
        when(locationRepository.findById(warehouseRequest.getLocationId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> warehouseService.updateWarehouse(id, warehouseRequest));
    }

    @Test
    void testDeleteWarehouse() {
        String expectedMessage = "Warehouse successfully deleted";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(warehouse1)
                .build();
        when(warehouseRepository.findById(warehouse1.getId())).thenReturn(Optional.of(warehouse1));

        BaseResponse actualResponse = Assertions.assertDoesNotThrow(() -> warehouseService.deleteWarehouse(warehouse1.getId()));

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    }

    @Test
    void testDeleteWarehouseWithInvalidId() {
        when(warehouseRepository.findById(warehouse1.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> warehouseService.deleteWarehouse(id));
    }

}
