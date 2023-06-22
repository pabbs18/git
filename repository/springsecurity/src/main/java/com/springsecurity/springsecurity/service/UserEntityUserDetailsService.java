package com.springsecurity.springsecurity.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsecurity.springsecurity.entity.UserEntity;
import com.springsecurity.springsecurity.entity.UserEntityUserDetails;
import com.springsecurity.springsecurity.repository.UserRepository;



@Service
public class UserEntityUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //get the user info from repo
        Optional<UserEntity> optUserEntity = userRepository.findByUsername(username);

        //since this method can only return UserDetails object, convert userEntity to UserDetails
        UserEntityUserDetails userEntityUserDetails = optUserEntity.map(UserEntityUserDetails :: new)
                     .orElseThrow(() -> new UsernameNotFoundException("user not found: " +username));
        
          return userEntityUserDetails;           
       // throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

}
