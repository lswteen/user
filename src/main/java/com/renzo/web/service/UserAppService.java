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

    public UserResponse emailLogin(String email, String password){
        User user =userService.getByEmailAndPassword(email,password);
        return UserResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phonenumber(user.getPhonenumber())
                .regdt(user.getRegdt())
                .build();
    }

    public UserResponse getUserById(Long id){
        User user = userService.getById(id);
        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phonenumber(user.getPhonenumber())
                .regdt(user.getRegdt())
                .build();
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

        return UserResponse.builder()
             .id(result.getId())
             .firstname(result.getFirstname())
             .lastname(result.getLastname())
             .email(result.getEmail())
             .phonenumber(result.getPhonenumber())
             .regdt(result.getRegdt())
             .build();

    }
}
