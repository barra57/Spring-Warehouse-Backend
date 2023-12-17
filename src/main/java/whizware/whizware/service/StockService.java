package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.stock.StockRequest;
import whizware.whizware.dto.stock.StockResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Stock;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.repository.GoodsRepository;
import whizware.whizware.repository.StockRepository;
import whizware.whizware.repository.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final WarehouseRepository warehouseRepository;
    private final GoodsRepository goodsRepository;

//    public BaseResponse saveStock(StockRequest stockRequest) {
//        Stock stock = new Stock();
//
//        Optional<Warehouse> warehouse = warehouseRepository.findById(stockRequest.getWarehouseId());
//        if (warehouse.isEmpty()) {
//            return BaseResponse.builder()
//                    .message("Warehouse with ID " + stockRequest.getWarehouseId() + " Not found" )
//                    .data(null)
//                    .build();
//        }
//
//        Optional<Goods> goods = goodsRepository.findById(stockRequest.getGoodsId());
//        if (goods.isEmpty()) {
//            return BaseResponse.builder()
//                    .message("Goods with ID " + stockRequest.getGoodsId() + " Not found")
//                    .data(null)
//                    .build();
//        }
//
//        stock.setWarehouse(warehouse.get());
//        stock.setGoods(goods.get());
//        stock.setQuantity(stockRequest.getQty());
//        Stock saveStock = stockRepository.save(stock);
//
//        StockResponse data = StockResponse.builder()
//                .id(saveStock.getId())
//                .warehouseId(saveStock.getWarehouse().getId())
//                .goodsId(saveStock.getGoods().getId())
//                .quantity(saveStock.getQuantity())
//                .build();
//
//        return BaseResponse.builder()
//                .message("Success add data")
//                .data(data)
//                .build();
//    }

    public BaseResponse getStockById(Long id) {
        Optional<Stock> stock = stockRepository.findById(id);
        if (stock.isEmpty()) {
            return BaseResponse.builder()
                    .message("Stock with ID " + id + " Not found" )
                    .data(null)
                    .build();
        }

        StockResponse data = StockResponse.builder()
                .id(stock.get().getId())
                .warehouseId(stock.get().getWarehouse().getId())
                .goodsId(stock.get().getGoods().getId())
                .quantity(stock.get().getQuantity())
                .build();

        return BaseResponse.builder()
                .message("Success")
                .data(data)
                .build();
    }

    public BaseResponse getAllStock() {
        List<Stock> stock = stockRepository.findAll();
        List<StockResponse> data = new ArrayList<>();
        for (Stock s : stock) {
            data.add(StockResponse.builder()
                            .id(s.getId())
                            .warehouseId(s.getWarehouse().getId())
                            .goodsId(s.getGoods().getId())
                            .quantity(s.getQuantity())
                    .build());
        }

        return BaseResponse.builder()
                .message("Berhasil")
                .data(data)
                .build();
    }

//    public BaseResponse updateStock(Long id, StockRequest stockRequest) {
//
//        Optional<Stock> stockOptional = stockRepository.findById(id);
//        if (stockOptional.isEmpty()) {
//            return BaseResponse.builder()
//                    .message("Stock with ID " + id + " Not found" )
//                    .data(null)
//                    .build();
//        }
//        Stock stock = stockOptional.get();
//
//        Optional<Warehouse> warehouse = warehouseRepository.findById(stockRequest.getWarehouseId());
//        if (warehouse.isEmpty()) {
//            return BaseResponse.builder()
//                    .message("Warehouse with ID " + stockRequest.getWarehouseId() + " Not found" )
//                    .data(null)
//                    .build();
//        }
//        stock.setWarehouse(warehouse.get());
//
//        Optional<Goods> goods = goodsRepository.findById(stockRequest.getGoodsId());
//        if (goods.isEmpty()) {
//            return BaseResponse.builder()
//                    .message("Goods with ID " + stockRequest.getGoodsId() + " Not found")
//                    .data(null)
//                    .build();
//        }
//        stock.setGoods(goods.get());
//
//        stock.setQuantity(stockRequest.getQty());
//        Stock updateStock = stockRepository.save(stock);
//
//        StockResponse data = StockResponse.builder()
//                .id(updateStock.getId())
//                .warehouseId(updateStock.getWarehouse().getId())
//                .goodsId(updateStock.getGoods().getId())
//                .quantity(updateStock.getQuantity())
//                .build();
//
//        return BaseResponse.builder()
//                .message("Success")
//                .data(data)
//                .build();
//    }

//    public BaseResponse deleteStockById(Long id) {
//        Optional<Stock> stockOptional = stockRepository.findById(id);
//        if (stockOptional.isEmpty()) {
//            return BaseResponse.builder()
//                    .message("Stock with ID " + id + " Not found" )
//                    .data(null)
//                    .build();
//        }
//        stockRepository.delete(stockOptional.get());
//        return BaseResponse.builder()
//                .message("Berhasil terhapus")
//                .data(null)
//                .build();
//    }
}
