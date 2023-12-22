package whizware.whizware.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.stock.StockRequest;
import whizware.whizware.dto.stock.StockResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Stock;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.exception.ConflictException;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.LocationRepository;
import whizware.whizware.repository.StockRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTests {

    @InjectMocks
    StockService stockService;

    @Mock
    StockRepository stockRepository;

    @Mock
    LocationRepository locationRepository;


    private Warehouse warehouse;
    private Goods goods1;
    private Goods goods2;

    private Stock stock1;
    private Stock stock2;

    private StockResponse stockResponse1;
    private StockResponse stockResponse2;

    private Long id;

    @BeforeEach
    void setUp() {
        this.warehouse = new Warehouse(1L, "admin", new Location(1L, "Jakarta"));

        this.goods1 = new Goods(1L, "Laptop" , new BigDecimal(1000000L), new BigDecimal(2000000L), "Barang Elektronik");
        this.goods2 = new Goods(2L, "Monitor", new BigDecimal(3000000L), new BigDecimal(4000000L), "Barang Elektronik");

        this.stock1 = new Stock(1L, warehouse, goods1, 5L);
        this.stock2 = new Stock(2L, warehouse, goods2, 10L);

        this.stockResponse1 = new StockResponse(1L, 1L, 1L, 5L);
        this.stockResponse2 = new StockResponse(2L, 1L, 2L, 10L);

        this.id = stock1.getId();
    }

    @Test
    void testGetAllStocks() {
        List<Stock> mockData = new ArrayList<>( );
        mockData.add(stock1);
        mockData.add(stock2);

        String expectedMessage = "Success";
        List<StockResponse> expectedData = new ArrayList<>( );
        expectedData.add(stockResponse1);
        expectedData.add(stockResponse2);

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();
        when(stockRepository.findAll()).thenReturn(mockData);

        BaseResponse actualResponse = stockService.getAllStock();
        List<StockResponse> actualData = (List<StockResponse>) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.size(), actualData.size());
    }

    @Test
    void testGetAllStocksWithEmptyList() {
        List<Stock> mockData = new ArrayList<>();

        when(stockRepository.findAll()).thenReturn(mockData);

        Assertions.assertThrows(NoContentException.class, () -> stockService.getAllStock()); // This will throw
    }

    @Test
    void testGetStockWithValidId() {
        String expectedMessage = "Success";
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(stockResponse1)
                .build();
        when(stockRepository.findById(stock1.getId())).thenReturn(Optional.of(stock1));

        BaseResponse actualResponse = stockService.getStockById(stock1.getId());
        StockResponse actualData = (StockResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(stockResponse1.getId(), actualData.getId());
    }

    @Test
    void testGetStockWithInvalidId() {
        when(stockRepository.findById(stock1.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> stockService.getStockById(id));
    }

    @Test
    void testSubtractStock() {
        List<Stock> mockData = new ArrayList<>();
        mockData.add(stock1);
        when(stockRepository.findByWarehouseIdAndGoodsId(warehouse.getId(), goods1.getId())).thenReturn(mockData);
        Assertions.assertDoesNotThrow(() -> stockService.subtractStock(warehouse, goods1, 1L));
    }

    @Test
    void testSubtractStockWithNotEnoughStock() {
        List<Stock> mockData = new ArrayList<>();
        mockData.add(stock1);
        when(stockRepository.findByWarehouseIdAndGoodsId(warehouse.getId(), goods1.getId())).thenReturn(mockData);
        Assertions.assertThrows(ConflictException.class, () -> stockService.subtractStock(warehouse, goods1, 10L));
    }

    @Test
    void testAddStockWhenStockIsAlreadyExist() {
        List<Stock> mockData = new ArrayList<>();
        mockData.add(stock1);
        when(stockRepository.findByWarehouseIdAndGoodsId(warehouse.getId(), goods1.getId())).thenReturn(mockData);
        Assertions.assertDoesNotThrow(() -> stockService.addStock(warehouse, goods1, 5L));
    }

    @Test
    void testAddStockWhenStockIsNotAlreadyExist() {
        List<Stock> mockData = new ArrayList<>();
        when(stockRepository.findByWarehouseIdAndGoodsId(warehouse.getId(), goods1.getId())).thenReturn(mockData);
        Assertions.assertDoesNotThrow(() -> stockService.addStock(warehouse, goods1, 5L));
    }

    @Test
    void testGetStockByWarehouseId() {
        List<Stock> mockData = new ArrayList<>();
        mockData.add(stock1);

        String expectedMessage = "Success";
        List<StockResponse> expectedData = new ArrayList<>();
        expectedData.add(stockResponse1);

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();
        when(stockRepository.findByWarehouseId(warehouse.getId())).thenReturn(mockData);

        BaseResponse actualResponse = stockService.getStockByWarehouseId(warehouse.getId());
        List<StockResponse> actualData = (List<StockResponse>) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.size(), actualData.size());
        Assertions.assertEquals(expectedData.get(0).getId(), actualData.get(0).getId());
        Assertions.assertEquals(expectedData.get(0).getWarehouseId(), actualData.get(0).getWarehouseId());
        Assertions.assertEquals(expectedData.get(0).getGoodsId(), actualData.get(0).getGoodsId());
        Assertions.assertEquals(expectedData.get(0).getQuantity(), actualData.get(0).getQuantity());
    }

    @Test
    void testGetStockByWarehouseIdWithEmptyList() {
        Long id = warehouse.getId();
        List<Stock> mockData = new ArrayList<>();
        when(stockRepository.findByWarehouseId(warehouse.getId())).thenReturn(mockData);
        Assertions.assertThrows(NoContentException.class, () -> stockService.getStockByWarehouseId(id));
    }

}
