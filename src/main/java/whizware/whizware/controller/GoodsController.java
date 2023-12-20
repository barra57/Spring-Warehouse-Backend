package whizware.whizware.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.ok(goodsService.getAllGoods());
    }

    @PostMapping
    public ResponseEntity<BaseResponse> saveGoods(@Valid @RequestBody GoodsRequest goodsRequest) {
        return new ResponseEntity<>(goodsService.saveGoods(goodsRequest), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateGoods(@PathVariable("id") Long id, @Valid @RequestBody GoodsRequest goodsRequest) {
        return ResponseEntity.ok(goodsService.updateGoods(id, goodsRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteGoods(@PathVariable("id") Long id) {
        return ResponseEntity.ok(goodsService.deleteIdGoods(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getGoodsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(goodsService.getGoodsById(id));
    }
}
