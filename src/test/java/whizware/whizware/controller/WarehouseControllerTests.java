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

    @Test
    void getAllWarehouses() {

        List<WarehouseResponse> data = new ArrayList<>(
                List.of(
                        generateWarehouseResponse(1L, "A", 2L),
                        generateWarehouseResponse(2L, "B", 2L),
                        generateWarehouseResponse(3L, "C", 2L)
                )
        );

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("berhasil")
                .data(data)
                .build();

        when(warehouseService.getAllWarehouses()).thenReturn(expectedResponse);
        ResponseEntity<BaseResponse> actualResponse = warehouseController.getAll();

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void getWarehouseById() {
        Long id = 1L;

        String expectedMessage = "berhasil";
        WarehouseResponse expectedData = generateWarehouseResponse(1L, "Jakarta Timur", 2L);

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(warehouseService.getWarehouseById(id)).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = warehouseController.getById(id);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void getWarehouseByInvalidId() {
        Long id = 1L;

        String expectedMessage = "Warehouse with ID " + id + " not found";

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();

        when(warehouseService.getWarehouseById(id)).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = warehouseController.getById(id);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNull(actualResponse.getBody().getData());
    }

    @Test
    void saveWarehouse() {
        Long id = 1L;

        WarehouseRequest request = generateWarehouseRequest("A", 2L);

        String expectedMessage = "Warehouse succesfully added";
        WarehouseResponse expectedData = generateWarehouseResponse(id, request.getName(), request.getLocationId());

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
    void saveWarehouseWithInvalidLocationId() {
        Long id = 1L;
        WarehouseRequest request = generateWarehouseRequest("A", 2L);

        String expectedMessage = "Location with ID " + id + " not found!";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();

        when(warehouseService.saveWarehouse(request)).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = warehouseController.save(request);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNull(actualResponse.getBody().getData());
    }

    @Test
    void updateWarehouse() {
        Long id = 1L;
        WarehouseRequest request = generateWarehouseRequest("A", 2L);

        String expectedMessage = "Warehouse succesfully updated!";
        WarehouseResponse expectedData = generateWarehouseResponse(id, request.getName(), request.getLocationId());

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(warehouseService.updateWarehouse(id, request)).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = warehouseController.updated(id, request);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void updateWarehouseWithInvalidId() {
        Long id = 1L;
        WarehouseRequest request = generateWarehouseRequest("A", 2L);

        String expectedMessage = "Warehouse with id " + id + " not found!";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();

        when(warehouseService.updateWarehouse(id, request)).thenReturn(expectedResponse);
        ResponseEntity<BaseResponse> actualResponse = warehouseController.updated(id, request);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNull(actualResponse.getBody().getData());
    }

    @Test
    void deleteWarehouse() {
        Long id = 1L;

        Location location = new Location();
        location.setId(1L);
        location.setName("Jakarta");

        String expectedMessage = "Warehouse successfully deleted!";
        Warehouse expectedData = generateWarehouse(id, "A", location);
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(warehouseService.deleteWarehouse(id)).thenReturn(expectedResponse);
        ResponseEntity<BaseResponse> actualResponse = warehouseController.delete(id);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void deleteWarehouseWithInvalidId() {
        Long id = 1L;

        String expectedMessage = "Warehouse with id " + id + " not found!";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();

        when(warehouseService.deleteWarehouse(id)).thenReturn(expectedResponse);
        ResponseEntity<BaseResponse> actualResponse = warehouseController.delete(id);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNull(actualResponse.getBody().getData());
    }

}