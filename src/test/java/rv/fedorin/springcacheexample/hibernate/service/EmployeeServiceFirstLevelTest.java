package rv.fedorin.springcacheexample.hibernate.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.cache.use_second_level_cache=false"
})
class EmployeeServiceFirstLevelTest {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    @Autowired
    private EmployeeService employeeService;

    @Test
    @Transactional
    public void findEmployee_firstSelect() {

        var empOne = employeeService.findEmployeeById(1);
        var empTwo = employeeService.findEmployeeById(1);

        log.info("{}Employee one:{} {}", ANSI_RED, ANSI_RESET, empOne);
        log.info("{}Employee two:{} {}", ANSI_RED, ANSI_RESET, empTwo);
    }

    @Test
    @Transactional
    public void findEmployee_secondSelect() {

        var empOne = employeeService.findEmployeeById(1);
        var empTwo = employeeService.findEmployeeById(1);

        log.info("{}Employee one:{} {}", ANSI_RED, ANSI_RESET, empOne);
        log.info("{}Employee two:{} {}", ANSI_RED, ANSI_RESET, empTwo);
    }
}