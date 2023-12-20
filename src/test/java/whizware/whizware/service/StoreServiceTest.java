package whizware.whizware.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.store.StoreRequest;
import whizware.whizware.dto.store.StoreResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Store;
import whizware.whizware.repository.LocationRepository;
import whizware.whizware.repository.StoreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StoreServiceTest {
    @InjectMocks
    StoreService storeService;

    @Mock
    StoreRepository storeRepository;

    @Mock
    LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addStoreTest() {
        Long id = 1L;

        StoreRequest request = new StoreRequest( 1L, "Maju");

        Location mockLoc = new Location();
        mockLoc.setId(request.getLoc_id());
        mockLoc.setName("Jakarta");

        Store mockData = new Store();
        mockData.setId(id);
        mockData.setLocation(mockLoc);
        mockData.setName(request.getName());

        StoreResponse expectedData = StoreResponse.builder()
                .id(id)
                .name(mockData.getName())
                .loc_id(mockData.getId())
                .build();

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success add new store!!")
                .data(expectedData)
                .build();

        when(locationRepository.findById(request.getLoc_id())).thenReturn(Optional.of(mockLoc));
        when(storeRepository.save(any())).thenReturn(mockData);

        BaseResponse actualResponse = storeService.addStore(request);
        StoreResponse actualData = (StoreResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        //Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getLoc_id(), actualData.getLoc_id());
        Assertions.assertEquals(expectedData.getName(), actualData.getName());

    }

    @Test
    void updateStoreTest() {
        Long id = 1L;
        StoreRequest request = new StoreRequest(1L, "Maju");

        Location mockLoc = new Location();
        mockLoc.setId(request.getLoc_id());
        mockLoc.setName("Bekasi");

        Store mockData = new Store();
        mockData.setId(id);
        mockData.setLocation(mockLoc);
        mockData.setName("Mundur");

        Store updateData = new Store();
        updateData.setId(id);
        updateData.setLocation(mockLoc);
        updateData.setName(request.getName());

        StoreResponse expectedData = StoreResponse.builder()
                .id(updateData.getId())
                .loc_id(updateData.getLocation().getId())
                .name(updateData.getName())
                .build();

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success update data!")
                .data(expectedData)
                .build();

        when(locationRepository.findById(request.getLoc_id())).thenReturn(Optional.of(mockLoc));
        when(storeRepository.findById(id)).thenReturn(Optional.of(mockData));
        when(storeRepository.save(any())).thenReturn(updateData);

        BaseResponse actualResponse = storeService.updateStore(id, request);
        StoreResponse actualData = (StoreResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getLoc_id(), actualData.getLoc_id());
        Assertions.assertEquals(expectedData.getName(), actualData.getName());
    }

    @Test
    void getAllStoreTest() {
        Location loc = new Location();
        loc.setId(1L);

        Store store1 = new Store();
        store1.setId(1L);
        store1.setLocation(loc);
        store1.setName("Berkah");

        Store store2 = new Store();
        store2.setId(2L);
        store2.setLocation(loc);
        store2.setName("Maju Jaya");

        List<Store> expectedData = Arrays.asList(store1, store2);

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success get all store data!")
                .data(expectedData)
                .build();
        when(storeRepository.findAll()).thenReturn(expectedData);

        BaseResponse actualResponse = storeService.getAll();
        List<StoreResponse> actualData = (List<StoreResponse>) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.size(), actualData.size());
    }

    @Test
    void getStoreByIDTest() {
        Long id = 1L;

        Location mockLoc = new Location();
        mockLoc.setId(1L);
        mockLoc.setName("Semarang");

        Store mockData = new Store();
        mockData.setId(id);
        mockData.setLocation(mockLoc);
        mockData.setName("Maju Berkah");

        StoreResponse expectedData = StoreResponse.builder()
                .id(id)
                .name(mockData.getName())
                .loc_id(mockData.getLocation().getId())
                .build();

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success get store with ID " + id)
                .data(expectedData)
                .build();
        when(storeRepository.findById(id)).thenReturn(Optional.of(mockData));

        BaseResponse actualResponse = storeService.getStoreById(id);
        StoreResponse actualData = (StoreResponse) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getLoc_id(), actualData.getLoc_id());
        Assertions.assertEquals(expectedData.getName(), actualData.getName());
    }

    @Test
    void deleteStoreTest() {
        Long id = 1L;

        Location mockLoc = new Location();
        mockLoc.setId(1L);
        mockLoc.setName("Jakarta");

        Store mockData = new Store();
        mockData.setId(id);
        mockData.setName("Barokah");
        mockData.setLocation(mockLoc);

        Store expectedData = new Store();
        expectedData.setId(mockData.getId());
        expectedData.setName(mockData.getName());
        expectedData.setLocation(mockData.getLocation());

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success delete data with ID " + id)
                .data(expectedData)
                .build();

        when(storeRepository.findById(id)).thenReturn(Optional.of(mockData));

        BaseResponse actualResponse = storeService.deleteStoreById(id);
        Store actualData = (Store) actualResponse.getData();

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getName(), actualData.getName());
        Assertions.assertEquals(expectedData.getLocation().getId(), actualData.getLocation().getId());
    }

}
