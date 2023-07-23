package rv.fedorin.springcacheexample.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rv.fedorin.springcacheexample.spring.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
