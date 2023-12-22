package whizware.whizware.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.warehouse.WarehouseRequest;
import whizware.whizware.dto.warehouse.WarehouseResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.service.WarehouseService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static whizware.whizware.util.TestUtilities.*;

@ExtendWith(MockitoExtension.class)
class WarehouseControllerTests {

    @InjectMocks
    WarehouseController warehouseController;

    @Mock
    WarehouseService warehouseService;

    private Warehouse warehouse1;
    private Warehouse warehouse2;

    private WarehouseResponse warehouseResponse1;
    private WarehouseResponse warehouseResponse2;

    private WarehouseRequest warehouseRequest;

    @BeforeEach
    void setUp() {
        this.warehouse1 = new Warehouse(1L, "A", new Location(1L, "Jakarta"));
        this.warehouse2 = new Warehouse(2L, "B", new Location(2L, "Bekasi"));

        this.warehouseRequest = new WarehouseRequest("A", 1L);

        this.warehouseResponse1 = new WarehouseResponse(1L, "A", 1L);
        this.warehouseResponse2 = new WarehouseResponse(2L, "B", 2L);
    }

    @Test
    void getAllWarehouses() {

        List<WarehouseResponse> data = new ArrayList<>();
        data.add(warehouseResponse1);
        data.add(warehouseResponse2);

        String expectedMessage = "Success";

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(data)
                .build();

        when(warehouseService.getAllWarehouses()).thenReturn(expectedResponse);
        ResponseEntity<BaseResponse> actualResponse = warehouseController.getAll();

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void getWarehouseById() {
        String expectedMessage = "Success";
        WarehouseResponse expectedData = warehouseResponse1;

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(warehouseService.getWarehouseById(warehouse1.getId())).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = warehouseController.getById(warehouse1.getId());

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void saveWarehouse() {
        WarehouseRequest request = warehouseRequest;

        String expectedMessage = "Warehouse succesfully added";
        WarehouseResponse expectedData = warehouseResponse1;

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(warehouseService.saveWarehouse(request)).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = warehouseController.save(request);

        Assertions.assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void updateWarehouse() {
        WarehouseRequest request = warehouseRequest;

        String expectedMessage = "Warehouse succesfully updated!";
        WarehouseResponse expectedData = warehouseResponse2;

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(warehouseService.updateWarehouse(warehouse2.getId(), request)).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = warehouseController.updated(warehouse2.getId(), request);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void deleteWarehouse() {
        String expectedMessage = "Warehouse successfully deleted!";
        Warehouse expectedData = warehouse1;
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(warehouseService.deleteWarehouse(warehouse1.getId())).thenReturn(expectedResponse);
        ResponseEntity<BaseResponse> actualResponse = warehouseController.delete(warehouse1.getId());

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

}