package whizware.whizware.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.stock.StockRequest;
import whizware.whizware.service.StockService;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public ResponseEntity<BaseResponse> getAllStock() {
        return ResponseEntity.ok(stockService.getAllStock());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getStockById(@PathVariable Long id) {
        BaseResponse response = stockService.getStockById(id);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

//    @PostMapping
//    public ResponseEntity<BaseResponse> saveStock(@RequestBody StockRequest stockRequest) {
//        BaseResponse response = stockService.saveStock(stockRequest);
//        if (response.getData() == null) {
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<BaseResponse> save(@PathVariable Long id, @RequestBody StockRequest stockRequest) {
//        BaseResponse response = stockService.updateStock(id, stockRequest);
//        if (response.getData() == null) {
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseResponse> save(@PathVariable Long id) {
//        BaseResponse response = stockService.deleteStockById(id);
//        if (response.getData() == null) {
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(response);
//    }

}
