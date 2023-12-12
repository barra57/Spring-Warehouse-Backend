package whizware.whizware.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.store.StoreRequest;
import whizware.whizware.service.StoreService;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<BaseResponse> addStore(@RequestBody StoreRequest request) {
        BaseResponse response = storeService.addStore(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAllStore() {
        BaseResponse response = storeService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getStoreById(@PathVariable("id") Long id) {
        BaseResponse response = storeService.getStoreById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteStoreById(@PathVariable("id") Long id) {
        BaseResponse response = storeService.deleteStoreById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateStore(@PathVariable("id") Long id, @RequestBody StoreRequest request) {
        BaseResponse response = storeService.updateStore(id, request);
        return ResponseEntity.ok(response);
    }

}
