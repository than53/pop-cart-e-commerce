package com.than.project.pop_cart_ecom.controller;

import com.than.project.pop_cart_ecom.model.AppRole;
import com.than.project.pop_cart_ecom.model.MyUser;
import com.than.project.pop_cart_ecom.model.Role;
import com.than.project.pop_cart_ecom.repository.MyUserRepository;
import com.than.project.pop_cart_ecom.repository.RoleRepository;
import com.than.project.pop_cart_ecom.security.jwt.JwtUtils;
import com.than.project.pop_cart_ecom.security.request.LoginRequest;
import com.than.project.pop_cart_ecom.security.request.SignupRequest;
import com.than.project.pop_cart_ecom.security.response.MessageResponse;
import com.than.project.pop_cart_ecom.security.response.UserInfoResponse;
import com.than.project.pop_cart_ecom.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private MyUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private  JwtUtils jwtUtils;


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

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){

        if(userRepository.existsByUsername(signupRequest.getUsername())){
            return  ResponseEntity.badRequest().body(new MessageResponse("Error: Username already exist!!"));
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return  ResponseEntity.badRequest().body(new MessageResponse("Error: email already exist!!"));
        }

        MyUser user = new MyUser(
                signupRequest.getUsername(), signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword())
        );

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null){
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(()-> new RuntimeException("Error: Role is not Found"));
        }else{
            strRoles.forEach(role ->{
                switch (role){
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(()-> new RuntimeException("Error: Role is not Found"));
                        roles.add(adminRole);
                        break;
                    case "seller":
                        Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                                .orElseThrow(()-> new RuntimeException("Error: Role is not Found"));
                        roles.add(sellerRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                                .orElseThrow(()-> new RuntimeException("Error: Role is not Found"));
                        roles.add(userRole);

                }
            });
        }
        user.setRoles(roles);

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public String currentUsername(Authentication authentication){
        if(authentication !=null){
            return authentication.getName();
        }else{
            return "";
        }
    }
}
