package whizware.whizware.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.transfer.RequestTransfer;
import whizware.whizware.service.TransferService;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<BaseResponse> addTransfer(@RequestBody RequestTransfer request) {
        BaseResponse response = transferService.addTransfer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll() {
        BaseResponse response = transferService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getTransferById(@PathVariable("id") Long id) {
        BaseResponse response = transferService.getTransferById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateTransfer(@PathVariable("id") Long id, @RequestBody RequestTransfer request) {
        BaseResponse response = transferService.updateTransfer(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteTransfer(@PathVariable("id") Long id) {
        BaseResponse response = transferService.deleteTransfer(id);
        return ResponseEntity.ok(response);
    }
}
