package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.location.LocationRequest;
import whizware.whizware.dto.location.LocationResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.repository.LocationRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public BaseResponse addLocation(LocationRequest request){
        Location location = new Location();
        location.setName(request.getName());
        locationRepository.save(location);

        LocationResponse data = LocationResponse.builder()
                .id(location.getId())
                .name(location.getName())
                .build();

        return BaseResponse.builder()
                .message("Successfully added data!")
                .data(data)
                .build();
    }

    public BaseResponse updateLocation(Long id, LocationRequest request){

        Optional<Location> optLocation = locationRepository.findById(id);

        Location location = new Location();

        if (optLocation.isPresent()) {
            location.setId(optLocation.get().getId());
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
        } else {
            return BaseResponse.builder()
                    .message("Fail update data with ID " + id)
                    .data(null)
                    .build();
        }

    }
    
    public BaseResponse getAll() {

        List<Location> data = null;

        data = locationRepository.findAll();

        return BaseResponse.builder()
                .message("Success get all data")
                .data(data)
                .build();
    }

    public BaseResponse getLocById(Long id) {

        Optional<Location> data = locationRepository.findById(id);

        if (data.isPresent()) {
            return BaseResponse.builder()
                    .message("Success get location with ID " + id + "!")
                    .data(data)
                    .build();
        } else {
            return BaseResponse.builder()
                    .message("Cannot find data with ID " + id)
                    .data(null)
                    .build();
        }

    }

    public BaseResponse deleteLocation(Long id) {

        Optional<Location> data = locationRepository.findById(id);

        if (data.isPresent()){
            locationRepository.deleteById(id);

            return BaseResponse.builder()
                    .message("Success delete data with ID " + id)
                    .data(null)
                    .build();
        } else {
            return BaseResponse.builder()
                    .message("Cannot find data with ID " + id)
                    .data(null)
                    .build();
        }
    }


}
