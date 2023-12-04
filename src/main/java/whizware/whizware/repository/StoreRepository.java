package whizware.whizware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whizware.whizware.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
