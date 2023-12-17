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

        BaseResponse response = deliveryService.getAllDeliveryByWarehouseId(warehouseId);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<BaseResponse> getDeliveryById(@PathVariable Long id) {
        BaseResponse response = deliveryService.getDeliveryById(id);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> saveDelivery(@Valid @RequestBody DeliveryRequest deliveryRequest) {
        BaseResponse response = deliveryService.saveDelivery(deliveryRequest);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
