package com.product.serverproduct.security.services;

import com.product.serverproduct.security.entities.RoleUser;
import com.product.serverproduct.security.entities.UserApp;
import com.product.serverproduct.security.repositories.RoleUserRepository;
import com.product.serverproduct.security.repositories.UserAppRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    private RoleUserRepository roleUserRepository;
    private UserAppRepository userAppRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(RoleUserRepository roleUserRepository,UserAppRepository userAppRepository,PasswordEncoder passwordEncoder){
        this.roleUserRepository=roleUserRepository;
        this.userAppRepository=userAppRepository;
        this.passwordEncoder=passwordEncoder;
    }
    @Override
    public UserApp addNewUser(UserApp userApp) {
        userApp.setUserId(UUID.randomUUID().toString());
        userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));
        return userAppRepository.save(userApp);
    }

    @Override
    public RoleUser addNewRole(RoleUser roleUser) {
        return roleUserRepository.save(roleUser);
    }

    @Override
    public void addRoleToUser(String userName,String roleName) {
      UserApp userApp = userAppRepository.findByUsername(userName);
      RoleUser roleUser = roleUserRepository.findByRoleName(roleName);
      userApp.getRoleUser().add(roleUser);
    }

    @Override
    public void removeRoleToUser(String userName,String roleName) {
        UserApp userApp = userAppRepository.findByUsername(userName);
        RoleUser roleUser = roleUserRepository.findByRoleName(roleName);
        userApp.getRoleUser().remove(roleUser);
    }

    @Override
    public UserApp loadByUserName(String name) {
        return userAppRepository.findByUsername(name);
    }

    @Override
    public List<UserApp> listUsers() {
        return userAppRepository.findAll();
    }

    @Override
    public UserApp login(UserApp userApp) {
        UserApp userAppLogin = userAppRepository.findByUsername(userApp.getUsername());
        if(userAppLogin ==null) return null;
        else if(passwordEncoder.matches(userApp.getPassword(), userAppLogin.getPassword())) return null;
        return userAppLogin;
    }
}
