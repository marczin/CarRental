package com.marcinrosol.carrental.repositories;

import com.marcinrosol.carrental.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String name);

    Optional<User> findByUsernameOrEmail(String username, String username1);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
