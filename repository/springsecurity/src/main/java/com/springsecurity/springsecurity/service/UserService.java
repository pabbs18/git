package com.springsecurity.springsecurity.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.springsecurity.dto.LoginObject;
import com.springsecurity.springsecurity.dto.UserDto;
import com.springsecurity.springsecurity.entity.UserEntity;
import com.springsecurity.springsecurity.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String createUser(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setEmail(userDto.getEmail());
        userEntity.setRoles(userDto.getRoles());

        userRepository.save(userEntity);

        return "user added";
    }

    public String authenticateUser(LoginObject loginObject){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginObject.getUsername(), loginObject.getPassword()));
        if(authenticate.isAuthenticated()){
          return  jwtService.generateJwtToken(loginObject.getUsername());
        }
        else throw new UsernameNotFoundException("Username or password is incorrect");
    }
}
