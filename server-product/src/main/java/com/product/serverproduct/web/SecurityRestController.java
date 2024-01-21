package com.product.serverproduct.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.serverproduct.security.config.JwtConfig;
import com.product.serverproduct.security.entities.RoleUser;
import com.product.serverproduct.security.entities.UserApp;
import com.product.serverproduct.security.services.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWTVerifier;
@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/V1")
@SecurityRequirement(name = "productApi")
public class SecurityRestController
{
    private AccountService securityService;

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String jwt_token = request.getHeader(JwtConfig.AUTHORIZATION_HEADER);//Authorization: Bearer xxx

        if (jwt_token != null && jwt_token.startsWith(JwtConfig.TOKEN_HEADER_PREFIX)) {//Authorization: Bearer xxx

            try {
                String jwt = jwt_token.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(JwtConfig.SECRET_PHRASE);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(jwt); // verify the token

                String username = decodedJWT.getSubject(); // get the username
                UserApp userApp = securityService.loadByUserName(username); // get the user using the username

                String jwtAccessToken = JWT.create() // create a new JWT with the username
                        .withSubject(userApp.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtConfig.ACCESS_TOKEN_EXPIRATION)) // 24hrs
                        .withIssuer(request.getRequestURL().toString()) // url of the issuer
                        .withClaim("roles", userApp.getRoleUser().stream().map(RoleUser::getRoleName).collect(Collectors.toList())) // roles
                        .sign(algorithm);// secret

                Map<String, String> tokens = new HashMap<>();// create a map with the tokens
                tokens.put("access_token", jwtAccessToken);// access token
                tokens.put("refresh_token", jwt);// refresh token
                response.setContentType("application/json"); // set the content type
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            } catch (Exception e) {
                throw e;
            }
        } else
            throw new RuntimeException(" You should refresh your token ");
    }

    @PreAuthorize("hasAuthority('USER')") // hasAuthority('USER')
    @GetMapping("/profile")
    public UserApp getUser(Principal principal) { // Principal is the user
        return securityService.loadByUserName(principal.getName());
    }
}
