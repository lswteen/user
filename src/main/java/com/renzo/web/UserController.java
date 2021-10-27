package com.renzo.web;

import com.renzo.config.security.JwtTokenProvider;
import com.renzo.web.request.UserRequest;
import com.renzo.web.response.UserResponse;
import com.renzo.web.service.SmsCertificationService;
import com.renzo.web.service.UserAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


import static com.renzo.web.response.ResponseConstants.CREATED;
import static com.renzo.web.response.ResponseConstants.OK;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserAppService userAppService;
    private final JwtTokenProvider jwtTokenProvider;
    private final SmsCertificationService smsCertificationService;

    /**
     * 회원 가입
     * @param request
     * @return
     */
    @PostMapping("/user/singup")
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
    @PostMapping("/user/login")
    public String login(@RequestBody UserRequest.LoginRequest loginRequest){
        UserResponse userResponse = null;
        if(loginRequest.getType().equals("email")){
            userResponse = userAppService.emailLogin(loginRequest.getArgument(), loginRequest.getPassword());
        }else if(loginRequest.getType().equals("phone")){
            userResponse = userAppService.phoneLogin(loginRequest.getArgument(), loginRequest.getPassword());
        }else if(loginRequest.getType().equals("nickname")){
            userResponse = userAppService.nicknameLogin(loginRequest.getArgument(), loginRequest.getPassword());
        }
        return jwtTokenProvider.createToken(userResponse.getUsername(),userResponse.getRoles());
    }

    /**
     * 내정보 보기
     * @return
     */
    @PostMapping("/user/me")
    public UserResponse getMe(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userAppService.getUserByEmail(authentication.getName());
    }

    /**
     * Finding a password reset.
     * 비밀번호 찾기 (재설정)기능
     * 로그인 되어있지 않은 상태에서 비밀번호 재설정 하는기능
     * 전화번호 인증 후 비밀번호 재설정이 가능해야함
     * @return
     */
    @PutMapping("/user/password")
    public UserResponse findByPasswordAndReset(String phonenumber, String newPassword){
        userAppService.findByPasswordAndReset(phonenumber,newPassword);
        return null;
    }

    /**
     * 인증번호 발송
     * @param requestDto
     * @return
     */
    @PostMapping("/sms/certification/sends")
    public ResponseEntity<Void> sendSms(@RequestBody UserRequest.SmsCertificationRequest requestDto) {
        smsCertificationService.sendSms(requestDto.getPhone());
        return CREATED;
    }

    /**
     * 인증번호 확인
     * @param requestDto
     * @return
     */
    @PostMapping("/sms/certification/confirms")
    public ResponseEntity<Void> SmsVerification(@RequestBody UserRequest.SmsCertificationRequest requestDto) {
        smsCertificationService.verifySms(requestDto);
        return OK;
    }

    //----------------------------- 절대 ... 이렇게 안하는데 H2 가 아직 익숙하질 않다보니 죄송합니다... ㅜ.ㅜ -----//

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

    @GetMapping("/user")
    public UserResponse getEmail(String email){
        return userAppService.getUserByEmail(email);
    }
    //----------------------------- 보통 이렇게 안하는데 H2 가 아직 익숙하질 않다보니 죄송합니다... ㅜ.ㅜ-----//
}
