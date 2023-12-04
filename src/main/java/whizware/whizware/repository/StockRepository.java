package whizware.whizware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whizware.whizware.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
