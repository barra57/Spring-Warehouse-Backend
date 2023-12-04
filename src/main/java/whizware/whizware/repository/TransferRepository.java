package whizware.whizware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whizware.whizware.entity.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
