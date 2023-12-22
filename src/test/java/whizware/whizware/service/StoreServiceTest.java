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
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.LocationRepository;
import whizware.whizware.repository.StoreRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StoreServiceTest {
    @InjectMocks
    StoreService storeService;

    @Mock
    StoreRepository storeRepository;

    @Mock
    LocationRepository locationRepository;

    private Location location;
    private Long id;
    private Store store;
    private Store store2;
    private StoreRequest storeRequest;
    private StoreResponse storeResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.location = new Location();
        location.setId(23L);
        location.setName("Jakarta");

        this.location = new Location();
        location.setId(29L);
        location.setName("Jakarta Timur");

        this.store = new Store();
        store.setId(45L);
        store.setName("Asyraf Store");
        store.setLocation(location);

        this.store2 = new Store();
        store2.setId(85L);
        store2.setName("Ajeng Store");
        store2.setLocation(location);

        this.storeRequest = new StoreRequest(23L, "Jakarta");
        this.storeResponse = new StoreResponse(store.getId(), location.getId(), location.getName());

        this.id = store.getId();
    }

    @Test
    void addStoreWithValidIdLocation() {
        BaseResponse response = new BaseResponse();
        response.setMessage("Success add new store!!");
        response.setData(storeResponse);

        when(locationRepository.findById(any())).thenReturn(Optional.of(location));
        when(storeRepository.save(any())).thenReturn(store);

        BaseResponse actualResponse = storeService.addStore(storeRequest);

        assertNotNull(actualResponse);
        assertEquals(response.getMessage(), actualResponse.getMessage());
    }

    @Test
    void addStoreWith_InValidIdLocation() {
        when(locationRepository.findById(store.getId())).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> storeService.addStore(storeRequest));
        assertEquals(exception.getMessage(), String.format("Location with ID %d not found", 23));
    }

    @Test
    void updateStore() {
        StoreRequest request = new StoreRequest(store.getId(), "Jakarta Update");
        StoreResponse response = new StoreResponse(store.getId(), location.getId(), request.getName());
        when(storeRepository.findById(store.getId())).thenReturn(Optional.ofNullable(store));
        when(locationRepository.findById(any())).thenReturn(Optional.ofNullable(location));

        BaseResponse updateResponse = storeService.updateStore(store.getId(), request);
        assertNotNull(updateResponse);
        assertEquals("Success update data!", updateResponse.getMessage());
        assertEquals(response, updateResponse.getData());

    }

    @Test
    void updateStore_InvalidStoreID() {
        StoreRequest request = new StoreRequest(store.getId(), "Jakarta Update");
        when(storeRepository.findById(store.getId())).thenReturn(Optional.empty());
        when(locationRepository.findById(any())).thenReturn(Optional.ofNullable(location));

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> storeService.updateStore(id, request));
        assertEquals(String.format("Store with ID %d not found", store.getId()), notFoundException.getMessage());
    }

    @Test
    void updateStore_InvalidLocationID() {
        StoreRequest request = new StoreRequest(store.getId(), "Jakarta Update");
        when(storeRepository.findById(store.getId())).thenReturn(Optional.ofNullable(store));
        when(locationRepository.findById(any())).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> storeService.updateStore(id, request));
        assertEquals(String.format("Location with ID %d not found", store.getId()), notFoundException.getMessage());
    }

    @Test
    void getAllStoreTest() {
        List<Store> expectedData = Arrays.asList(store, store2);
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
    void getAllStoreTestEmpty() {
        List<Store> expectedData = new ArrayList<>();
        when(storeRepository.findAll()).thenReturn(expectedData);
        Assertions.assertThrows(NoContentException.class, () -> storeService.getAll());
    }

    @Test
    void getStoreByIdTest() {
        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success get store with ID " + store.getId())
                .data(storeResponse)
                .build();
        when(storeRepository.findById(store.getId())).thenReturn(Optional.ofNullable(store));

        BaseResponse actualResponse = storeService.getStoreById(store.getId());

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    }

    @Test
    void getStoreWithInvalidIdTest() {
        when(storeRepository.findById(store.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> storeService.getStoreById(id));
    }

    @Test
    void deleteStoreByIdTest() {
        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success delete store with ID " + store.getId())
                .data(null)
                .build();
        when(storeRepository.findById(store.getId())).thenReturn(Optional.ofNullable(store));
        BaseResponse actualResponse = storeService.deleteStoreById(store.getId());

        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    }

    @Test
    void deleteStoreWithInvalidIdTest() {
        Long id = store.getId();
        when(storeRepository.findById(store.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> storeService.deleteStoreById(id));
    }
}