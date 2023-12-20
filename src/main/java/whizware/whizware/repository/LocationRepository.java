package whizware.whizware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whizware.whizware.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
