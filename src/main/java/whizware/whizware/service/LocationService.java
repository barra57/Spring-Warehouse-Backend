package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.location.LocationRequest;
import whizware.whizware.dto.location.LocationResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.LocationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    private static final String LOCATION_NOT_FOUND_MESSEGE = "Location with ID %d not found";

    public BaseResponse getAll() {

        List<Location> data = locationRepository.findAll();
        if (data.isEmpty())
            throw new NoContentException("Location is empty");

        return BaseResponse.builder()
                .message("Success get all data")
                .data(data)
                .build();
    }

    public BaseResponse getLocById(Long id) {
        Location data = locationRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(LOCATION_NOT_FOUND_MESSEGE, id)));

        return BaseResponse.builder()
                .message("Success get location with ID " + id + "!")
                .data(data)
                .build();

    }

    public BaseResponse addLocation(LocationRequest request) {
        Location location = new Location();
        location.setName(request.getName());
        locationRepository.save(location);

        LocationResponse data = LocationResponse.builder()
                .id(location.getId())
                .name(location.getName())
                .build();

        return BaseResponse.builder()
                .message("Location successfully added")
                .data(data)
                .build();
    }

    public BaseResponse updateLocation(Long id, LocationRequest request) {

        Location location = locationRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(LOCATION_NOT_FOUND_MESSEGE, id)));

        location.setId(location.getId());
        location.setName(request.getName());
        locationRepository.save(location);

        LocationResponse data = LocationResponse.builder()
                .id(location.getId())
                .name(location.getName())
                .build();

        return BaseResponse.builder()
                .message("Success update data!")
                .data(data)
                .build();

    }

    public BaseResponse deleteLocation(Long id) {

        Location data = locationRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(LOCATION_NOT_FOUND_MESSEGE, id)));

        locationRepository.delete(data);

        return BaseResponse.builder()
                .message("Success delete data with ID " + id)
                .data(null)
                .build();
    }


}
