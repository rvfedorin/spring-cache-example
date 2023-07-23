package rv.fedorin.springcacheexample.spring.service;

import java.util.List;
import rv.fedorin.springcacheexample.spring.model.User;

public interface UserService {

    User create(User user);

    User createOrReturnCached(User user);

    User createAndRefreshCache(User user);

    User get(Long id);

    List<User> findAll();

    String getUserName(User user);
    String getUserNameAndRefreshCacheForFirstId(User user);

    void deleteAndEvict(Long id);
}
