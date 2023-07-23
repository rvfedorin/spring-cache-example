package rv.fedorin.springcacheexample.spring.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import rv.fedorin.springcacheexample.spring.model.User;
import rv.fedorin.springcacheexample.spring.service.UserService;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.show_sql=false",
        "spring.jpa.properties.hibernate.cache.use_second_level_cache=false"})
public class UserServiceImplTest {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    @Autowired
    private UserService service;

    @Test
    public void get() {
        User user1 = service.create(new User("User1", "user1@mail.ru"));
        User user2 = service.create(new User("User2", "user2@mail.ru"));

        logLine();
        logInColor(service.get(user1.getId()), ANSI_RED);
        logInColor(service.get(user2.getId()), ANSI_RED);
        logLine();
        logInColor(service.get(user1.getId()), ANSI_RED);
        logInColor(service.get(user2.getId()), ANSI_RED);
        logLine();
    }

    @Test
    public void createAndRefresh() {
        logLine();
        User user1 = service.createOrReturnCached(new User("user1", "user1one@mail.ru"));
        logInColor("created user1: " + user1, ANSI_RED);
        User user1another = service.createOrReturnCached(new User("user1", "user1two@mail.ru"));
        logInColor("created user1 another: " + user1another, ANSI_RED);

        logLine();
        logAllUsersInDb();
        logLine();

        User user2 = service.createAndRefreshCache(new User("user2", "user2one@mail.ru"));
        logInColor("created user2: " + user2, ANSI_RED);
        User user2another = service.createAndRefreshCache(new User("user2", "user2two@mail.ru"));
        logInColor("created user2 another: " + user2another, ANSI_RED);
        logLine();
        logAllUsersInDb();
        logLine();
    }

    @Test
    public void delete() {
        User user1 = service.create(new User("Vasya", "vasya@mail.ru"));
        log.info("{}", service.get(user1.getId()));

        User user2 = service.create(new User("Vasya", "vasya@mail.ru"));
        log.info("{}", service.get(user2.getId()));

        logLine();
        log.info("{}", service.getUserName(user2));
        log.info("{}", service.getUserName(user2));
        logLine();

        service.deleteAndEvict(user2.getId());

        log.info("{}", service.get(user1.getId()));

        assertThatThrownBy(() -> service.get(user2.getId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User with id=2 not found");

        assertThatThrownBy(() -> service.getUserName(user2))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User with id=2 not found");
    }

    @Test
    public void getUserNameAndRefreshCacheForFirstId() {
        User user1 = service.create(new User("User1", "user1@mail.ru"));
        User user2 = service.create(new User("User2", "user2@mail.ru"));

        logLine();
        log.info(" :::::: {}", service.getUserNameAndRefreshCacheForFirstId(user1));
        log.info(" :::::: {}", service.getUserNameAndRefreshCacheForFirstId(user2));
        logLine();
        log.info(" :::::: {}", service.getUserNameAndRefreshCacheForFirstId(user1));
        log.info(" :::::: {}", service.getUserNameAndRefreshCacheForFirstId(user2));
        logLine();
    }

    private <T> void logInColor(T body, String color) {
        log.info(" :::::: {}{}{}", color, body, ANSI_RESET);
    }

    private void logAllUsersInDb() {
        log.info(" :::::: in DB: {}", service.findAll().stream()
                .map(u -> "\nid:" + u.getId() + ", name:" + u.getName())
                .reduce((s1, s2) -> s1 + s2)
                .get());
    }

    private void logLine() {
        log.info("{}", "=".repeat(60));
    }
}