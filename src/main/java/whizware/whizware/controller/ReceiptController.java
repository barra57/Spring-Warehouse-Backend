package whizware.whizware.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        return ResponseEntity.ok(receiptService.getAllReceiptByWarehouseId(warehouseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(receiptService.getReceiptById(id));
    }

    @PostMapping
    public ResponseEntity<BaseResponse> save(@Valid @RequestBody ReceiptRequest request) {
        return new ResponseEntity<>(receiptService.saveReceipt(request), HttpStatus.CREATED);
    }

}
