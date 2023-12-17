package whizware.whizware.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.goods.GoodsRequest;
import whizware.whizware.service.GoodsService;

@RequestMapping("/goods")
@RestController
public class GoodsController {

    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAllGods() {
        BaseResponse responseGoods = goodsService.getAllGoods();
        return ResponseEntity.ok(responseGoods);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> saveGoods(@Valid @RequestBody GoodsRequest goodsRequest) {
        BaseResponse baseResponse = goodsService.saveGoods(goodsRequest);
        return ResponseEntity.ok(baseResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateGoods(@PathVariable("id") Long id, @Valid @RequestBody GoodsRequest goodsRequest) {
        BaseResponse baseResponse = goodsService.updateGoods(id, goodsRequest);
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteGoods(@PathVariable("id") Long id) {
        BaseResponse responseGoods = goodsService.deleteIdGoods(id);
        return ResponseEntity.ok(responseGoods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getGoodsById(@PathVariable("id") Long id) {
        BaseResponse baseResponse = goodsService.getGoodsById(id);
        return ResponseEntity.ok(baseResponse);
    }
}
