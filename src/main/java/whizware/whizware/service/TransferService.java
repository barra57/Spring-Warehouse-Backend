package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.transfer.RequestTransfer;
import whizware.whizware.dto.transfer.ResponseTransfer;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Transfer;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.GoodsRepository;
import whizware.whizware.repository.TransferRepository;
import whizware.whizware.repository.WarehouseRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final StockService stockService;

    private final TransferRepository transferRepository;
    private final WarehouseRepository warehouseRepository;
    private final GoodsRepository goodsRepository;

    public BaseResponse getAll() {
        List<Transfer> transfers = transferRepository.findAll();
        if (transfers.isEmpty())
            throw new NoContentException("Transfer is empty");

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

        return BaseResponse.builder()
                .message("Success get all transfer data!")
                .data(data)
                .build();
    }

    public BaseResponse getTransferById(Long id) {
        Transfer transfer = transferRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Transfer with ID %d not found", id)));

        ResponseTransfer data = ResponseTransfer.builder()
                .id(id)
                .quantity(transfer.getQuantity())
                .date(new Date())
                .warehouseId(transfer.getWarehouse().getId())
                .warehouseTargetId(transfer.getWarehouseTarget().getId())
                .goodsId(transfer.getGoods().getId())
                .build();

        return BaseResponse.builder()
                .message("Success get transfer data with ID " + id)
                .data(data)
                .build();
    }

    public BaseResponse addTransfer(RequestTransfer request) {
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId()).orElseThrow(() -> new NotFoundException(String.format("Warehouse with ID %d not found", request.getWarehouseId())));
        Warehouse warehouseTarget = warehouseRepository.findById(request.getWarehouseTargetId()).orElseThrow(() -> new NotFoundException(String.format("Warehouse Target with ID %d not found", request.getWarehouseId())));
        Goods goods = goodsRepository.findById(request.getGoodsId()).orElseThrow(() -> new NotFoundException(String.format("Goods with ID %d not found", request.getGoodsId())));

        stockService.subtractStock(warehouse, goods, request.getQuantity());
        stockService.addStock(warehouseTarget, goods, request.getQuantity());

        Transfer transfer = new Transfer();
        transfer.setWarehouse(warehouse);
        transfer.setWarehouseTarget(warehouseTarget);
        transfer.setQuantity(request.getQuantity());
        transfer.setGoods(goods);
        transfer.setDate(new Date());

        Transfer savedTransfer = transferRepository.save(transfer);

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
