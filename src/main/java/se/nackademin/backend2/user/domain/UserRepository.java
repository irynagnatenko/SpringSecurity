package se.nackademin.backend2.user.domain;

import java.util.Optional;

public interface UserRepository {
    void save(final User user);

    Optional<User> findById(final String userName);
}
