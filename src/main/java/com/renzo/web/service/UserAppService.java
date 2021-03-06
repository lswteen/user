package com.renzo.web.service;

import com.renzo.core.exception.ApiException;
import com.renzo.core.type.ServiceErrorType;
import com.renzo.domain.user.entity.User;
import com.renzo.domain.user.service.UserService;
import com.renzo.web.request.UserRequest;
import com.renzo.web.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAppService {

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Transactional
    public UserResponse emailLogin(String email, String password){
        User user = userService.getByEmail(email);
        if(bCryptPasswordEncoder.matches(password,user.getPassword())){
            return userResponse(user);
        }else{
            throw new ApiException(ServiceErrorType.INVALID_PARAMETER);
        }
    }

    @Transactional
    public UserResponse phoneLogin(String phonenumber, String password){
        User user = userService.getByPhonenumber(phonenumber);
        if(bCryptPasswordEncoder.matches(password,user.getPassword())){
            return userResponse(user);
        }else{
            throw new ApiException(ServiceErrorType.INVALID_PARAMETER);
        }
    }

    @Transactional
    public UserResponse nicknameLogin(String nickname, String password){
        User user = userService.getByNickname(nickname);
        if(bCryptPasswordEncoder.matches(password,user.getPassword())){
            return userResponse(user);
        }else{
            throw new ApiException(ServiceErrorType.INVALID_PARAMETER);
        }
    }

    @Transactional
    public UserResponse getUserByEmail(String email){
        return userResponse(userService.getByEmail(email));
    }

    @Transactional
    public UserResponse findByPasswordAndReset(String phonenumber, String newPassword){
        User user = userService.getByPhonenumber(phonenumber);
        if(bCryptPasswordEncoder.matches(newPassword,user.getPassword())){  //???????????? ???????????? ??????????????? ??????
            throw new ApiException(ServiceErrorType.OLDPASSWORD);
        }else{
            return userResponse(userService.save(User.builder()
                    .id(user.getId())
                    .password(bCryptPasswordEncoder.encode(newPassword))
                    .build()));
        }
    }

    @Transactional
    public UserResponse save(UserRequest request){
        userService.phoneNumberValidationCheck(request.getPhonenumber());
        userService.emailValidationCheck(request.getEmail());
        userService.nicknameValidatoinCheck(request.getNickname());
        return userResponse(userService.save(request.toEntity()));
    }
}
