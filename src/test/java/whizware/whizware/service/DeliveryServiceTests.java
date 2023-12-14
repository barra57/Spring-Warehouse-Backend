package whizware.whizware.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.delivery.DeliveryRequest;
import whizware.whizware.dto.delivery.DeliveryResponse;
import whizware.whizware.entity.*;
import whizware.whizware.repository.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DeliveryServiceTests {
    @InjectMocks
    DeliveryService deliveryService;

    @Mock
    DeliveryRepository deliveryRepository;

    @Mock
    WarehouseRepository warehouseRepository;

    @Mock
    GoodsRepository goodsRepository;

    @Mock
    LocationRepository locationRepository;

    @Mock
    StoreRepository storeRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    Location generateMockLocation() {
        Location location = new Location();
        location.setId(1L);
        location.setName("Jakata");
        return location;
    }

    Warehouse generateMockWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setName("A");
        warehouse.setLocation(generateMockLocation());
        return warehouse;
    }

    Store generateMockStore() {
        Store store = new Store();
        store.setId(1L);
        store.setName("Store 1");
        store.setLocation(generateMockLocation());
        return store;
    }

    Goods generatedMockGoods() {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setName("Coke");
        goods.setDescription("Minuman bersoda");
        goods.setPurchasePrice(100L);
        goods.setSellingPrice(200L);
        return goods;
    }

    @Test
    void testGetAllDelivery() {

        Delivery delivery1 = new Delivery(1L, generateMockWarehouse(), generateMockStore(), generatedMockGoods(), 10L, new BigDecimal(100), "Delivered", new Date(2022, 12, 12));
        Delivery delivery2 = new Delivery(2L, generateMockWarehouse(), generateMockStore(), generatedMockGoods(), 20L, new BigDecimal(200), "Delivered", new Date(2023, 12, 12));

        List<Delivery> deliveryList = Arrays.asList(delivery1, delivery2);

        BaseResponse response = BaseResponse.builder()
                .message("Status")
                .data(deliveryList)
                .build();

        when(deliveryRepository.findAll()).thenReturn(deliveryList);
        BaseResponse actualResponse = deliveryService.getAllDelivery();
        List<Delivery> actualData = (List<Delivery>) actualResponse.getData();

        Assertions.assertEquals(deliveryList.size(), actualData.size());

    }

    @Test
    void testGetDeliveryWithValidId() {
        Long id = 1L;
        Delivery delivery1 = new Delivery(1L, generateMockWarehouse(), generateMockStore(), generatedMockGoods(), 10L, new BigDecimal(100), "Delivered", new Date(2022, 12, 12));

        when(deliveryRepository.findById(id)).thenReturn(Optional.of(delivery1));
        BaseResponse actualResponse = deliveryService.getDeliveryById(id);
        DeliveryResponse actualData = (DeliveryResponse) actualResponse.getData();

        Assertions.assertEquals(delivery1.getId(), actualData.getId());
    }

    @Test
    void testGetDeliveryWithInvalidId() {
        Long id = 2L;
        Delivery delivery1 = new Delivery(1L, generateMockWarehouse(), generateMockStore(), generatedMockGoods(), 10L, new BigDecimal(100), "Delivered", new Date(2022, 12, 12));

        Optional<Delivery> deliveryOptional = Optional.empty();
        when(deliveryRepository.findById(id)).thenReturn(deliveryOptional);
        BaseResponse result = deliveryService.getDeliveryById(id);

        Assertions.assertNull(result.getData());
        Assertions.assertNotEquals(delivery1.getId(), result.getData());
    }

    @Test
    void testSaveDeliveryWithValidData() {

        Long id = 1L;
        Delivery delivery1 = new Delivery(id, generateMockWarehouse(), generateMockStore(), generatedMockGoods(), 10L, new BigDecimal(100), "Delivered", new Date(2022, 12, 12));

        DeliveryRequest request = new DeliveryRequest();
        request.setWarehouseId(delivery1.getWarehouse().getId());
        request.setStoreId(delivery1.getStore().getId());
        request.setGoodsId(delivery1.getGoods().getId());
        request.setQty(delivery1.getQty());
        request.setStatus(delivery1.getStatus());
        request.setDate(delivery1.getDate());

        when(warehouseRepository.findById(delivery1.getWarehouse().getId())).thenReturn(Optional.of(generateMockWarehouse()));
        when(goodsRepository.findById(delivery1.getGoods().getId())).thenReturn(Optional.of(generatedMockGoods()));
        when(storeRepository.findById(delivery1.getStore().getId())).thenReturn(Optional.of(generateMockStore()));
        when(deliveryRepository.save(any())).thenReturn(delivery1);

        BaseResponse actualResponse = deliveryService.saveDelivery(request);
        DeliveryResponse actualData = (DeliveryResponse) actualResponse.getData();

        Assertions.assertEquals(actualData.getId(), delivery1.getId());
        Assertions.assertNotNull(actualResponse.getData());
    }

}
