package rv.fedorin.springcacheexample.hibernate.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rv.fedorin.springcacheexample.hibernate.model.Employee;
import rv.fedorin.springcacheexample.hibernate.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee findEmployee(Employee employee) {
        return findEmployeeById(employee.getId());
    }

    public Employee findEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> findAllExcludeId(Integer excludeId) {
        return employeeRepository.findAllInsteadId(excludeId);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
