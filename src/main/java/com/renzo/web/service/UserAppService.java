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

    public UserResponse getUserById(Long id){
        User user = userService.getById(id);
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .tel(user.getTel())
                .status(user.getStatus())
                .regdt(user.getRegdt())
                .build();
    }

    public UserResponse save(UserRequest request){
        //FIXME : request 전달받아 처리필요함.
        User result = userService.save(User.builder()
            .username("A001")
            .email("a@gmail.com")
            .password("abcde12345!")
            .tel("010-3856-4215")
            .status("001")
            .build());

        return UserResponse.builder()
             .id(result.getId())
             .username(result.getUsername())
             .email(result.getEmail())
             .tel(result.getTel())
             .status(result.getStatus())
             .regdt(result.getRegdt())
             .build();

    }
}
