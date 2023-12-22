package whizware.whizware.util;

import whizware.whizware.dto.receipt.ReceiptRequest;
import whizware.whizware.dto.receipt.ReceiptResponse;
import whizware.whizware.dto.warehouse.WarehouseRequest;
import whizware.whizware.dto.warehouse.WarehouseResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Receipt;
import whizware.whizware.entity.Warehouse;

import java.math.BigDecimal;
import java.util.Date;

public class TestUtilities {

    public static WarehouseResponse generateWarehouseResponse(Long id, String name, Long locationId) {
        return WarehouseResponse.builder()
                .id(id)
                .name(name)
                .locationId(locationId)
                .build();
    }

    public static WarehouseRequest generateWarehouseRequest(String name, Long locationId) {
        WarehouseRequest request = new WarehouseRequest();
        request.setName(name);
        request.setLocationId(locationId);
        return request;
    }

    public static Warehouse generateWarehouse(Long id, String name, Location location) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(id);
        warehouse.setName(name);
        warehouse.setLocation(location);
        return warehouse;
    }

    public static ReceiptResponse generateReceiptResponse(
            Long id,
            Long warehouseId,
            Long goodsId,
            Long quantity,
            BigDecimal totalPrice,
            String supplier,
            Date date
    ) {
        return ReceiptResponse.builder()
                .id(id)
                .warehouseId(warehouseId)
                .goodsId(goodsId)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .supplier(supplier)
                .date(date)
                .build();
    }

    public static ReceiptRequest generateReceiptRequest(
            Long warehouseId,
            Long goodsId,
            Long quantity,
            BigDecimal totalPrice,
            String supplier,
            Date date
    ) {
        ReceiptRequest request = new ReceiptRequest();
        request.setWarehouseId(warehouseId);
        request.setGoodsId(goodsId);
        request.setQuantity(quantity);
//        request.setTotalPrice(totalPrice);
        request.setSuplier(supplier);
//        request.setDate(date);
        return request;
    }

    public static Receipt generateReceipt(
            Long id,
            Warehouse warehouse,
            Goods goods,
            Long quantity,
            BigDecimal totalPrice,
            String supplier,
            Date date
    ) {
        Receipt receipt = new Receipt();
        receipt.setId(id);
        receipt.setWarehouse(warehouse);
        receipt.setGoods(goods);
        receipt.setQuantity(quantity);
        receipt.setTotalPrice(totalPrice);
        receipt.setSuplier(supplier);
        receipt.setDate(date);
        return receipt;
    }

    public static Goods generateGoods(
            Long id,
            String name,
            String description,
            Long purchasePrice,
            Long sellingPrice
    ) {
        Goods goods = new Goods();
        goods.setId(id);
        goods.setName(name);
        goods.setDescription(description);
//        goods.setPurchasePrice(purchasePrice);
//        goods.setSellingPrice(sellingPrice);
        return goods;
    }

    public static Location generateLocation(Long id, String name) {
        Location location = new Location();
        location.setId(id);
        location.setName(name);
        return location;
    }

}
