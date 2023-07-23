package rv.fedorin.springcacheexample.hibernate.config;

import java.util.Date;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rv.fedorin.springcacheexample.hibernate.model.Employee;
import rv.fedorin.springcacheexample.hibernate.repository.EmployeeRepository;

@Configuration
public class DataConfig {

    @Bean
    public CommandLineRunner createEmployees(EmployeeRepository employeeRepository) {
        return cr -> {
            var employeeNew = new Employee();
            employeeNew.setName("NameOne");
            employeeNew.setOccupation("Developer");
            employeeNew.setSalary(200);
            employeeNew.setJoin(new Date());

            employeeRepository.save(employeeNew);
        };
    }
}
