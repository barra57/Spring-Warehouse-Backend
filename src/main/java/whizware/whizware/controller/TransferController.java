package whizware.whizware.controller;

import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<BaseResponse> getAll() {
        return ResponseEntity.ok(transferService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getTransferById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(transferService.getTransferById(id));
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addTransfer(@Valid @RequestBody RequestTransfer request) {
        return new ResponseEntity<>(transferService.addTransfer(request), HttpStatus.CREATED);
    }

}
