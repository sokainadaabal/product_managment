package com.product.serverproduct.security.services;

import com.product.serverproduct.security.entities.RoleUser;
import com.product.serverproduct.security.entities.UserApp;

import java.util.List;

public interface AccountService  {
    // all function used to manage a user
    UserApp addNewUser(UserApp userApp);
    RoleUser addNewRole(RoleUser roleUser);
    void addRoleToUser(String userName,String roleName);
    void removeRoleToUser(String userName,String roleName);
    UserApp loadByUserName(String name);
    List<UserApp> listUsers();
    // function for to login
    UserApp login(UserApp userApp);

}
