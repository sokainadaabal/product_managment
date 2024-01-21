package com.product.serverproduct.security.repositories;

import com.product.serverproduct.security.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp,String> {
    UserApp findByUsername(String userName);
}
