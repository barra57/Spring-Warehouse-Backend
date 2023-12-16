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
import whizware.whizware.dto.delivery.DeliveryRequest;
import whizware.whizware.dto.delivery.DeliveryResponse;
import whizware.whizware.service.DeliveryService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class DeliveryControllerTests {
    @InjectMocks
    DeliveryController deliveryController;

    @Mock
    DeliveryService deliveryService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllDelivery() {
        List<DeliveryResponse> expectedResponse = new ArrayList<>();

        DeliveryResponse response1 = DeliveryResponse.builder()
                .id(1L)
                .warehouseId(1L)
                .storeId(1L)
                .goodsId(1L)
                .qty(5L)
                .status("Delivered")
                .date(new Date(2023, 12, 12))
                .build();
        DeliveryResponse response2 = DeliveryResponse.builder()
                .id(2L)
                .warehouseId(1L)
                .storeId(1L)
                .goodsId(1L)
                .qty(5L)
                .status("Delivered")
                .date(new Date(2023, 12, 12))
                .build();

        expectedResponse.add(response1);
        expectedResponse.add(response2);

        BaseResponse response = BaseResponse.builder()
                .message("status")
                .data(expectedResponse)
                .build();

        when(deliveryService.getAllDelivery()).thenReturn(response);
        ResponseEntity<BaseResponse> actualResponse = deliveryController.getAll(null);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(response.getData(), actualResponse.getBody().getData());
    }

    @Test
    void testGetDeliveryByValidId() {
        Long id = 1L;

        DeliveryResponse expectedResponse = DeliveryResponse.builder()
                .id(id)
                .warehouseId(1L)
                .storeId(1L)
                .goodsId(1L)
                .qty(3L)
                .status("Delivered")
                .date(new Date(2023, 12, 12))
                .build();

        BaseResponse response = BaseResponse.builder()
                .message("Status")
                .data(expectedResponse)
                .build();

        when(deliveryService.getDeliveryById(id)).thenReturn(response);
        ResponseEntity<BaseResponse> actualResponse = deliveryController.getDeliveryById(id);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(response.getData(), actualResponse.getBody().getData());
    }
    @Test
    void testGetDeliveryByInvalidId() {
        Long id = 99L;

        BaseResponse response = BaseResponse.builder()
                .message("Status")
                .data(null)
                .build();

        when(deliveryService.getDeliveryById(id)).thenReturn(response);
        ResponseEntity<BaseResponse> actualResponse = deliveryController.getDeliveryById(id);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        Assertions.assertNull(response.getData());
    }

    @Test
    void testSaveDeliveryWithValidData() {

        Long id = 1L;

        DeliveryRequest request = new DeliveryRequest();
        request.setWarehouseId(1L);
        request.setStoreId(1L);
        request.setGoodsId(1L);
        request.setQty(3L);
        request.setStatus("Delivered");
        request.setDate(new Date(2023, 12, 12));

        DeliveryResponse expectedResponse = DeliveryResponse.builder()
                .id(id)
                .warehouseId(request.getWarehouseId())
                .storeId(request.getStoreId())
                .goodsId(request.getGoodsId())
                .qty(request.getQty())
                .status(request.getStatus())
                .date(request.getDate())
                .build();

        BaseResponse response = BaseResponse.builder()
                .message("Status")
                .data(expectedResponse)
                .build();

        when(deliveryService.saveDelivery(request)).thenReturn(response);
        ResponseEntity<BaseResponse> actualResponse = deliveryController.saveDelivery(request);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(response.getData(), actualResponse.getBody().getData());
    }

    @Test
    void testSaveDeliveryWithInvalidData() {
        DeliveryRequest request = new DeliveryRequest();

        BaseResponse response = BaseResponse.builder()
                .message("Status")
                .data(null)
                .build();

        when(deliveryService.saveDelivery(request)).thenReturn(response);
        ResponseEntity<BaseResponse> actualResponse = deliveryController.saveDelivery(request);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        Assertions.assertNull(response.getData());

    }

    @Test
    void testDeleteDeliveryWithValidId() {
        Long id = 1L;

        DeliveryResponse expectedResponse = DeliveryResponse.builder()
                .id(id)
                .warehouseId(1L)
                .storeId(1L)
                .goodsId(1L)
                .qty(3L)
                .status("Delivered")
                .date(new Date(2023, 12, 12))
                .build();

        BaseResponse response = BaseResponse.builder()
                .message("Status")
                .data(expectedResponse)
                .build();

        when(deliveryService.deleteDelivery(id)).thenReturn(response);
        ResponseEntity<BaseResponse> actualResponse = deliveryController.deleteDelivery(id);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(response.getData(), actualResponse.getBody().getData());
    }

    @Test
    void testDeleteDeliveryWithInvalidId() {
        Long id = 9999L;

        BaseResponse response = BaseResponse.builder()
                .message("Status")
                .data(null)
                .build();

        when(deliveryService.deleteDelivery(id)).thenReturn(response);
        ResponseEntity<BaseResponse> actualResponse = deliveryController.deleteDelivery(id);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        Assertions.assertNull(response.getData());
    }

    @Test
    void testUpdateDeliveryWithValidData() {
        Long id = 1L;

        DeliveryRequest request = new DeliveryRequest();
        request.setWarehouseId(1L);
        request.setStoreId(1L);
        request.setGoodsId(1L);
        request.setQty(3L);
        request.setStatus("Delivered");
        request.setDate(new Date(2023, 12, 12));

        DeliveryResponse expectedResponse = DeliveryResponse.builder()
                .id(id)
                .warehouseId(request.getWarehouseId())
                .storeId(request.getStoreId())
                .goodsId(request.getGoodsId())
                .qty(request.getQty())
                .status(request.getStatus())
                .date(request.getDate())
                .build();

        BaseResponse response = BaseResponse.builder()
                .message("Status")
                .data(expectedResponse)
                .build();

        when(deliveryService.updateDelivery(id, request)).thenReturn(response);
        ResponseEntity<BaseResponse> actualResponse = deliveryController.updateDelivery(id, request);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(response.getData(), actualResponse.getBody().getData());
    }

    @Test
    void testUpdateDeliveryWithInvalidData() {
        Long id = 999L;
        DeliveryRequest request = new DeliveryRequest();

        BaseResponse response = BaseResponse.builder()
                .message("Status")
                .data(null)
                .build();

        when(deliveryService.updateDelivery(id, request)).thenReturn(response);
        ResponseEntity<BaseResponse> actualResponse = deliveryController.updateDelivery(id, request);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        Assertions.assertNull(response.getData());
    }
}
