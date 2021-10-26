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
        return userResponse(userService.getByEmailAndPassword(email,password));
    }

    public UserResponse phoneLogin(String phone, String password){
        return userResponse(userService.getByPhonenumberAndPassword(phone, password));
    }

    public UserResponse nicknameLogin(String nickname, String password){
        return userResponse(userService.getByNicknameAndPassword(nickname, password));
    }

    public UserResponse getUserByEmail(String email){
        return userResponse(userService.getByEmail(email));
    }

    public UserResponse changePassword(String phonenumber, String password){
        //패스워드 동일한지 체크 귀찮다.
        //패스워드가 다르면 변경 후 유저정보 리턴

        return null;
    }

    public UserResponse save(UserRequest request){
        return userResponse(userService.save(User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(request.getPassword())
                .roles(request.getRoles())
                .phonenumber(request.getPhonenumber())
                .build()));
    }



}
