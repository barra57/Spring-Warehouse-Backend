package whizware.whizware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.goods.RequestGoods;
import whizware.whizware.dto.goods.ResponseGoods;
import whizware.whizware.services.GoodsService;

import java.util.List;

@RequestMapping
@RestController("/whizware")
public class GoodsController {

    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping("/goods")
    public ResponseEntity<BaseResponse> getAllGods(@RequestBody RequestGoods requestGoods) {
        BaseResponse responseGoods = goodsService.getAllGoods(requestGoods);
        return ResponseEntity.ok(responseGoods);
    }

    @PostMapping("/goods")
    public ResponseEntity<BaseResponse> saveGoods(@RequestBody RequestGoods requestGoods) {
        BaseResponse baseResponse = goodsService.saveGoods(requestGoods);
        return ResponseEntity.ok(baseResponse);
    }

    @PutMapping("/goods/{id}")
    public ResponseEntity<BaseResponse> updateGoods(@PathVariable("id") Long id, @RequestBody RequestGoods requestGoods) {
        BaseResponse baseResponse = goodsService.updateGoods(id, requestGoods);
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/goods/{id}")
    public ResponseEntity<BaseResponse> deleteGoods(@PathVariable("id") Long id) {
        BaseResponse responseGoods = goodsService.deleteIdGoods(id);
        return ResponseEntity.ok(responseGoods);
    }

    @GetMapping("/goods/{id}")
    public ResponseEntity<BaseResponse> getGoodsById(@PathVariable("id") Long id, @RequestBody RequestGoods requestGoods) {
        BaseResponse baseResponse = goodsService.getGoodsById(id, requestGoods);
        return ResponseEntity.ok(baseResponse);
    }
}
