package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.store.StoreRequest;
import whizware.whizware.dto.store.StoreResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Store;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.LocationRepository;
import whizware.whizware.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    private final LocationRepository locationRepository;

    private static final String STORE_NOT_FOUND_MESSEGE = "Store with ID %d not found";
    private static final String LOCATION_NOT_FOUND_MESSEGE = "Location with ID %d not found";

    public BaseResponse addStore(StoreRequest request) {
        Store store = new Store();

        Location location = locationRepository.findById(request.getLocationId()).orElseThrow(() -> new NotFoundException(String.format(LOCATION_NOT_FOUND_MESSEGE, request.getLocationId())));

        store.setName(request.getName());
        store.setLocation(location);
        storeRepository.save(store);

        StoreResponse data = StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .locationId(store.getLocation().getId())
                .build();

        return BaseResponse.builder()
                .message("Success add new store!!")
                .data(data)
                .build();
    }

    public BaseResponse updateStore(Long id, StoreRequest request) {

        Store optStore = storeRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(STORE_NOT_FOUND_MESSEGE, id)));
        Location optLoc = locationRepository.findById(request.getLocationId()).orElseThrow(() -> new NotFoundException(String.format(LOCATION_NOT_FOUND_MESSEGE, request.getLocationId())));

        Store store = new Store();

        store.setId(optStore.getId());
        store.setName(request.getName());
        store.setLocation(optLoc);
        storeRepository.save(store);

        StoreResponse data = StoreResponse.builder()
                .id(id)
                .name(store.getName())
                .locationId(store.getLocation().getId())
                .build();

        return BaseResponse.builder()
                .message("Success update data!")
                .data(data)
                .build();
    }

    public BaseResponse getAll() {

        List<Store> stores = storeRepository.findAll();

        List<StoreResponse> data = new ArrayList<>();

        for (Store store : stores) {
            data.add(StoreResponse.builder()
                    .id(store.getId())
                    .name(store.getName())
                    .locationId(store.getLocation().getId())
                    .build());
        }

        if (data.isEmpty()) {
            throw new NoContentException("Store is empty");
        } else {
            return BaseResponse.builder()
                    .message("Success get all store data!")
                    .data(data)
                    .build();
        }
    }

    public BaseResponse getStoreById(Long id) {

        Optional<Store> data = storeRepository.findById(id);

        if (data.isPresent()) {
            return BaseResponse.builder()
                    .message("Success get store with ID " + id)
                    .data(data)
                    .build();
        } else {
            throw new NotFoundException(String.format(STORE_NOT_FOUND_MESSEGE, id));
        }
    }

    public BaseResponse deleteStoreById(Long id) {
        Optional<Store> data = storeRepository.findById(id);

        if (data.isPresent()) {
            storeRepository.deleteById(id);

            return BaseResponse.builder()
                    .message("Success delete store with ID " + id)
                    .data(null)
                    .build();
        } else {
            throw new NotFoundException(String.format(STORE_NOT_FOUND_MESSEGE, id));
        }
    }
}
