package com.renzo.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class UserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String nickname;
    private String email;
    private String phonenumber;
    private List<String> roles;
    private Date regdt;
}
