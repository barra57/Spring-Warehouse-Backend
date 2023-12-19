package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.store.StoreRequest;
import whizware.whizware.dto.store.StoreResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Store;
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

    public BaseResponse addStore(StoreRequest request) {
        Store store = new Store();

        Optional<Location> optLoc = locationRepository.findById(request.getLoc_id());

        if (optLoc.isPresent()) {
            store.setName(request.getName());
            store.setLocation(optLoc.get());
            storeRepository.save(store);

            StoreResponse data = StoreResponse.builder()
                    .id(store.getId())
                    .name(store.getName())
                    .loc_id(store.getLocation().getId())
                    .build();

            return BaseResponse.builder()
                    .message("Success add new store!!")
                    .data(data)
                    .build();
        } else {
            return BaseResponse.builder()
                    .message("Failed added store data!!")
                    .data(null)
                    .build();
        }
    }

    public BaseResponse updateStore(Long id, StoreRequest request) {

        Optional<Store> optStore = storeRepository.findById(id);
        Optional<Location> optLoc = locationRepository.findById(request.getLoc_id());

        Store store = new Store();

        if (optStore.isPresent() && optLoc.isPresent()) {
            store.setId(optStore.get().getId());
            store.setName(request.getName());
            store.setLocation(optLoc.get());
            storeRepository.save(store);

            StoreResponse data = StoreResponse.builder()
                    .id(id)
                    .name(store.getName())
                    .loc_id(store.getLocation().getId())
                    .build();

            return BaseResponse.builder()
                    .message("Success update data!")
                    .data(data)
                    .build();
        } else {
            return BaseResponse.builder()
                    .message("Cannot update data!")
                    .data(null)
                    .build();
        }
    }

    public BaseResponse getAll() {

        List<Store> stores = storeRepository.findAll();

        List<StoreResponse> data = new ArrayList<>();

        for (Store store: stores) {
            data.add(StoreResponse.builder()
                    .id(store.getId())
                    .name(store.getName())
                    .loc_id(store.getLocation().getId())
                    .build());
        }

        if (data.isEmpty()){
            return BaseResponse.builder()
                    .message("Failed get all store data!!")
                    .data(null)
                    .build();
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
            return BaseResponse.builder()
                    .message("Failed get store with ID " + id)
                    .data(null)
                    .build();
        }
    }

    public BaseResponse deleteStoreById(Long id) {
        Optional<Store> data = storeRepository.findById(id);

        if (data.isPresent()) {
            storeRepository.deleteById(id);

            return BaseResponse.builder()
                    .message("Success delete data with ID " + id)
                    .data(null)
                    .build();
        } else {
            return BaseResponse.builder()
                    .message("Failed delete store data!")
                    .data(null)
                    .build();
        }
    }
}
