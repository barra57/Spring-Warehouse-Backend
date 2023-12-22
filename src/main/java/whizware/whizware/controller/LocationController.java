package whizware.whizware.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.location.LocationRequest;
import whizware.whizware.service.LocationService;


@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<BaseResponse> addLoc(@Valid @RequestBody LocationRequest request) {
        return new ResponseEntity<>(locationService.addLocation(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateLoc(@PathVariable("id") Long id, @Valid @RequestBody LocationRequest request) {
        return ResponseEntity.ok(locationService.updateLocation(id, request));
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll() {
        return ResponseEntity.ok(locationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(locationService.getLocById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteLocation(@PathVariable("id") Long id) {
        return ResponseEntity.ok(locationService.deleteLocation(id));
    }
}
