package whizware.whizware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whizware.whizware.entity.Location;

import java.util.Optional;


public interface LocationRepository extends JpaRepository<Location, Long> {

}
