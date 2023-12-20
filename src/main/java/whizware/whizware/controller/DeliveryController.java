package whizware.whizware.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.delivery.DeliveryRequest;
import whizware.whizware.service.DeliveryService;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(@RequestParam(value = "warehouse_id", required = false) Long warehouseId) {
        if (warehouseId == null) {
            return ResponseEntity.ok(deliveryService.getAllDelivery());
        }
        return ResponseEntity.ok(deliveryService.getAllDeliveryByWarehouseId(warehouseId));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<BaseResponse> getDeliveryById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }

    @PostMapping
    public ResponseEntity<BaseResponse> saveDelivery(@Valid @RequestBody DeliveryRequest deliveryRequest) {
        return new ResponseEntity<>(deliveryService.saveDelivery(deliveryRequest), HttpStatus.CREATED);
    }

}
