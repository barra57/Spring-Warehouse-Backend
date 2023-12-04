package whizware.whizware.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.receipt.ReceiptRequest;
import whizware.whizware.dto.receipt.ReceiptResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Receipt;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.repository.GoodsRepository;
import whizware.whizware.repository.ReceiptRepository;
import whizware.whizware.repository.WarehouseRepository;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    public final ReceiptRepository receiptRepository;
    public final WarehouseRepository warehouseRepository;
    public final GoodsRepository goodsRepository;

    public BaseResponse getAllReceipt() {
        List<Receipt> receipts = receiptRepository.findAll();

        List<ReceiptResponse> data = new ArrayList<>();
        for (Receipt receipt : receipts) {
            data.add(ReceiptResponse.builder()
                    .id(receipt.getId())
                    .warehouseId(receipt.getWarehouse().getId())
                    .goodsId(receipt.getGoods().getId())
                    .quantity(receipt.getQuantity())
                    .totalPrice(receipt.getTotalPrice())
                    .supplier(receipt.getSuplier())
                    .date(receipt.getDate())
                    .build());
        }

        return BaseResponse.builder()
                .message("berhasil")
                .data(data)
                .build();
    }

    public BaseResponse getReceiptById(Long id) {
        Optional<Receipt> receipt = receiptRepository.findById(id);
        if (receipt.isEmpty()) {
            return BaseResponse.builder()
                    .message("Receipt with ID " + id + " is not found!")
                    .build();
        }

        ReceiptResponse data = ReceiptResponse.builder()
                .id(receipt.get().getId())
                .warehouseId(receipt.get().getWarehouse().getId())
                .goodsId(receipt.get().getGoods().getId())
                .quantity(receipt.get().getQuantity())
                .totalPrice(receipt.get().getTotalPrice())
                .supplier(receipt.get().getSuplier())
                .date(receipt.get().getDate())
                .build();

        return BaseResponse.builder()
                .message("berhasil")
                .data(data)
                .build();
    }

    public BaseResponse getAllReceiptByWarehouseId(Long warehouseId) {
        List<Receipt> receipts = receiptRepository.findByWarehouseId(warehouseId);

        List<ReceiptResponse> data = new ArrayList<>();
        for (Receipt receipt : receipts) {
            data.add(ReceiptResponse.builder()
                    .id(receipt.getId())
                    .warehouseId(receipt.getWarehouse().getId())
                    .goodsId(receipt.getGoods().getId())
                    .quantity(receipt.getQuantity())
                    .totalPrice(receipt.getTotalPrice())
                    .supplier(receipt.getSuplier())
                    .date(receipt.getDate())
                    .build());
        }

        return BaseResponse.builder()
                .message("berhasil")
                .data(data)
                .build();
    }

    public BaseResponse saveReceipt(ReceiptRequest request) {
        Receipt receipt = new Receipt();

        Optional<Warehouse> warehouse = warehouseRepository.findById(request.getWarehouseId());
        if (warehouse.isEmpty()) {
            return BaseResponse.builder()
                    .message("Warehouse with ID " + request.getWarehouseId() + " is not found!")
                    .build();
        }
        receipt.setWarehouse(warehouse.get());

        Optional<Goods> goods = goodsRepository.findById(request.getGoodsId());
        if (goods.isEmpty()) {
            return BaseResponse.builder()
                    .message("Goods with ID " + request.getGoodsId() + " is not found!")
                    .build();
        }
        receipt.setGoods(goods.get());

        receipt.setQuantity(request.getQuantity());
        receipt.setSuplier(request.getSuplier());
        receipt.setDate(request.getDate());
        receipt.setTotalPrice(new BigDecimal(goods.get().getPurchasePrice() * request.getQuantity()));

        Receipt savedReceipt = receiptRepository.save(receipt);
        ReceiptResponse data = ReceiptResponse.builder()
                .id(savedReceipt.getId())
                .warehouseId(savedReceipt.getWarehouse().getId())
                .goodsId(savedReceipt.getGoods().getId())
                .quantity(savedReceipt.getQuantity())
                .totalPrice(savedReceipt.getTotalPrice())
                .supplier(savedReceipt.getSuplier())
                .date(savedReceipt.getDate())
                .build();

        return BaseResponse.builder()
                .message("berhasil")
                .data(data)
                .build();
    }

    public BaseResponse updateReceipt(Long id, ReceiptRequest request) {
        Optional<Receipt> receiptOptional = receiptRepository.findById(id);
        if (receiptOptional.isEmpty()) {
            return BaseResponse.builder()
                    .message("Receipt with ID " + id + " is not found!")
                    .build();
        }
        Receipt receipt = receiptOptional.get();

        Optional<Warehouse> warehouse = warehouseRepository.findById(request.getWarehouseId());
        if (warehouse.isEmpty()) {
            return BaseResponse.builder()
                    .message("Warehouse with ID " + id + " is not found!")
                    .build();
        }
        receipt.setWarehouse(warehouse.get());

        Optional<Goods> goods = goodsRepository.findById(request.getGoodsId());
        if (goods.isEmpty()) {
            return BaseResponse.builder()
                    .message("Goods with ID " + id + " is not found!")
                    .build();
        }
        receipt.setGoods(goods.get());

        receipt.setQuantity(request.getQuantity());
        receipt.setSuplier(request.getSuplier());
        receipt.setDate(request.getDate());
        receipt.setTotalPrice(new BigDecimal(goods.get().getPurchasePrice() * request.getQuantity()));

        Receipt savedReceipt = receiptRepository.save(receipt);
        ReceiptResponse data = ReceiptResponse.builder()
                .id(savedReceipt.getId())
                .warehouseId(savedReceipt.getWarehouse().getId())
                .goodsId(savedReceipt.getGoods().getId())
                .quantity(savedReceipt.getQuantity())
                .totalPrice(savedReceipt.getTotalPrice())
                .supplier(savedReceipt.getSuplier())
                .date(savedReceipt.getDate())
                .build();

        return BaseResponse.builder()
                .message("berhasil")
                .data(data)
                .build();
    }

    public BaseResponse deleteReceipt(Long id) {
        Optional<Receipt> receipt = receiptRepository.findById(id);
        if (receipt.isEmpty()) {
            return BaseResponse.builder()
                    .message("Receipt with ID " + id + " is not found!")
                    .build();
        }
        receiptRepository.delete(receipt.get());
        return BaseResponse.builder()
            .message("berhasil")
            .build();
    }

}
