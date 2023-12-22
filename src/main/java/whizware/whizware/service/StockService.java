package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.stock.StockResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.entity.Stock;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.exception.ConflictException;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public BaseResponse getAllStock() {
        List<Stock> stock = stockRepository.findAll();
        if (stock.isEmpty())
            throw new NoContentException("Stock is empty");
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
                .message("Success")
                .data(data)
                .build();
    }

    public BaseResponse getStockById(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Stock with ID %d Not found", id)));
        StockResponse data = StockResponse.builder()
                .id(stock.getId())
                .warehouseId(stock.getWarehouse().getId())
                .goodsId(stock.getGoods().getId())
                .quantity(stock.getQuantity())
                .build();

        return BaseResponse.builder()
                .message("Success")
                .data(data)
                .build();
    }

    public void subtractStock(Warehouse warehouse, Goods goods, Long quantity) {
        List<Stock> stockList = stockRepository.findByWarehouseIdAndGoodsId(warehouse.getId(), goods.getId());
        if (stockList.isEmpty() || stockList.get(0).getQuantity() < quantity)
            throw new ConflictException(String.format("Warehouse %s doesn't have enough %s", warehouse.getName(), goods.getName()));
        Stock stock = stockList.get(0);
        stock.setWarehouse(warehouse);
        stock.setGoods(goods);
        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
    }

    public void addStock(Warehouse warehouse, Goods goods, Long quantity) {
        List<Stock> stockList = stockRepository.findByWarehouseIdAndGoodsId(warehouse.getId(), goods.getId());
        Stock stock = stockList.isEmpty() ? new Stock() : stockList.get(0);
        stock.setWarehouse(warehouse);
        stock.setGoods(goods);
        stock.setQuantity(Optional.ofNullable(stock.getQuantity()).orElse(0L) + quantity);
        stockRepository.save(stock);
    }

    public BaseResponse getStockByWarehouseId(Long id) {
        List<Stock> stock = stockRepository.findByWarehouseId(id);
        if (stock.isEmpty())
            throw new NoContentException("Stock is empty");
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
                .message("Success")
                .data(data)
                .build();
    }
}