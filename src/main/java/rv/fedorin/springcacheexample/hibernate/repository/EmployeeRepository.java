package rv.fedorin.springcacheexample.hibernate.repository;

import java.util.List;
import javax.persistence.QueryHint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import rv.fedorin.springcacheexample.hibernate.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FROM Employee e WHERE e.id != ?1")
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "promotionServiceXrefByCustomerId")
    List<Employee> findAllInsteadId(Integer excludeId);
}
