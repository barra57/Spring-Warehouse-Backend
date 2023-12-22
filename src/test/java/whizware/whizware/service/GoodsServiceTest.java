package whizware.whizware.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.goods.GoodsRequest;
import whizware.whizware.dto.goods.GoodsResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.GoodsRepository;
import whizware.whizware.repository.LocationRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class GoodsServiceTest {
    @InjectMocks
    GoodsService goodsService;

    @Mock
    GoodsRepository goodsRepository;

    private Goods goods1;
    private Goods goods2;

    private GoodsResponse goodsResponse1;
    private GoodsResponse goodsResponse2;

    private GoodsRequest goodsRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.goods1 = new Goods();
        goods1.setId(12L);
        goods1.setName("Piring");
        goods1.setSellingPrice(BigDecimal.valueOf(30000));
        goods1.setPurchasePrice(BigDecimal.valueOf(25000));
        goods1.setDescription("Description piring");
        this.goods2 = new Goods();
        goods2.setId(13L);
        goods2.setName("Gelas");
        goods2.setSellingPrice(BigDecimal.valueOf(26000));
        goods2.setPurchasePrice(BigDecimal.valueOf(23000));
        goods2.setDescription("Description gelas");

        this.goodsResponse1 = new GoodsResponse(goods1.getId(), goods1.getName(), goods1.getSellingPrice(), goods1.getPurchasePrice(), goods1.getDescription());
        this.goodsResponse2 = new GoodsResponse(goods2.getId(), goods2.getName(), goods2.getSellingPrice(), goods2.getPurchasePrice(), goods2.getDescription());

        this.goodsRequest = new GoodsRequest();
        goodsRequest.setName(goods1.getName());
        goodsRequest.setSellingPrice(goods1.getSellingPrice());
        goodsRequest.setPurchasePrice(goods1.getPurchasePrice());
        goodsRequest.setDescription(goods1.getDescription());
    }

    @Test
    void getAllGoods() {
        List<Goods> listGoods = new ArrayList<>();
        listGoods.add(goods1);
        listGoods.add(goods2);

        GoodsResponse goodsResponse1 = new GoodsResponse(goods1.getId(), goods1.getName(), goods1.getSellingPrice(), goods1.getPurchasePrice(), goods1.getDescription());
        GoodsResponse goodsResponse2 = new GoodsResponse(goods2.getId(), goods2.getName(), goods2.getSellingPrice(), goods2.getPurchasePrice(), goods2.getDescription());

        List<GoodsResponse> goodsList = new ArrayList<>();
        goodsList.add(goodsResponse1);
        goodsList.add(goodsResponse2);
        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success")
                .data(goodsList)
                .build();

        when(goodsRepository.findAll()).thenReturn(listGoods);

        BaseResponse actualResponse = goodsService.getAllGoods();
        assertNotNull(actualResponse.getData());
        assertEquals(actualResponse.getMessage(), expectedResponse.getMessage());
        assertEquals(actualResponse.getData(), expectedResponse.getData());
    }

    @Test
    void getAllGoodsInvalidCase() {
        List<Goods> goodsList = new ArrayList<>();
        when(goodsRepository.findAll()).thenReturn(goodsList);
        NoContentException noContentException = assertThrows(NoContentException.class, () -> goodsService.getAllGoods());

        assertEquals("Goods is empty", noContentException.getMessage());
    }

    @Test
    void getGoodsByIdTrueCase() {
        when(goodsRepository.findById(goods1.getId())).thenReturn(Optional.ofNullable(goods1));
        BaseResponse actualResponse = goodsService.getGoodsById(goods1.getId());

        assertNotNull(actualResponse);
        assertEquals("Success", actualResponse.getMessage());
        assertEquals(goodsResponse1, actualResponse.getData());
    }

    @Test
    void getGoodsByIdFalseCase() {
        Long id = goods1.getId();

        String GOODS_NOT_FOUND_MESSAGE = "Goods with ID %d not found";
        when(goodsRepository.findById(goods1.getId())).thenReturn(Optional.empty());
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> goodsService.getGoodsById(id));
        assertEquals(String.format(GOODS_NOT_FOUND_MESSAGE, goods1.getId()), notFoundException.getMessage());
    }

    @Test
    void saveGoods() {
        GoodsResponse expected = new GoodsResponse(goods1.getId(), goods1.getName(), goods1.getSellingPrice(), goods1.getPurchasePrice(), goods1.getDescription());
        when(goodsRepository.save(any())).thenReturn(goods1);
        BaseResponse response = goodsService.saveGoods(goodsRequest);
        assertNotNull(response);
        assertEquals("Goods successfully added", response.getMessage());
    }

    @Test
    void updateGoodsByIdTrueCase() {
        GoodsRequest request = new GoodsRequest();
        request.setName("Piring Update");
        request.setSellingPrice(goods1.getSellingPrice().add(BigDecimal.valueOf(2000)));
        request.setPurchasePrice(goods1.getPurchasePrice().add(BigDecimal.valueOf(2000)));
        request.setDescription("update");

        GoodsResponse expected = new GoodsResponse(goods2.getId(), request.getName(), request.getSellingPrice(), request.getPurchasePrice(), request.getDescription());

        when(goodsRepository.findById(goods2.getId())).thenReturn(Optional.ofNullable(goods2));
        BaseResponse response = goodsService.updateGoods(goods2.getId(), request);

        assertNotNull(response);
        assertEquals("Goods successfully updated", response.getMessage());
        assertEquals(expected, response.getData());
    }

    @Test
    void updateGoodsByIdNegativeCase() {
        Long id = goods1.getId();
        GoodsRequest request = new GoodsRequest();
        String message = String.format("Goods with ID %d not found", goods1.getId());
        when(goodsRepository.findById(goods1.getId())).thenReturn(Optional.empty());
        NotFoundException response = assertThrows(NotFoundException.class, () -> goodsService.updateGoods(id, request));
        assertEquals(message, response.getMessage());
    }

    @Test
    void deleteGoodsByIdPositiveCase() {
        String message = "Goods successfully deleted";
        when(goodsRepository.findById(goods1.getId())).thenReturn(Optional.ofNullable(goods1));
        BaseResponse response = goodsService.deleteIdGoods(goods1.getId());
        assertEquals(message, response.getMessage());
    }

    @Test
    void deleteGoodsByIdNegativeCase() {
        Long id = goods1.getId();
        GoodsRequest request = new GoodsRequest();
        String message = String.format("Goods with ID %d not found", goods1.getId());
        when(goodsRepository.findById(goods1.getId())).thenReturn(Optional.empty());
        NotFoundException response = assertThrows(NotFoundException.class, () -> goodsService.deleteIdGoods(id));
        assertEquals(message, response.getMessage());
    }
}
