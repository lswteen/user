package com.renzo.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class UserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String nickname;
    private String email;
    private String phonenumber;
    private String status;
    private Date regdt;
}
