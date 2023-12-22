//package whizware.whizware.controller;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import whizware.whizware.dto.BaseResponse;
//import whizware.whizware.dto.receipt.ReceiptRequest;
//import whizware.whizware.dto.receipt.ReceiptResponse;
//import whizware.whizware.service.ReceiptService;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static whizware.whizware.util.TestUtilities.*;
//
//@ExtendWith(MockitoExtension.class)
//class ReceiptControllerTests {
//
//    @InjectMocks
//    ReceiptController receiptController;
//
//    @Mock
//    ReceiptService receiptService;
//
//    @Test
//    void getAllReceipt() {
//        String expectedMessage = "Success";
//        List<ReceiptResponse> expectedData = new ArrayList<>(
//                List.of(
//                        generateReceiptResponse(1L, 1L, 1L, 250L, new BigDecimal(100000), "Alfamart", new Date()),
//                        generateReceiptResponse(2L, 1L, 2L, 50L, new BigDecimal(100000), "Alfamart", new Date()),
//                        generateReceiptResponse(3L, 1L, 3L, 100L, new BigDecimal(100000), "Alfamart", new Date())
//                )
//        );
//        BaseResponse expectedResponse = BaseResponse.builder()
//                .message(expectedMessage)
//                .data(expectedData)
//                .build();
//
//        when(receiptService.getAllReceipt()).thenReturn(expectedResponse);
//
//        ResponseEntity<BaseResponse> actualResponse = receiptController.getAll(null);
//
//        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
//        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getBody().getMessage());
//        Assertions.assertNotNull(actualResponse.getBody().getData());
//    }
//
//    @Test
//    void getReceiptById() {
//        Long id = 1L;
//
//        String expectedMessage = "Success";
//        ReceiptResponse expectedData = generateReceiptResponse(1L, 1L, 1L, 250L, new BigDecimal(100000), "Alfamart", new Date());
//
//        BaseResponse expectedResponse = BaseResponse.builder()
//                .message(expectedMessage)
//                .data(expectedData)
//                .build();
//
//        when(receiptService.getReceiptById(id)).thenReturn(expectedResponse);
//
//        ResponseEntity<BaseResponse> actualResponse = receiptController.getById(id);
//
//        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
//        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
//        Assertions.assertNotNull(actualResponse.getBody().getData());
//    }
//
//    @Test
//    void getReceiptByInvalidId() {
//        Long id = 1L;
//
//        String expectedMessage = "Receipt with ID " + id + " not found";
//
//        BaseResponse expectedResponse = BaseResponse.builder()
//                .message(expectedMessage)
//                .data(null)
//                .build();
//
//        when(receiptService.getReceiptById(id)).thenReturn(expectedResponse);
//
//        ResponseEntity<BaseResponse> actualResponse = receiptController.getById(id);
//
//        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
//        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
//        Assertions.assertNull(actualResponse.getBody().getData());
//    }
//
//    @Test
//    void saveReceipt() {
//        Long id = 1L;
//
//        ReceiptRequest request = generateReceiptRequest(1L, 1L, 250L, new BigDecimal(100000), "Alfamart", new Date());
//
//        String expectedMessage = "Receipt succesfully added";
//        ReceiptResponse expectedData = generateReceiptResponse(
//                id,
//                request.getWarehouseId(),
//                request.getGoodsId(),
//                request.getQuantity(),
//                request.getTotalPrice(),
//                request.getSuplier(),
//                request.getDate()
//        );
//
//        BaseResponse expectedResponse = BaseResponse.builder()
//                .message(expectedMessage)
//                .data(expectedData)
//                .build();
//
//        when(receiptService.saveReceipt(request)).thenReturn(expectedResponse);
//
//        ResponseEntity<BaseResponse> actualResponse = receiptController.save(request);
//
//        Assertions.assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
//        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
//        Assertions.assertNotNull(actualResponse.getBody().getData());
//    }
//
//    @Test
//    void saveReceiptWithInvalidData() {
//        Long id = 1L;
//        ReceiptRequest request = generateReceiptRequest(1L, 1L, 250L, new BigDecimal(100000), "Alfamart", new Date());
//
//        String expectedMessage = "Location with ID " + id + " not found!";
//        BaseResponse expectedResponse = BaseResponse.builder()
//                .message(expectedMessage)
//                .data(null)
//                .build();
//
//        when(receiptService.saveReceipt(request)).thenReturn(expectedResponse);
//
//        ResponseEntity<BaseResponse> actualResponse = receiptController.save(request);
//
//        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
//        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
//        Assertions.assertNull(actualResponse.getBody().getData());
//    }
//
//    @Test
//    void updateReceipt() {
//        Long id = 1L;
//        ReceiptRequest request = generateReceiptRequest(1L, 1L, 250L, new BigDecimal(100000), "Alfamart", new Date());
//
//        String expectedMessage = "Receipt succesfully updated!";
//        ReceiptResponse expectedData = generateReceiptResponse(
//                id,
//                request.getWarehouseId(),
//                request.getGoodsId(),
//                request.getQuantity(),
//                request.getTotalPrice(),
//                request.getSuplier(),
//                request.getDate()
//        );
//
//        BaseResponse expectedResponse = BaseResponse.builder()
//                .message(expectedMessage)
//                .data(expectedData)
//                .build();
//
//        when(receiptService.updateReceipt(id, request)).thenReturn(expectedResponse);
//
//        ResponseEntity<BaseResponse> actualResponse = receiptController.update(id, request);
//
//        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
//        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
//        Assertions.assertNotNull(actualResponse.getBody().getData());
//    }
//
//    @Test
//    void updateReceiptWithInvalidId() {
//        Long id = 1L;
//        ReceiptRequest request = generateReceiptRequest(1L, 1L, 250L, new BigDecimal(100000), "Alfamart", new Date());
//
//        String expectedMessage = "Receipt with id " + id + " not found!";
//        BaseResponse expectedResponse = BaseResponse.builder()
//                .message(expectedMessage)
//                .data(null)
//                .build();
//
//        when(receiptService.updateReceipt(id, request)).thenReturn(expectedResponse);
//        ResponseEntity<BaseResponse> actualResponse = receiptController.update(id, request);
//
//        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
//        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
//        Assertions.assertNull(actualResponse.getBody().getData());
//    }
//
//}