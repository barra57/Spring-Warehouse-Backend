package whizware.whizware.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.receipt.ReceiptRequest;
import whizware.whizware.dto.receipt.ReceiptResponse;
import whizware.whizware.dto.warehouse.WarehouseResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Receipt;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.repository.GoodsRepository;
import whizware.whizware.repository.LocationRepository;
import whizware.whizware.repository.ReceiptRepository;
import whizware.whizware.repository.WarehouseRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static whizware.whizware.util.TestUtilities.*;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTests {

    @InjectMocks
    ReceiptService receiptService;

    @Mock
    ReceiptRepository receiptRepository;

    @Mock
    WarehouseRepository warehouseRepository;

    @Mock
    GoodsRepository goodsRepository;

    ReceiptRequest getRequest() {
        return generateReceiptRequest(
                1L,
                1L,
                2L,
                new BigDecimal(1000000),
                "Alfamart",
                new Date()
        );
    }

    @Test
    void testGetAllReceipts() {
        Warehouse warehouse = generateWarehouse(1L, "A", generateLocation(1L, "Jakarta"));
        Goods goods1 = generateGoods(1L, "Laptop", "Barang Elektronik", 1000000L, 2000000L);
        Goods goods2 = generateGoods(2L, "Handphone", "Barang Elektronik", 100000L, 200000L);
        List<Receipt> mockData = new ArrayList<>(
                List.of(
                        generateReceipt(1L, warehouse, goods1, 1L, new BigDecimal(goods1.getPurchasePrice() * 1L), "", new Date()),
                        generateReceipt(2L, warehouse, goods2, 2L, new BigDecimal(goods2.getPurchasePrice() * 2L), "", new Date())
                )
        );

        String expectedMessage = "Success";
        List<ReceiptResponse> expectedData = new ArrayList<>(
                List.of(
                        generateReceiptResponse(1L, warehouse.getId(), goods1.getId(), 1L, new BigDecimal(goods1.getPurchasePrice() * 1L), "Alfamart", new Date()),
                        generateReceiptResponse(2L, warehouse.getId(), goods2.getId(), 2L, new BigDecimal(goods2.getPurchasePrice() * 2L), "Alfamart", new Date())
                )
        );
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();
        when(receiptRepository.findAll()).thenReturn(mockData);

        BaseResponse actualResponse = receiptService.getAllReceipt();
        List<ReceiptResponse> actualData = (List<ReceiptResponse>) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.size(), actualData.size());
    }

    @Test
    void testGetReceiptWithValidId() {
        Long id = 1L;
        Warehouse warehouse = generateWarehouse(1L, "A", generateLocation(1L, "Jakarta"));
        Goods goods = generateGoods(1L, "Laptop", "Barang Elektronik", 1000000L, 2000000L);
        Receipt mockData = generateReceipt(
                id,
                warehouse,
                goods,
                15L,
                new BigDecimal(goods.getPurchasePrice() * 15L),
                "Alfamart",
                new Date()
        );

        String expectedMessage = "Success";
        ReceiptResponse expectedData = generateReceiptResponse(
                mockData.getId(),
                mockData.getWarehouse().getId(),
                mockData.getGoods().getId(),
                mockData.getQuantity(),
                mockData.getTotalPrice(),
                mockData.getSuplier(),
                mockData.getDate()
        );
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(receiptRepository.findById(id)).thenReturn(Optional.of(mockData));

        BaseResponse actualResponse = receiptService.getReceiptById(id);
        ReceiptResponse actualData = (ReceiptResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getWarehouseId(), actualData.getWarehouseId());
        Assertions.assertEquals(expectedData.getGoodsId(), actualData.getGoodsId());
        Assertions.assertEquals(expectedData.getQuantity(), actualData.getQuantity());
        Assertions.assertEquals(expectedData.getTotalPrice(), actualData.getTotalPrice());
        Assertions.assertEquals(expectedData.getSupplier(), actualData.getSupplier());
        Assertions.assertEquals(expectedData.getDate(), actualData.getDate());
    }

    @Test
    void testGetReceiptWithInvalidId() {
        Long id = 1L;

        String expectedMessage = "Receipt with ID " + id + " not found";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();

        when(receiptRepository.findById(id)).thenReturn(Optional.empty());

        BaseResponse actualResponse = receiptService.getReceiptById(id);

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertNull(actualResponse.getData());
    }

    @Test
    void testSaveReceipt() {
        Long id = 1L;
        ReceiptRequest request = getRequest();

        Warehouse warehouse = generateWarehouse(request.getWarehouseId(), "A", generateLocation(1L, "Jakarta"));
        Goods goods = generateGoods(request.getGoodsId(), "Laptop", "Barang Elektronik", 1000000L, 2000000L);

        Receipt mockData = generateReceipt(
                id,
                warehouse,
                goods,
                15L,
                new BigDecimal(goods.getPurchasePrice() * 15L),
                "Alfamart",
                new Date()
        );

        String expectedMessage = "Receipt successfully added";
        ReceiptResponse expectedData = generateReceiptResponse(
                mockData.getId(),
                mockData.getWarehouse().getId(),
                mockData.getGoods().getId(),
                mockData.getQuantity(),
                mockData.getTotalPrice(),
                mockData.getSuplier(),
                mockData.getDate()
        );
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(warehouseRepository.findById(request.getWarehouseId())).thenReturn(Optional.of(warehouse));
        when(goodsRepository.findById(request.getGoodsId())).thenReturn(Optional.of(goods));

        when(receiptRepository.save(any())).thenReturn(mockData);

        BaseResponse actualResponse = receiptService.saveReceipt(request);
        ReceiptResponse actualData = (ReceiptResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getWarehouseId(), actualData.getWarehouseId());
        Assertions.assertEquals(expectedData.getGoodsId(), actualData.getGoodsId());
        Assertions.assertEquals(expectedData.getQuantity(), actualData.getQuantity());
        Assertions.assertEquals(expectedData.getTotalPrice(), actualData.getTotalPrice());
        Assertions.assertEquals(expectedData.getSupplier(), actualData.getSupplier());
        Assertions.assertEquals(expectedData.getDate(), actualData.getDate());
    }

    @Test
    void testSaveReceiptWithInvalidWarehouseId() {
        ReceiptRequest request = getRequest();

        String expectedMessage = "Warehouse with ID " + request.getWarehouseId() + " not found";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();

        when(warehouseRepository.findById(request.getWarehouseId())).thenReturn(Optional.empty());

        BaseResponse actualResponse = receiptService.saveReceipt(request);

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertNull(actualResponse.getData());
    }

    @Test
    void testSaveReceiptWithInvalidGoodsId() {
        ReceiptRequest request = getRequest();

        Warehouse warehouse = generateWarehouse(request.getWarehouseId(), "A", generateLocation(1L, "Jakarta"));

        String expectedMessage = "Goods with ID " + request.getGoodsId() + " not found";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();

        when(warehouseRepository.findById(request.getWarehouseId())).thenReturn(Optional.of(warehouse));
        when(goodsRepository.findById(request.getGoodsId())).thenReturn(Optional.empty());

        BaseResponse actualResponse = receiptService.saveReceipt(request);

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertNull(actualResponse.getData());
    }

    @Test
    void testUpdateReceipt() {
        Long id = 1L;
        ReceiptRequest request = getRequest();

        Warehouse warehouse = generateWarehouse(request.getWarehouseId(), "A", generateLocation(1L, "Jakarta"));
        Goods goods = generateGoods(request.getGoodsId(), "Laptop", "Barang Elektronik", 1000000L, 2000000L);

        Receipt mockData = generateReceipt(
                id,
                warehouse,
                goods,
                15L,
                new BigDecimal(goods.getPurchasePrice() * 15L),
                "Alfamart",
                new Date()
        );

        when(receiptRepository.findById(id)).thenReturn(Optional.of(mockData));
        when(warehouseRepository.findById(request.getWarehouseId())).thenReturn(Optional.of(warehouse));
        when(goodsRepository.findById(request.getGoodsId())).thenReturn(Optional.of(goods));
        when(receiptRepository.save(any())).thenReturn(mockData);

        String expectedMessage = "Receipt successfully updated";
        ReceiptResponse expectedData = generateReceiptResponse(
                mockData.getId(),
                mockData.getWarehouse().getId(),
                mockData.getGoods().getId(),
                mockData.getQuantity(),
                mockData.getTotalPrice(),
                mockData.getSuplier(),
                mockData.getDate()
        );

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        BaseResponse actualResponse = receiptService.updateReceipt(id, request);
        ReceiptResponse actualData = (ReceiptResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getWarehouseId(), actualData.getWarehouseId());
        Assertions.assertEquals(expectedData.getGoodsId(), actualData.getGoodsId());
        Assertions.assertEquals(expectedData.getQuantity(), actualData.getQuantity());
        Assertions.assertEquals(expectedData.getTotalPrice(), actualData.getTotalPrice());
        Assertions.assertEquals(expectedData.getSupplier(), actualData.getSupplier());
        Assertions.assertEquals(expectedData.getDate(), actualData.getDate());

    }

    @Test
    void testUpdateReceiptWithInvalidReceiptId() {
        Long id = 1L;
        ReceiptRequest request = getRequest();

        when(receiptRepository.findById(id)).thenReturn(Optional.empty());

        String expectedMessage = "Receipt with ID " + request.getGoodsId() + " not found";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();

        BaseResponse actualResponse = receiptService.updateReceipt(id, request);

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertNull(actualResponse.getData());
    }

    @Test
    void testUpdateReceiptWithInvalidWarehouseId() {
        Long id = 1L;
        ReceiptRequest request = getRequest();

        Warehouse warehouse = generateWarehouse(request.getWarehouseId(), "A", generateLocation(1L, "Jakarta"));
        Goods goods = generateGoods(request.getGoodsId(), "Laptop", "Barang Elektronik", 1000000L, 2000000L);

        Receipt mockData = generateReceipt(
                id,
                warehouse,
                goods,
                15L,
                new BigDecimal(goods.getPurchasePrice() * 15L),
                "Alfamart",
                new Date()
        );

        when(receiptRepository.findById(id)).thenReturn(Optional.of(mockData));
        when(warehouseRepository.findById(request.getWarehouseId())).thenReturn(Optional.empty());

        String expectedMessage = "Warehouse with ID " + request.getGoodsId() + " not found";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();

        BaseResponse actualResponse = receiptService.updateReceipt(id, request);

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertNull(actualResponse.getData());
    }

    @Test
    void testUpdateReceiptWithInvalidGoodsId() {
        Long id = 1L;
        ReceiptRequest request = getRequest();

        Warehouse warehouse = generateWarehouse(request.getWarehouseId(), "A", generateLocation(1L, "Jakarta"));
        Goods goods = generateGoods(request.getGoodsId(), "Laptop", "Barang Elektronik", 1000000L, 2000000L);

        Receipt mockData = generateReceipt(
                id,
                warehouse,
                goods,
                15L,
                new BigDecimal(goods.getPurchasePrice() * 15L),
                "Alfamart",
                new Date()
        );

        when(receiptRepository.findById(id)).thenReturn(Optional.of(mockData));
        when(warehouseRepository.findById(request.getWarehouseId())).thenReturn(Optional.of(warehouse));
        when(goodsRepository.findById(request.getGoodsId())).thenReturn(Optional.empty());

        String expectedMessage = "Goods with ID " + request.getGoodsId() + " not found";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(null)
                .build();

        BaseResponse actualResponse = receiptService.updateReceipt(id, request);

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertNull(actualResponse.getData());
    }

//    @Test
//    void testUpdateReceiptWithInvalidReceiptId() {
//        Long id = 1L;
//        ReceiptRequest request = generateReceiptRequest("A", 2L);
//
//        String expectedMessage = "Receipt with ID " + id + " not found";
//        BaseResponse expectedResponse = BaseResponse.builder()
//                .message(expectedMessage)
//                .data(null)
//                .build();
//        when(receiptRepository.findById(id)).thenReturn(Optional.empty());
//
//        BaseResponse actualResponse = receiptService.updateReceipt(id, request);
//
//        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
//        Assertions.assertNull(actualResponse.getData());
//    }
//
//    @Test
//    void testUpdateReceiptWithInvalidLocationId() {
//        Long id = 1L;
//        ReceiptRequest request = generateReceiptRequest("A", 2L);
//        Location mockLocation = generateLocation(1L, "Jakarta");
//        Receipt mockData = generateReceipt(id, "B", mockLocation);
//
//        String expectedMessage = "Location with ID " + id + " not found";
//        BaseResponse expectedResponse = BaseResponse.builder()
//                .message(expectedMessage)
//                .data(null)
//                .build();
//        when(receiptRepository.findById(id)).thenReturn(Optional.of(mockData));
//        when(locationRepository.findById(request.getLocationId())).thenReturn(Optional.empty());
//
//        BaseResponse actualResponse = receiptService.updateReceipt(id, request);
//
//        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
//        Assertions.assertNull(actualResponse.getData());
//    }
//
//    @Test
//    void testDeleteReceipt() {
//        Long id = 1L;
//        Location mockLocation = generateLocation(2L, "Jakarta");
//        Receipt mockData = generateReceipt(id, "B", mockLocation);
//
//        String expectedMessage = "Receipt successfully deleted";
//        Receipt expectedData = generateReceipt(
//                mockData.getId(),
//                mockData.getName(),
//                mockData.getLocation()
//        );
//        BaseResponse expectedResponse = BaseResponse.builder()
//                .message(expectedMessage)
//                .data(expectedData)
//                .build();
//        when(receiptRepository.findById(id)).thenReturn(Optional.of(mockData));
//
//        BaseResponse actualResponse = receiptService.deleteReceipt(id);
//        Receipt actualData = (Receipt) actualResponse.getData();
//
//        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
//        Assertions.assertEquals(expectedData.getId(), actualData.getId());
//        Assertions.assertEquals(expectedData.getName(), actualData.getName());
//        Assertions.assertEquals(expectedData.getLocation().getId(), actualData.getLocation().getId());
//    }
//
//    @Test
//    void testDeleteReceiptWithInvalidId() {
//        Long id = 1L;
//
//        String expectedMessage = "Receipt with ID " + id + " not found";
//        BaseResponse expectedResponse = BaseResponse.builder()
//                .message(expectedMessage)
//                .data(null)
//                .build();
//        when(receiptRepository.findById(id)).thenReturn(Optional.empty());
//
//        BaseResponse actualResponse = receiptService.deleteReceipt(id);
//
//        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
//        Assertions.assertNull(actualResponse.getData());
//    }
//
}
