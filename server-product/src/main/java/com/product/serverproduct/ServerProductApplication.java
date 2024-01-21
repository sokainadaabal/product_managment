package com.product.serverproduct;


import com.product.serverproduct.dtos.ProductsDTO;
import com.product.serverproduct.security.entities.RoleUser;
import com.product.serverproduct.security.entities.UserApp;
import com.product.serverproduct.security.services.AccountService;
import com.product.serverproduct.services.ProductServiceImpl;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.stream.Stream;

@SpringBootApplication
@SecurityScheme(name = "productApi", description = "Product management API", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class ServerProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerProductApplication.class, args);

    }
    @Bean
    CommandLineRunner usrs_(AccountService securityService, ProductServiceImpl productService) {
        return args -> {
            /*
            ** add role et user ***/
            securityService.addNewRole(new RoleUser(null, "USER"));
            securityService.addNewRole(new RoleUser(null, "ADMIN"));
            securityService.addNewUser(new UserApp(null, "sokaina", "1994", new ArrayList<>()));
            securityService.addNewUser(new UserApp(null, "ahmed", "2027", new ArrayList<>()));
            /*
            ** associate a role to user ***/
            securityService.addRoleToUser("ahmed", "USER");
            securityService.addRoleToUser("sokaina", "USER");
            securityService.addRoleToUser("sokaina", "ADMIN");

            /*
             ** add product ***/
            Stream.of("PC","Tablet","Phone").forEach(name->{
                ProductsDTO productsDTO=new ProductsDTO();
                productsDTO.setName(name);
                productsDTO.setDescription("Article est"+name);
                productsDTO.setPrix(Math.random()*200);

                productService.saveProduct(productsDTO);
            });
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
