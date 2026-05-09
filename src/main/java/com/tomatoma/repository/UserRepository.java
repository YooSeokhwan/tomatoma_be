package com.tomatoma.repository;

import com.tomatoma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String userId);

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
