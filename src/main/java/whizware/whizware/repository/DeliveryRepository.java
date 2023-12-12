package whizware.whizware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whizware.whizware.entity.Delivery;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByWarehouseId(Long warehouseId);
}
