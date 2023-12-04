package whizware.whizware.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.receipt.ReceiptRequest;
import whizware.whizware.service.ReceiptService;

@RestController
@RequestMapping("/receipt")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(@RequestParam(value = "warehouse_id", required = false) Long warehouseId) {
        if (warehouseId == null) {
            return ResponseEntity.ok(receiptService.getAllReceipt());
        }

        BaseResponse response = receiptService.getAllReceiptByWarehouseId(warehouseId);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
        BaseResponse response = receiptService.getReceiptById(id);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

//    @GetMapping
//    public ResponseEntity<BaseResponse> getByWarehouseId(@RequestParam("warehouse_id") Long warehouseId) {
//        BaseResponse response = receiptService.getAllReceiptByWarehouseId(warehouseId);
//        if (response.getData() == null) {
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(response);
//    }

    @PostMapping
    public ResponseEntity<BaseResponse> save(@RequestBody ReceiptRequest request) {
        BaseResponse response = receiptService.saveReceipt(request);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable Long id, @RequestBody ReceiptRequest request) {
        BaseResponse response = receiptService.updateReceipt(id, request);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable Long id) {
        BaseResponse response = receiptService.deleteReceipt(id);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

}
