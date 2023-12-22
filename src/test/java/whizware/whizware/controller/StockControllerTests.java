package whizware.whizware.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.stock.StockResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Stock;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.service.StockService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockControllerTests {

    @InjectMocks
    StockController stockController;

    @Mock
    StockService stockService;

    private Warehouse warehouse;
    private Goods goods1;
    private Goods goods2;

    private Stock stock1;
    private Stock stock2;

    private StockResponse stockResponse1;
    private StockResponse stockResponse2;


    @BeforeEach
    void setUp() {
        this.warehouse = new Warehouse(1L, "admin", new Location(1L, "Jakarta"));

        this.goods1 = new Goods(1L, "Laptop" , new BigDecimal(1000000L), new BigDecimal(2000000L), "Barang Elektronik");
        this.goods2 = new Goods(2L, "Monitor", new BigDecimal(3000000L), new BigDecimal(4000000L), "Barang Elektronik");

        this.stock1 = new Stock(1L, warehouse, goods1, 5L);
        this.stock1 = new Stock(2L, warehouse, goods2, 10L);

        this.stockResponse1 = new StockResponse(1L, 1L, 1L, 5L);
        this.stockResponse2 = new StockResponse(2L, 1L, 2L, 10L);
    }

    @Test
    void getAllStocks() {

        List<StockResponse> data = new ArrayList<>();
        data.add(stockResponse1);
        data.add(stockResponse2);

        String expectedMessage = "Success";

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(data)
                .build();

        when(stockService.getAllStock()).thenReturn(expectedResponse);
        ResponseEntity<BaseResponse> actualResponse = stockController.getAllStock();

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void getStockById() {
        String expectedMessage = "Success";
        StockResponse expectedData = stockResponse1;

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(stockService.getStockById(stock1.getId())).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = stockController.getStockById(stock1.getId());

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

}