package se.nackademin.backend2.user.presistance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nackademin.backend2.user.domain.User;
import se.nackademin.backend2.user.domain.UserRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<String, User> db = new ConcurrentHashMap<>();

    @Override
    public void save(final User user) {
        db.put(user.getUsername(), user);
    }

    @Override
    public Optional<User> findById(final String userId) {
        LOG.info("Trying to find user {}", userId);
        return Optional.ofNullable(db.get(userId));
    }
}
