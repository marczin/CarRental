package com.marcinrosol.carrental.repositories;

import com.marcinrosol.carrental.models.Enums.RoleName;
import com.marcinrosol.carrental.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleUser);
}
