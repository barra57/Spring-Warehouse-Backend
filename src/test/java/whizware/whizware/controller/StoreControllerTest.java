package whizware.whizware.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.store.StoreRequest;
import whizware.whizware.dto.store.StoreResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Store;
import whizware.whizware.service.StoreService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class StoreControllerTest {
    @InjectMocks
    StoreController storeController;

    @Mock
    StoreService storeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);}

    @Test
    void addStoreTest() {
        StoreRequest request = new StoreRequest(1L,   "Toko Berkas");

        StoreResponse data = new StoreResponse(1L, request.getName(), request.getLoc_id());

        BaseResponse response = BaseResponse.builder()
                .message("Successfully added data!")
                .data(data)
                .build();

        when(storeService.addStore(request)).thenReturn(response);

        ResponseEntity<BaseResponse> responseEntity = storeController.addStore(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    void getAllStoreTest() {
        StoreResponse store1 = new StoreResponse(1L, "Toko Berkah", 1L);
        StoreResponse store2 = new StoreResponse(2L, "Toko Maju", 1L);
        StoreResponse store3 = new StoreResponse(3L, "Toko Banteng", 2L);

        List<StoreResponse> data = Arrays.asList(store1, store2, store3);

        BaseResponse response = BaseResponse.builder()
                .message("Success get all data")
                .data(data)
                .build();

        when(storeService.getAll()).thenReturn(response);

        ResponseEntity<BaseResponse> actualResponse = storeController.getAllStore();

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(response, actualResponse.getBody());
    }

    @Test
    void getStoreByIdTest() {
        StoreResponse data = StoreResponse.builder()
                .id(1L)
                .name("Jakarta Barat")
                .loc_id(1L)
                .build();

        BaseResponse response = BaseResponse.builder()
                .message("Succes get data with ID")
                .data(data)
                .build();

        when(storeService.getStoreById(data.getId())).thenReturn(response);

        ResponseEntity<BaseResponse> actualResponse = storeController.getStoreById(data.getId());

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(response, actualResponse.getBody());
    }

    @Test
    void updateStoreTest() {
        Long id = 2L;

        StoreRequest request = new StoreRequest(1L, "Harumi");

        StoreResponse data = StoreResponse.builder()
                .id(id)
                .loc_id(request.getLoc_id())
                .name(request.getName())
                .build();

        BaseResponse response = BaseResponse.builder()
                .message("Success update data!")
                .data(data)
                .build();

        when(storeService.updateStore(id, request)).thenReturn(response);

        ResponseEntity<BaseResponse> actualResponse = storeController.updateStore(id, request);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(response, actualResponse.getBody());
    }

    @Test
    void deleteStoreTest(){
        Long id = 1L;
        Long idLoc = 1L;

        Location location = new Location();
        location.setId(idLoc);
        location.setName("Bukit Tinggi");

        Store expectedData = new Store();
        expectedData.setId(id);
        expectedData.setName("Toko Jaya");
        expectedData.setLocation(location);

        BaseResponse expectedResponse = BaseResponse.builder()
                .message("Success delete data")
                .data(expectedData)
                .build();

        when(storeService.deleteStoreById(id)).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = storeController.deleteStoreById(id);
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse.getMessage(), actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

}
