package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.delivery.DeliveryRequest;
import whizware.whizware.dto.delivery.DeliveryResponse;
import whizware.whizware.entity.*;
import whizware.whizware.repository.DeliveryRepository;
import whizware.whizware.repository.GoodsRepository;
import whizware.whizware.repository.StoreRepository;
import whizware.whizware.repository.WarehouseRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final WarehouseRepository warehouseRepository;
    private final StoreRepository storeRepository;
    private final GoodsRepository goodsRepository;

    public BaseResponse getAllDelivery() {
        List<Delivery> delivery = deliveryRepository.findAll();

        List<DeliveryResponse> data = new ArrayList<>();

        for (Delivery deliv : delivery) {
            data.add(DeliveryResponse.builder()
                            .id(deliv.getId())
                            .warehouseId(deliv.getWarehouse().getId())
                            .storeId(deliv.getStore().getId())
                            .goodsId(deliv.getGoods().getId())
                            .qty(deliv.getQty())
                            .totalPrice(deliv.getTotalPrice())
                            .status(deliv.getStatus())
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
                            .qty(deliv.getQty())
                            .totalPrice(deliv.getTotalPrice())
                            .status(deliv.getStatus())
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
                .qty(delivery.get().getQty())
                .totalPrice(delivery.get().getTotalPrice())
                .status(delivery.get().getStatus())
                .date(delivery.get().getDate())
                .build();

        return BaseResponse.builder()
                .message("Get delivery with ID " + id + " Success")
                .data(data)
                .build();
    }

    public BaseResponse saveDelivery(DeliveryRequest deliveryRequest) {
        Delivery delivery = new Delivery();

        Optional<Warehouse> warehouse = warehouseRepository.findById(deliveryRequest.getWarehouseId());
        if (warehouse.isEmpty()) {
            return BaseResponse.builder()
                    .message("Warehouse with ID " + deliveryRequest.getWarehouseId() + " is not found!")
                    .data(null)
                    .build();
        }
        delivery.setWarehouse(warehouse.get());

        Optional<Store> store = storeRepository.findById(deliveryRequest.getStoreId());
        if (store.isEmpty()) {
            return BaseResponse.builder()
                    .message("Store with ID " + deliveryRequest.getStoreId() + " is not found!")
                    .data(null)
                    .build();
        }
        delivery.setStore(store.get());

        Optional<Goods> goods = goodsRepository.findById(deliveryRequest.getGoodsId());
        if (goods.isEmpty()) {
            return BaseResponse.builder()
                    .message("Goods with ID " + deliveryRequest.getGoodsId() + " is not found!")
                    .data(null)
                    .build();
        }
        delivery.setGoods(goods.get());

        delivery.setQty(deliveryRequest.getQty());
        delivery.setTotalPrice(new BigDecimal(goods.get().getPurchasePrice() * deliveryRequest.getQty()));
        delivery.setStatus(deliveryRequest.getStatus());
        delivery.setDate(deliveryRequest.getDate());

        Delivery saveDelivery = deliveryRepository.save(delivery);
        DeliveryResponse data = DeliveryResponse.builder()
                .id(saveDelivery.getId())
                .warehouseId(saveDelivery.getWarehouse().getId())
                .storeId(saveDelivery.getStore().getId())
                .goodsId(saveDelivery.getGoods().getId())
                .qty(saveDelivery.getQty())
                .totalPrice(saveDelivery.getTotalPrice())
                .status(saveDelivery.getStatus())
                .date(saveDelivery.getDate())
                .build();

        return BaseResponse.builder()
                .message("Success Added data")
                .data(data)
                .build();
    }

    public BaseResponse updateDelivery(Long id, DeliveryRequest deliveryRequest) {
        Optional<Delivery> deliveryOptional = deliveryRepository.findById(id);
        if (deliveryOptional.isEmpty()) {
            return BaseResponse.builder()
                    .message("Delivery with ID " + id + " Not found")
                    .data(null)
                    .build();
        }
        Delivery delivery = deliveryOptional.get();

        Optional<Warehouse> warehouse = warehouseRepository.findById(deliveryRequest.getWarehouseId());
        if (warehouse.isEmpty()) {
            return BaseResponse.builder()
                    .message("Warehouse with ID " + deliveryRequest.getWarehouseId() + " is not found!")
                    .data(null)
                    .build();
        }
        delivery.setWarehouse(warehouse.get());

        Optional<Store> store = storeRepository.findById(deliveryRequest.getStoreId());
        if (store.isEmpty()) {
            return BaseResponse.builder()
                    .message("Store with ID " + deliveryRequest.getStoreId() + " is not found!")
                    .data(null)
                    .build();
        }
        delivery.setStore(store.get());

        Optional<Goods> goods = goodsRepository.findById(deliveryRequest.getGoodsId());
        if (goods.isEmpty()) {
            return BaseResponse.builder()
                    .message("Goods with ID " + deliveryRequest.getGoodsId() + " is not found!")
                    .data(null)
                    .build();
        }
        delivery.setGoods(goods.get());

        delivery.setQty(deliveryRequest.getQty());
        delivery.setTotalPrice(new BigDecimal(goods.get().getPurchasePrice() * deliveryRequest.getQty()));
        delivery.setStatus(deliveryRequest.getStatus());
        delivery.setDate(deliveryRequest.getDate());

        Delivery saveDelivery = deliveryRepository.save(delivery);
        DeliveryResponse data = DeliveryResponse.builder()
                .id(saveDelivery.getId())
                .warehouseId(saveDelivery.getWarehouse().getId())
                .storeId(saveDelivery.getStore().getId())
                .goodsId(saveDelivery.getGoods().getId())
                .qty(saveDelivery.getQty())
                .totalPrice(saveDelivery.getTotalPrice())
                .status(saveDelivery.getStatus())
                .date(saveDelivery.getDate())
                .build();

        return BaseResponse.builder()
                .message("Success Updated data with ID " + id)
                .data(data)
                .build();
    }

    public BaseResponse deleteDelivery(Long id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        if (delivery.isEmpty()) {
            return BaseResponse.builder()
                    .message("Delivery with ID " + id + " is not found!")
                    .data(null)
                    .build();
        }
        deliveryRepository.delete(delivery.get());
        return BaseResponse.builder()
                .message("Delete Success...")
                .data(null)
                .build();
    }
}
