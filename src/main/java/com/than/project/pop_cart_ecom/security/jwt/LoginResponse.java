package com.than.project.pop_cart_ecom.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String jwtTokem;
    private String username;
    private List<String> roles;

}
