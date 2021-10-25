package com.renzo.web.service;

import com.renzo.domain.user.entity.User;
import com.renzo.domain.user.service.UserService;
import com.renzo.web.request.UserRequest;
import com.renzo.web.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAppService {
    @Autowired
    UserService userService;

    private UserResponse userResponse(User user) {
        return UserResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phonenumber(user.getPhonenumber())
                .roles(user.getRoles())
                .regdt(user.getRegdt())
                .build();
    }

    public UserResponse emailLogin(String email, String password){
        User user =userService.getByEmailAndPassword(email,password);
        return userResponse(user);
    }

    public UserResponse getUserByEmail(String email){
        User user = userService.getByEmail(email);
        return userResponse(user);
    }

    public UserResponse getUserById(Long id){
        User user = userService.getById(id);
        return userResponse(user);
    }

    public UserResponse save(UserRequest request){
        User result = userService.save(User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .nickname(request.getNickname())
            .email(request.getEmail())
            .password(request.getPassword())
            .phonenumber(request.getPhonenumber())
            .build());
        return userResponse(result);
    }



}
