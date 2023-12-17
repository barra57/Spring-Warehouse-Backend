package whizware.whizware.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.receipt.ReceiptRequest;
import whizware.whizware.dto.receipt.ReceiptResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Receipt;
import whizware.whizware.entity.Stock;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.repository.GoodsRepository;
import whizware.whizware.repository.ReceiptRepository;
import whizware.whizware.repository.StockRepository;
import whizware.whizware.repository.WarehouseRepository;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    public final ReceiptRepository receiptRepository;
    public final WarehouseRepository warehouseRepository;
    public final GoodsRepository goodsRepository;
    public final StockRepository stockRepository;

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
                .message("Success")
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
                .message("Success")
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
        Optional<Warehouse> warehouse = warehouseRepository.findById(request.getWarehouseId());
        if (warehouse.isEmpty()) {
            return BaseResponse.builder()
                    .message("Warehouse with ID " + request.getWarehouseId() + " not found")
                    .build();
        }
        Optional<Goods> goods = goodsRepository.findById(request.getGoodsId());
        if (goods.isEmpty()) {
            return BaseResponse.builder()
                    .message("Goods with ID " + request.getGoodsId() + " not found")
                    .build();
        }

        List<Stock> stockList = stockRepository.findByWarehouseIdAndGoodsId(request.getWarehouseId(), request.getGoodsId());

//        Stock stock = Optional.ofNullable(stockList.get(0)).orElse(new Stock());
        Stock stock = stockList.isEmpty() ? new Stock() : stockList.get(0);
        stock.setWarehouse(warehouse.get());
        stock.setGoods(goods.get());
        stock.setQuantity(Optional.ofNullable(stock.getQuantity()).orElse(0L) + request.getQuantity());
        stockRepository.save(stock);

        Receipt receipt = new Receipt();
        receipt.setWarehouse(warehouse.get());
        receipt.setGoods(goods.get());
        receipt.setSuplier(request.getSuplier());
        receipt.setQuantity(request.getQuantity());
        receipt.setTotalPrice(goods.get().getPurchasePrice().multiply(BigDecimal.valueOf(request.getQuantity())));
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
