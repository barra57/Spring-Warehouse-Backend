package whizware.whizware.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.delivery.DeliveryRequest;
import whizware.whizware.dto.delivery.DeliveryResponse;
import whizware.whizware.entity.*;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final StockService stockService;

    private final DeliveryRepository deliveryRepository;
    private final WarehouseRepository warehouseRepository;
    private final StoreRepository storeRepository;
    private final GoodsRepository goodsRepository;

    private static final String SUCCESS_MESSAGE = "Success";

    public BaseResponse getAllDelivery() {
        List<Delivery> delivery = deliveryRepository.findAll();
        if (delivery.isEmpty())
            throw new NoContentException("Delivery is empty");

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
                .message(SUCCESS_MESSAGE)
                .data(data)
                .build();
    }

    public BaseResponse getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Delivery with ID %d Not Found", id)));

        DeliveryResponse data = DeliveryResponse.builder()
                .id(delivery.getId())
                .warehouseId(delivery.getWarehouse().getId())
                .storeId(delivery.getStore().getId())
                .goodsId(delivery.getGoods().getId())
                .quantity(delivery.getQuantity())
                .totalPrice(delivery.getTotalPrice())
                .date(delivery.getDate())
                .build();

        return BaseResponse.builder()
                .message(SUCCESS_MESSAGE)
                .data(data)
                .build();
    }

    public BaseResponse getAllDeliveryByWarehouseId(Long warehouseId) {
        List<Delivery> delivery = deliveryRepository.findByWarehouseId(warehouseId);
        if (delivery.isEmpty())
            throw new NotFoundException(String.format("No Delivery for Warehouse with ID %d", warehouseId));

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
                .message(SUCCESS_MESSAGE)
                .data(data)
                .build();
    }

    public BaseResponse saveDelivery(DeliveryRequest request) {
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId()).orElseThrow(() -> new NotFoundException(String.format("Warehouse with ID %d not found", request.getWarehouseId())));
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(() -> new NotFoundException(String.format("Store with ID %d not found", request.getStoreId())));
        Goods goods = goodsRepository.findById(request.getGoodsId()).orElseThrow(() -> new NotFoundException(String.format("Goods with ID %d not found", request.getGoodsId())));

        stockService.subtractStock(warehouse, goods, request.getQuantity());

        Delivery delivery = new Delivery();
        delivery.setWarehouse(warehouse);
        delivery.setStore(store);
        delivery.setGoods(goods);
        delivery.setQuantity(request.getQuantity());
        delivery.setTotalPrice(goods.getSellingPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        delivery.setDate(new Date());
        Delivery savedDelivery = deliveryRepository.save(delivery);

        return BaseResponse.builder()
                .message("Delivery success")
                .data(DeliveryResponse.builder()
                        .id(savedDelivery.getId())
                        .warehouseId(savedDelivery.getWarehouse().getId())
                        .storeId(store.getId())
                        .goodsId(savedDelivery.getGoods().getId())
                        .quantity(savedDelivery.getQuantity())
                        .totalPrice(savedDelivery.getTotalPrice())
                        .date(savedDelivery.getDate())
                        .build())
                .build();
    }

}