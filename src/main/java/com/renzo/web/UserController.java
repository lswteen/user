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

import io.swagger.annotations.*;
import static com.renzo.web.response.ResponseConstants.CREATED;
import static com.renzo.web.response.ResponseConstants.OK;

@Slf4j
@RequestMapping("/api")
@RestController
@Api(tags = "회원")
@RequiredArgsConstructor
public class UserController {
    private final UserAppService userAppService;
    private final JwtTokenProvider jwtTokenProvider;
    private final SmsCertificationService smsCertificationService;

    @ApiOperation(value = "회원가입", notes = "회원가입", tags = "회원")
    @ApiResponses({
        @ApiResponse(code=200, message="회원가입 성공시 login 페이지 이동합니다."),
        @ApiResponse(code=409, message="닉네임,이메일,휴대폰 번호는 유일한 식별자입니다.")
    })
    @ApiParam(value = "email", required = true,example = "a@gmail.com")
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
    @ApiOperation(value = "로그인", notes = "로그인", tags = "회원")
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

    @ApiOperation(value = "회원정보보기", notes = "회원정보보기", tags = "회원")
    @PostMapping("/user/me")
    public UserResponse getMe(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userAppService.getUserByEmail(authentication.getName());
    }

    /**
     * 로그인 되어있지 않은 상태에서 비밀번호 재설정 하는기능
     * 전화번호 인증 후 비밀번호 재설정이 가능해야함
     * @return
     */
    @ApiOperation(value = "비밀번호 찾기 (재설정)기능", notes = "비밀번호 찾기 (재설정)기능", tags = "회원")
    @PutMapping("/user/password")
    public ResponseEntity<Void> findByPasswordAndReset(String phonenumber, String newPassword){
        userAppService.findByPasswordAndReset(phonenumber,newPassword);
        return OK;
    }

    /**
     * 인증번호 발송
     * @param requestDto
     * @return
     */
    @PostMapping("/sms/certification/sends")
    @ApiOperation(value = "SMS 인증번호 발송", notes = "SMS 인증번호 발송", tags = "회원")
    public ResponseEntity<Void> sendSms(@RequestBody UserRequest.SmsCertificationRequest requestDto) {
        smsCertificationService.sendSms(requestDto.getPhone());
        return CREATED;
    }

    /**
     * 인증번호 확인
     * @param requestDto
     * @return
     */
    @ApiOperation(value = "SMS 인증번호 확인", notes = "SMS 인증번호 확인", tags = "회원")
    @PostMapping("/sms/certification/confirms")
    public ResponseEntity<Void> SmsVerification(@RequestBody UserRequest.SmsCertificationRequest requestDto) {
        smsCertificationService.verifySms(requestDto);
        return OK;
    }

}
