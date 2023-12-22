package whizware.whizware.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.store.StoreRequest;
import whizware.whizware.dto.store.StoreResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Store;
import whizware.whizware.service.StoreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreControllerTests {

    @InjectMocks
    StoreController storeController;

    @Mock
    StoreService storeService;

    private Store store1;
    private Store store2;

    private StoreResponse storeResponse1;
    private StoreResponse storeResponse2;

    private StoreRequest storeRequest;

    @BeforeEach
    void setUp() {
        this.store1 = new Store(1L, "A", new Location(1L, "Jakarta"));
        this.store2 = new Store(2L, "B", new Location(2L, "Bekasi"));

        this.storeRequest = new StoreRequest(1l, "A");

        this.storeResponse1 = new StoreResponse(1L, 1L, "A");
        this.storeResponse2 = new StoreResponse(2L, 2L, "B");
    }

    @Test
    void getAllStores() {

        List<StoreResponse> data = new ArrayList<>();
        data.add(storeResponse1);
        data.add(storeResponse2);

        String expectedMessage = "Success";

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(data)
                .build();

        when(storeService.getAll()).thenReturn(expectedResponse);
        ResponseEntity<BaseResponse> actualResponse = storeController.getAllStore();

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void getStoreById() {
        String expectedMessage = "Success";
        StoreResponse expectedData = storeResponse1;

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(storeService.getStoreById(store1.getId())).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = storeController.getStoreById(store1.getId());

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void saveStore() {
        StoreRequest request = storeRequest;

        String expectedMessage = "Store succesfully added";
        StoreResponse expectedData = storeResponse1;

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(storeService.addStore(request)).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = storeController.addStore(request);

        Assertions.assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void updateStore() {
        StoreRequest request = storeRequest;

        String expectedMessage = "Store succesfully updated!";
        StoreResponse expectedData = storeResponse2;

        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(storeService.updateStore(store2.getId(), request)).thenReturn(expectedResponse);

        ResponseEntity<BaseResponse> actualResponse = storeController.updateStore(store2.getId(), request);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

    @Test
    void deleteStore() {
        String expectedMessage = "Store successfully deleted!";
        Store expectedData = store1;
        BaseResponse expectedResponse = BaseResponse.builder()
                .message(expectedMessage)
                .data(expectedData)
                .build();

        when(storeService.deleteStoreById(store1.getId())).thenReturn(expectedResponse);
        ResponseEntity<BaseResponse> actualResponse = storeController.deleteStoreById(store1.getId());

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedMessage, actualResponse.getBody().getMessage());
        Assertions.assertNotNull(actualResponse.getBody().getData());
    }

}