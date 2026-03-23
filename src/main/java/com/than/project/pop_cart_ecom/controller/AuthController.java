package com.than.project.pop_cart_ecom.controller;

import com.than.project.pop_cart_ecom.security.jwt.JwtUtils;
import com.than.project.pop_cart_ecom.security.request.LoginRequest;
import com.than.project.pop_cart_ecom.security.response.UserInfoResponse;
import com.than.project.pop_cart_ecom.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private  JwtUtils jwtUtils;

    @GetMapping("/hello")
    public  String hello(){
        return "Hello";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/helloUser")
    public  String helloUser(){
        return "Hello User";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/helloAdmin")
    public  String helloAdmin(){
        return "Hello Admin";
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticationUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        }catch (AuthenticationException e){
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad Credentials");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        UserInfoResponse response = new UserInfoResponse(userDetails.getId() , jwtToken, userDetails.getUsername(), roles);

        return  new ResponseEntity<>(response, HttpStatus.OK);




    }
}
