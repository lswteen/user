package com.renzo.web.request;

import com.renzo.domain.user.entity.User;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

@Getter
@Setter
public class UserRequest {

    private String password;

    private String nickname;

    private String firstname;

    private String lastname;

    private String email;

    private String phonenumber;

    private List<String> roles;

    public User toEntity(){
        return User.builder()
                .password(new BCryptPasswordEncoder().encode(password))
                .nickname(nickname)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .phonenumber(phonenumber)
                .roles(roles)
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SmsCertificationRequest {

        private String phone;
        private String certificationNumber;

        @Builder
        public SmsCertificationRequest(String phone, String certificationNumber) {
            this.phone = phone;
            this.certificationNumber = certificationNumber;
        }

    }
}
