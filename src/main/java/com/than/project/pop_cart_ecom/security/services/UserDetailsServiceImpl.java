package com.than.project.pop_cart_ecom.security.services;

import com.than.project.pop_cart_ecom.model.MyUser;
import com.than.project.pop_cart_ecom.repository.MyUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MyUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MyUser user = userRepository.findByUsername(username)
                .orElseThrow( () ->
                        new UsernameNotFoundException("Uset Not found with username: " + username)
                );

        return UserDetailsImpl.build(user);
    }
}
