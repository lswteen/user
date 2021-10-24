package com.renzo.web.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserRequest {
    private String password;
    private String nickname;
    private String username;
    private String email;
    private String tel;
    private String status;
    private Date regdt;
}
