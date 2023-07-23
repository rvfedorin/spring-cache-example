package rv.fedorin.springcacheexample.spring.service.impl;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import rv.fedorin.springcacheexample.spring.model.User;
import rv.fedorin.springcacheexample.spring.repository.UserRepository;
import rv.fedorin.springcacheexample.spring.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Cacheable(value = "users", key = "#user.name")
    public User createOrReturnCached(User user) {
        log.info("---->>> Creating user: {}", user);

        return userRepository.save(user);
    }

    @Cacheable(value = "names", key = "#user.id")
    public String getUserName(User user) {
        log.info("---->>> Getting name user: {}", user);

        return userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id=" + user.getId() + " not found"))
                .getName();
    }

    @Cacheable(value = "names", key = "#user.id", condition = "#user.id != 1")
    public String getUserNameAndRefreshCacheForFirstId(User user) {
        log.info("---->>> Getting and refresh name user: {}", user);

        return userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id=" + user.getId() + " not found"))
                .getName();
    }

    @CachePut(value = "users", key = "#user.name")
    public User createAndRefreshCache(User user) {
        log.info("---->>> Creating user: {}, and update cache", user);

        return userRepository.save(user);
    }

    @Cacheable("users")
    public User get(Long id) {
        log.info("---->>> Getting user by id: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id=" + id + " not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Caching(evict = {
            @CacheEvict("users"),
            @CacheEvict(value = "names", key = "#id")
    })
    public void deleteAndEvict(Long id) {
        log.info("deleting user by id: {}", id);
        userRepository.deleteById(id);
    }
}
