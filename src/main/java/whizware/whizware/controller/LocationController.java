package whizware.whizware.controller;

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
    public ResponseEntity<BaseResponse> addLoc(@RequestBody LocationRequest request) {
        BaseResponse response = locationService.addLocation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateLoc(@PathVariable("id") Long id, @RequestBody LocationRequest request) {
        BaseResponse response = locationService.updateLocation(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll() {
        BaseResponse response = locationService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getById(@PathVariable("id") Long id){
        BaseResponse response = locationService.getLocById(id);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteLocation(@PathVariable("id") Long id) {
        BaseResponse response = locationService.deleteLocation(id);
        return ResponseEntity.ok(response);
    }
}
