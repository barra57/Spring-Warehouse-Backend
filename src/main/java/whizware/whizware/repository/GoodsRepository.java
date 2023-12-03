package whizware.whizware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whizware.whizware.entity.Goods;
@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
