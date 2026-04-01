package com.than.project.pop_cart_ecom.util;

import com.than.project.pop_cart_ecom.model.MyUser;
import com.than.project.pop_cart_ecom.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final MyUserRepository userRepository;

    public String loggedInEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));

        return user.getEmail();
    }

    public long loggedInUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));

        return user.getUserId();
    }

    public MyUser loggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));
    }

}
