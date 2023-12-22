package whizware.whizware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whizware.whizware.entity.Stock;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByWarehouseIdAndGoodsId(Long warehouseId, Long goodsId);

    List<Stock> findByWarehouseId(Long id);
}
