package whizware.whizware.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.delivery.DeliveryRequest;
import whizware.whizware.dto.delivery.DeliveryResponse;
import whizware.whizware.entity.*;
import whizware.whizware.repository.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final WarehouseRepository warehouseRepository;
    private final StoreRepository storeRepository;
    private final GoodsRepository goodsRepository;
    private final StockRepository stockRepository;

    public BaseResponse getAllDelivery() {
        List<Delivery> delivery = deliveryRepository.findAll();

        List<DeliveryResponse> data = new ArrayList<>();

        for (Delivery deliv : delivery) {
            data.add(DeliveryResponse.builder()
                    .id(deliv.getId())
                    .warehouseId(deliv.getWarehouse().getId())
                    .storeId(deliv.getStore().getId())
                    .goodsId(deliv.getGoods().getId())
                    .quantity(deliv.getQuantity())
                    .totalPrice(deliv.getTotalPrice())
                    .date(deliv.getDate())
                    .build());
        }

        return BaseResponse.builder()
                .message("Get all delivery list success")
                .data(data)
                .build();
    }

    public BaseResponse getAllDeliveryByWarehouseId(Long warehouseId) {
        List<Delivery> delivery = deliveryRepository.findByWarehouseId(warehouseId);

        List<DeliveryResponse> data = new ArrayList<>();
        for (Delivery deliv : delivery) {
            data.add(DeliveryResponse.builder()
                    .id(deliv.getId())
                    .warehouseId(deliv.getWarehouse().getId())
                    .storeId(deliv.getStore().getId())
                    .goodsId(deliv.getGoods().getId())
                    .quantity(deliv.getQuantity())
                    .totalPrice(deliv.getTotalPrice())
                    .date(deliv.getDate())
                    .build());
        }

        return BaseResponse.builder()
                .message("Get Delivery by warehouse ID " + warehouseId + " Success")
                .data(data)
                .build();
    }

    public BaseResponse getDeliveryById(Long id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        if (delivery.isEmpty()) {
            return BaseResponse.builder()
                    .message("Delivery with ID " + id + " Not Found")
                    .data(null)
                    .build();
        }

        DeliveryResponse data = DeliveryResponse.builder()
                .id(delivery.get().getId())
                .warehouseId(delivery.get().getWarehouse().getId())
                .storeId(delivery.get().getStore().getId())
                .goodsId(delivery.get().getGoods().getId())
                .quantity(delivery.get().getQuantity())
                .totalPrice(delivery.get().getTotalPrice())
                .date(delivery.get().getDate())
                .build();

        return BaseResponse.builder()
                .message("Get delivery with ID " + id + " Success")
                .data(data)
                .build();
    }

    public BaseResponse saveDelivery(DeliveryRequest request) {

        Optional<Warehouse> warehouse = warehouseRepository.findById(request.getWarehouseId());
        if (warehouse.isEmpty()) {
            return BaseResponse.builder()
                    .message("Warehouse with ID " + request.getWarehouseId() + " not found")
                    .build();
        }

        Optional<Store> store = storeRepository.findById(request.getStoreId());
        if (store.isEmpty()) {
            return BaseResponse.builder()
                    .message("Store with ID " + request.getStoreId() + " not found")
                    .build();
        }

        Optional<Goods> goods = goodsRepository.findById(request.getGoodsId());
        if (goods.isEmpty()) {
            return BaseResponse.builder()
                    .message("Goods with ID " + request.getStoreId() + " not found")
                    .build();
        }

        List<Stock> stockList = stockRepository.findByWarehouseIdAndGoodsId(request.getWarehouseId(), request.getGoodsId());
        if (stockList.isEmpty() || stockList.get(0).getQuantity() < request.getQuantity()) {
            return BaseResponse.builder()
                    .message("Warehouse " + warehouse.get().getName() + " doesn't have enough " + goods.get().getName())
                    .build();
        }

        Stock stock = stockList.get(0);
        stock.setWarehouse(warehouse.get());
        stock.setGoods(goods.get());
        stock.setQuantity(stock.getQuantity() - request.getQuantity());
        stockRepository.save(stock);

        Delivery delivery = new Delivery();
        delivery.setWarehouse(warehouse.get());
        delivery.setStore(store.get());
        delivery.setGoods(goods.get());
        delivery.setQuantity(request.getQuantity());
        delivery.setTotalPrice(goods.get().getSellingPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        delivery.setDate(new Date());
        Delivery savedDelivery = deliveryRepository.save(delivery);

        return BaseResponse.builder()
                .message("Delivery success")
                .data(DeliveryResponse.builder()
                        .id(savedDelivery.getId())
                        .warehouseId(savedDelivery.getWarehouse().getId())
                        .goodsId(savedDelivery.getGoods().getId())
                        .quantity(savedDelivery.getQuantity())
                        .totalPrice(savedDelivery.getTotalPrice())
                        .date(savedDelivery.getDate())
                        .build())
                .build();
    }

}
