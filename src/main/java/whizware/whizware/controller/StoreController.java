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
        return new ResponseEntity<>(storeService.addStore(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAllStore() {
        return new ResponseEntity<>(storeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getStoreById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteStoreById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(storeService.deleteStoreById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateStore(@PathVariable("id") Long id, @RequestBody StoreRequest request) {
        return ResponseEntity.ok(storeService.updateStore(id, request));
    }

}
