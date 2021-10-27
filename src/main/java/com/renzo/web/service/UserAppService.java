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

    private BCryptPasswordEncoder newInstancebCryptPasswordEncoder(){
       return new BCryptPasswordEncoder();
    }

    public UserResponse emailLogin(String email, String password){
        User user = userService.getByEmail(email);
        if(newInstancebCryptPasswordEncoder().matches(password,user.getPassword())){
            return userResponse(user);
        }else{
            throw new ApiException(ServiceErrorType.INVALID_PARAMETER);
        }
    }

    public UserResponse phoneLogin(String phonenumber, String password){
        User user = userService.getByPhonenumber(phonenumber);
        if(newInstancebCryptPasswordEncoder().matches(password,user.getPassword())){
            return userResponse(user);
        }else{
            throw new ApiException(ServiceErrorType.INVALID_PARAMETER);
        }
    }

    public UserResponse nicknameLogin(String nickname, String password){
        User user = userService.getByNickname(nickname);
        if(newInstancebCryptPasswordEncoder().matches(password,user.getPassword())){
            return userResponse(user);
        }else{
            throw new ApiException(ServiceErrorType.INVALID_PARAMETER);
        }
    }

    public UserResponse getUserByEmail(String email){
        return userResponse(userService.getByEmail(email));
    }

    public UserResponse findByPasswordAndReset(String phonenumber, String newPassword){
        User user = userService.getByPhonenumber(phonenumber);
        if(newInstancebCryptPasswordEncoder().matches(newPassword,user.getPassword())){  //중복이면 다른걸로 넣어달라는 리턴
            throw new ApiException(ServiceErrorType.OLDPASSWORD);
        }else{
            return userResponse(userService.save(User.builder()
                    .id(user.getId())
                    .password(new BCryptPasswordEncoder().encode(newPassword))
                    .build()));
        }
    }

    public UserResponse save(UserRequest request){
        userService.phoneNumberValidationCheck(request.getPhonenumber());
        userService.emailValidationCheck(request.getEmail());
        userService.nicknameValidatoinCheck(request.getNickname());
        return userResponse(userService.save(request.toEntity()));
    }
}
