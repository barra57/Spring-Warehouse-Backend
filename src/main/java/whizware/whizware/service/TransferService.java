package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.transfer.RequestTransfer;
import whizware.whizware.dto.transfer.ResponseTransfer;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Transfer;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.repository.GoodsRepository;
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

    public BaseResponse addTransfer(RequestTransfer request) {
        Transfer transfer = new Transfer();

        Optional<Warehouse> optWH = warehouseRepository.findById(request.getWarehouse_id());
        Optional<Warehouse> optWHTarget = warehouseRepository.findById(request.getWarehouse_target_id());
        Optional<Goods> optGoods = goodsRepository.findById(request.getGoods_id());

        transfer.setQuantity(request.getQuantity());
        transfer.setDate(new Date());
        transfer.setStatus(request.getStatus());
        transfer.setWarehouse(optWH.get());
        transfer.setWarehouse_target(optWHTarget.get());
        transfer.setGoods(optGoods.get());
        transferRepository.save(transfer);

        ResponseTransfer data = ResponseTransfer.builder()
                .id(transfer.getId())
                .date(new Date())
                .quantity(transfer.getQuantity())
                .status(transfer.getStatus())
                .warehouse_id(transfer.getWarehouse().getId())
                .warehouse_target_id(transfer.getWarehouse_target().getId())
                .goods_id(transfer.getGoods().getId())
                .build();

        return BaseResponse.builder()
                .message("Success add data!")
                .data(data)
                .build();
    }

    public BaseResponse updateTransfer(Long id, RequestTransfer request) {
        Optional<Transfer> optTransfer = transferRepository.findById(id);

        Optional<Warehouse> optWH = warehouseRepository.findById(request.getWarehouse_id());
        Optional<Warehouse> optWHTarget = warehouseRepository.findById(request.getWarehouse_target_id());
        Optional<Goods> optGoods = goodsRepository.findById(request.getGoods_id());

        Transfer transfer = new Transfer();

        if (optTransfer.isPresent()) {
            transfer.setQuantity(request.getQuantity());
            transfer.setStatus(request.getStatus());
            transfer.setWarehouse(optWH.get());
            transfer.setWarehouse_target(optWHTarget.get());
            transfer.setGoods(optGoods.get());
            transferRepository.save(transfer);

            ResponseTransfer data = ResponseTransfer.builder()
                    .id(id)
                    .date(new Date())
                    .quantity(transfer.getQuantity())
                    .status(transfer.getStatus())
                    .warehouse_id(transfer.getWarehouse().getId())
                    .warehouse_target_id(transfer.getWarehouse_target().getId())
                    .goods_id(transfer.getGoods().getId())
                    .build();

            return BaseResponse.builder()
                    .message("Success update data!")
                    .data(data)
                    .build();
        } else {
            return BaseResponse.builder()
                    .message("Failed update data!")
                    .data(null)
                    .build();
        }
    }

    public BaseResponse getAll() {

        List<Transfer> transfers = transferRepository.findAll();
        List<ResponseTransfer> data = new ArrayList<>();

        for (Transfer transfer: transfers) {
            data.add(ResponseTransfer.builder()
                            .id(transfer.getId())
                            .date(new Date())
                            .quantity(transfer.getQuantity())
                            .status(transfer.getStatus())
                            .warehouse_id(transfer.getWarehouse().getId())
                            .warehouse_target_id(transfer.getWarehouse_target().getId())
                            .goods_id(transfer.getGoods().getId())
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
                .status(transfer.get().getStatus())
                .date(new Date())
                .warehouse_id(transfer.get().getWarehouse().getId())
                .warehouse_target_id(transfer.get().getWarehouse_target().getId())
                .goods_id(transfer.get().getGoods().getId())
                .build();

        return BaseResponse.builder()
                .message("Success get transfer data with ID " + id)
                .data(data)
                .build();
    }

    public BaseResponse deleteTransfer(Long id) {
        Optional<Transfer> data = transferRepository.findById(id);

        if (data.isEmpty()){
            return BaseResponse.builder()
                    .message("Failed delete transfer data with ID " + id)
                    .data(null)
                    .build();
        }

        transferRepository.deleteById(id);

        return BaseResponse.builder()
                .message("Succes delete transfer data with ID " + id)
                .data(data)
                .build();
    }
}
