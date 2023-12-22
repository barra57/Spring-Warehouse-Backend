package whizware.whizware.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.goods.GoodsRequest;
import whizware.whizware.dto.goods.GoodsResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.service.GoodsService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class GoodsControllerTest {
    @InjectMocks
    private GoodsController goodsController;

    @Mock
    private GoodsService goodsService;

    private Goods goods1;
    private Goods goods2;

    private GoodsResponse goodsResponse1;
    private GoodsResponse goodsResponse2;

    private GoodsRequest goodsRequest;

    @BeforeEach
    void setUp(){
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
    void getAllGoods(){
        List<GoodsResponse> list = new ArrayList<>();
        list.add(goodsResponse1);
        list.add(goodsResponse2);
        BaseResponse response = new BaseResponse();
        response.setMessage("Success");
        response.setData(list);

        when(goodsService.getAllGoods()).thenReturn(response);
        ResponseEntity<BaseResponse> actual = goodsController.getAllGods();

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(response, actual.getBody());
    }

    @Test
    void saveGood(){
        BaseResponse response = new BaseResponse();
        response.setMessage("Success");
        response.setData(goods1);

        when(goodsService.saveGoods(goodsRequest)).thenReturn(response);
        ResponseEntity<BaseResponse> actual = goodsController.saveGoods(goodsRequest);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(response, actual.getBody());
    }

    @Test
    void updateGoods(){
        BaseResponse response = new BaseResponse();
        response.setMessage("Success");
        response.setData(goods1);

        when(goodsService.updateGoods(goods2.getId(), goodsRequest)).thenReturn(response);
        ResponseEntity<BaseResponse> actual = goodsController.updateGoods(goods2.getId(), goodsRequest);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(response, actual.getBody());
    }

    @Test
    void deleteGoods(){
        BaseResponse response = new BaseResponse();
        response.setMessage("Success");
        response.setData(goods1);

        when(goodsService.deleteIdGoods(goods2.getId())).thenReturn(response);
        ResponseEntity<BaseResponse> actual = goodsController.deleteGoods(goods2.getId());

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(response, actual.getBody());
    }

    @Test
    void getGoodsById(){
        BaseResponse response = new BaseResponse();
        response.setMessage("Success");
        response.setData(goods1);

        when(goodsService.getGoodsById(goods2.getId())).thenReturn(response);
        ResponseEntity<BaseResponse> actual = goodsController.getGoodsById(goods2.getId());

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(response, actual.getBody());
    }

}
