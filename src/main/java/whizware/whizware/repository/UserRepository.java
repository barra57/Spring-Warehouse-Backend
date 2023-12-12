package whizware.whizware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import whizware.whizware.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
