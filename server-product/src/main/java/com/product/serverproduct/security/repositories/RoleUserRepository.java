package com.product.serverproduct.security.repositories;

import com.product.serverproduct.security.entities.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser,Long> {
RoleUser findByRoleName(String roleName);
}
