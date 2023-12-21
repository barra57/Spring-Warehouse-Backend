package whizware.whizware.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.receipt.ReceiptRequest;
import whizware.whizware.dto.receipt.ReceiptResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Receipt;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.GoodsRepository;
import whizware.whizware.repository.ReceiptRepository;
import whizware.whizware.repository.WarehouseRepository;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    public final StockService stockService;

    public final ReceiptRepository receiptRepository;
    public final WarehouseRepository warehouseRepository;
    public final GoodsRepository goodsRepository;

    public BaseResponse getAllReceipt() {
        List<Receipt> receipts = receiptRepository.findAll();
        if (receipts.isEmpty())
            throw new NoContentException("Receipt is empty");

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
                .message("Success")
                .data(data)
                .build();
    }

    public BaseResponse getReceiptById(Long id) {
        Receipt receipt = receiptRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Receipt with ID %d is not found!", id)));

        ReceiptResponse data = ReceiptResponse.builder()
                .id(receipt.getId())
                .warehouseId(receipt.getWarehouse().getId())
                .goodsId(receipt.getGoods().getId())
                .quantity(receipt.getQuantity())
                .totalPrice(receipt.getTotalPrice())
                .supplier(receipt.getSuplier())
                .date(receipt.getDate())
                .build();

        return BaseResponse.builder()
                .message("Success")
                .data(data)
                .build();
    }

    public BaseResponse getAllReceiptByWarehouseId(Long warehouseId) {
        List<Receipt> receipts = receiptRepository.findByWarehouseId(warehouseId);
        if (receipts.isEmpty())
            throw new NoContentException(String.format("No Receipt for Warehouse with ID %d", warehouseId));

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
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(String.format("Warehouse with ID %d not found", request.getWarehouseId())));
        Goods goods = goodsRepository.findById(request.getGoodsId()).
                orElseThrow(() -> new NotFoundException(String.format("Goods with ID %d not found", request.getGoodsId())));

        stockService.addStock(warehouse, goods, request.getQuantity());

        Receipt receipt = new Receipt();
        receipt.setWarehouse(warehouse);
        receipt.setGoods(goods);
        receipt.setSuplier(request.getSuplier());
        receipt.setQuantity(request.getQuantity());
        receipt.setTotalPrice(goods.getPurchasePrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        receipt.setDate(new Date());
        Receipt savedReceipt = receiptRepository.save(receipt);

        return BaseResponse.builder()
                .message("Receipt success")
                .data(ReceiptResponse.builder()
                        .id(savedReceipt.getId())
                        .warehouseId(savedReceipt.getWarehouse().getId())
                        .goodsId(savedReceipt.getGoods().getId())
                        .supplier(savedReceipt.getSuplier())
                        .quantity(savedReceipt.getQuantity())
                        .totalPrice(savedReceipt.getTotalPrice())
                        .date(savedReceipt.getDate())
                        .build())
                .build();
    }

}
