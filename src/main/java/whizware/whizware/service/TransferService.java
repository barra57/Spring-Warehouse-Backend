package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.transfer.RequestTransfer;
import whizware.whizware.dto.transfer.ResponseTransfer;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Stock;
import whizware.whizware.entity.Transfer;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.repository.GoodsRepository;
import whizware.whizware.repository.StockRepository;
import whizware.whizware.repository.TransferRepository;
import whizware.whizware.repository.WarehouseRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;

    private final WarehouseRepository warehouseRepository;

    private final GoodsRepository goodsRepository;

    private final StockRepository stockRepository;

    public BaseResponse getAll() {

        List<Transfer> transfers = transferRepository.findAll();
        List<ResponseTransfer> data = new ArrayList<>();

        for (Transfer transfer : transfers) {
            data.add(ResponseTransfer.builder()
                    .id(transfer.getId())
                    .date(new Date())
                    .quantity(transfer.getQuantity())
                    .warehouseId(transfer.getWarehouse().getId())
                    .warehouseTargetId(transfer.getWarehouseTarget().getId())
                    .goodsId(transfer.getGoods().getId())
                    .build());
        }

        if (data.isEmpty()) {
            return BaseResponse.builder()
                    .message("Failed get all transfer data!")
                    .data(null)
                    .build();
        } else {
            return BaseResponse.builder()
                    .message("Success get all transfer data!")
                    .data(data)
                    .build();
        }
    }

    public BaseResponse getTransferById(Long id) {

        Optional<Transfer> transfer = transferRepository.findById(id);

        if (transfer.isEmpty()) {
            return BaseResponse.builder()
                    .message("Failed get transfer data with ID " + id)
                    .data(null)
                    .build();
        }

        ResponseTransfer data = ResponseTransfer.builder()
                .id(id)
                .quantity(transfer.get().getQuantity())
                .date(new Date())
                .warehouseId(transfer.get().getWarehouse().getId())
                .warehouseTargetId(transfer.get().getWarehouseTarget().getId())
                .goodsId(transfer.get().getGoods().getId())
                .build();

        return BaseResponse.builder()
                .message("Success get transfer data with ID " + id)
                .data(data)
                .build();
    }

    public BaseResponse addTransfer(RequestTransfer request) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(request.getWarehouseId());
        if (warehouse.isEmpty()) {
            return BaseResponse.builder()
                    .message("Warehouse with ID " + request.getWarehouseId() + " not found")
                    .build();
        }

        Optional<Warehouse> warehouseTarget = warehouseRepository.findById(request.getWarehouseTargetId());
        if (warehouseTarget.isEmpty()) {
            return BaseResponse.builder()
                    .message("Warehouse with ID " + request.getWarehouseTargetId() + " not found")
                    .build();
        }

        Optional<Goods> goods = goodsRepository.findById(request.getGoodsId());
        if (goods.isEmpty()) {
            return BaseResponse.builder()
                    .message("Goods with ID " + request.getGoodsId() + " not found")
                    .build();
        }

        List<Stock> stockList = stockRepository.findByWarehouseIdAndGoodsId(request.getWarehouseId(), request.getGoodsId());
        if (stockList.isEmpty() || stockList.get(0).getQuantity() < request.getQuantity()) {
            return BaseResponse.builder()
                    .message("Warehouse " + warehouse.get().getName() + " doesn't have enough " + goods.get().getName())
                    .build();
        }
        Stock stock = stockList.isEmpty() ? new Stock() : stockList.get(0);
        stock.setWarehouse(warehouse.get());
        stock.setGoods(goods.get());
        stock.setQuantity(Optional.ofNullable(stock.getQuantity()).orElse(0L) - request.getQuantity());

        List<Stock> stockTargetList = stockRepository.findByWarehouseIdAndGoodsId(request.getWarehouseTargetId(), request.getGoodsId());
        Stock stockTarget = stockTargetList.isEmpty() ? new Stock() : stockTargetList.get(0);
        stockTarget.setWarehouse(warehouseTarget.get());
        stockTarget.setGoods(goods.get());
        stockTarget.setQuantity(Optional.ofNullable(stockTarget.getQuantity()).orElse(0L) + request.getQuantity());

        Transfer transfer = new Transfer();
        transfer.setWarehouse(warehouse.get());
        transfer.setWarehouseTarget(warehouseTarget.get());
        transfer.setQuantity(request.getQuantity());
        transfer.setGoods(goods.get());
        transfer.setDate(new Date());

        Transfer savedTransfer = transferRepository.save(transfer);

        stockRepository.save(stock);
        stockRepository.save(stockTarget);

        return BaseResponse.builder()
                .message("Transfer success")
                .data(ResponseTransfer.builder()
                        .id(savedTransfer.getId())
                        .warehouseId(savedTransfer.getWarehouse().getId())
                        .warehouseTargetId(savedTransfer.getWarehouseTarget().getId())
                        .goodsId(savedTransfer.getGoods().getId())
                        .quantity(savedTransfer.getQuantity())
                        .date(savedTransfer.getDate())
                        .build())
                .build();

    }

}
