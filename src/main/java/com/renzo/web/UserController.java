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

    /**
     * 회원 가입
     * @param request
     * @return
     */
    @PostMapping("/users")
    public UserResponse save(UserRequest request){
        //FIXME : request 방어코딩 및 벨리데이션 체크 작업필요
        return userAppService.save(request);
    }

    /**
     * 로그인
     * 식별 가능한 모든 정보로 로그인 가능
     * 식별 가능한 모든 정보가 무엇인지 스스로 판단하여 결정
     * 예) (아이디 혹인 전화번호) + 비밀번호 입력 하여 로그인 가능
     * @return
     */
    public UserResponse login(){
        return null;
    }

    /**
     * 내정보 보기
     * @param id
     * @return
     */
    @GetMapping("/users")
    public UserResponse getById(@RequestParam long id){
        return userAppService.getUserById(id);
    }

    /**
     * 비밀번호 찾기 (재설정)기능
     * 로그인 되어있지 않은 상태에서 비밀번호 재설정 하는기능
     * 전화번호 인증 후 비밀번호 재설정이 가능해야함
     * @return
     */
    public UserResponse getByPassword(){
        return null;
    }


    @GetMapping("/save")
    public UserResponse testSave(UserRequest request){
        //FIXME : request 방어코딩 및 벨리데이션 체크 작업필요
        return userAppService.save(request);
    }

}
