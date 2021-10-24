package com.renzo.web;

import com.renzo.web.request.UserRequest;
import com.renzo.web.response.UserResponse;
import com.renzo.web.service.UserAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserAppService userAppService;

    @GetMapping("/users")
    public UserResponse getById(@RequestParam long id){
        return userAppService.getUserById(id);
    }
    @GetMapping("/save")
    public UserResponse testSave(UserRequest request){
        //FIXME : request 방어코딩 및 벨리데이션 체크 작업필요
        return userAppService.save(request);
    }

    @PostMapping("/users")
    public UserResponse save(UserRequest request){
        //FIXME : request 방어코딩 및 벨리데이션 체크 작업필요
        return userAppService.save(request);
    }
}
