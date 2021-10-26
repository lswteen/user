package com.renzo.web.service;

import com.renzo.core.exception.ApiException;
import com.renzo.core.type.ServiceErrorType;
import com.renzo.domain.user.entity.User;
import com.renzo.domain.user.service.UserService;
import com.renzo.web.request.UserRequest;
import com.renzo.web.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserAppService {
    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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
        User user = userService.getByEmail(email);
        if(bCryptPasswordEncoder.matches(password,user.getPassword())){
            return userResponse(user);
        }else{
            throw new ApiException(ServiceErrorType.INVALID_PARAMETER);
        }
    }

    public UserResponse phoneLogin(String phonenumber, String password){
        User user = userService.getByPhonenumber(phonenumber);
        if(bCryptPasswordEncoder.matches(password,user.getPassword())){
            return userResponse(user);
        }else{
            throw new ApiException(ServiceErrorType.INVALID_PARAMETER);
        }
    }

    public UserResponse nicknameLogin(String nickname, String password){
        User user = userService.getByNickname(nickname);
        if(bCryptPasswordEncoder.matches(password,user.getPassword())){
            return userResponse(user);
        }else{
            throw new ApiException(ServiceErrorType.INVALID_PARAMETER);
        }
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
        userService.phoneNumberValidationCheck(request.getPhonenumber());
        userService.emailValidationCheck(request.getEmail());
        userService.nicknameValidatoinCheck(request.getNickname());
        return userResponse(userService.save(request.toEntity()));
    }
}
