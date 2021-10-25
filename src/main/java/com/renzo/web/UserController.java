package com.renzo.web;

import com.renzo.config.security.JwtTokenProvider;
import com.renzo.web.request.UserRequest;
import com.renzo.web.response.UserResponse;
import com.renzo.web.service.UserAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/api")
@RestController
public class UserController {
    @Autowired
    private UserAppService userAppService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입
     * @param request
     * @return
     */
    @PostMapping("/user")
    public String signup(UserRequest request){
        userAppService.save(request);
        return "redirect:/login";
    }

    /**
     * 로그인
     * 식별 가능한 모든 정보로 로그인 가능
     * 식별 가능한 모든 정보가 무엇인지 스스로 판단하여 결정
     * 예) (아이디 혹인 전화번호) + 비밀번호 입력 하여 로그인 가능
     * @return
     */
    @GetMapping("/login")
    public String login(String arg, String password,String type){
        UserResponse userResponse = null;
        if(type.equals("email")){
            userResponse =userAppService.emailLogin(arg,password);
        }else if(type.equals("phone")){
            userResponse =userAppService.emailLogin(arg,password);
        }
        return jwtTokenProvider.createToken(userResponse.getUsername(),userResponse.getRoles());
    }

    /**
     * 내정보 보기
     * @return
     */
    @GetMapping("/me")
    public UserResponse getMe(){
        return userAppService.getUserByEmail(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
    }

    /**
     * 비밀번호 찾기 (재설정)기능
     * 로그인 되어있지 않은 상태에서 비밀번호 재설정 하는기능
     * 전화번호 인증 후 비밀번호 재설정이 가능해야함
     * @return
     */
    @GetMapping("/find/password")
    public UserResponse getByPassword(){
        return null;
    }

    @PutMapping("/password")
    public UserResponse updatePassword(){
        return null;
    }

    @GetMapping("/testsave")
    public UserResponse testSave(UserRequest request){
        //FIXME : request 방어코딩 및 벨리데이션 체크 작업필요
        request.setEmail("renzo@gmail.com");
        request.setNickname("renzo");
        request.setPassword("qwer1234!");
        request.setFirstname("순우");
        request.setLastname("리");
        request.setPhonenumber("01033334444");
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        request.setRoles(roles);
        return userAppService.save(request);
    }

}
