package com.renzo.core.type;

import org.springframework.http.HttpStatus;

public enum ServiceErrorType {
    CREATED(HttpStatus.CREATED, 1, "등록 되었습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 1, "비 인가 사용자입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, 1, "권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, 1, "해당 리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1, "시스템에 문제가 발생하였습니다."),
    INVALID_PARAMETER(HttpStatus.CONFLICT, 1, "유효하지 않은 전달값입니다."),
    DUPLICATE(HttpStatus.CONFLICT, 1, "이미 등록 되었습니다."),
    DUPLICATE_USER(HttpStatus.CONFLICT, 1, "이미 가입되어 있는 유저입니다."),
    OLDPASSWORD(HttpStatus.CONFLICT, 1, "이전 패스워드 입니다. 변경해주시기 바랍니다."),
    MISMATCH(HttpStatus.CONFLICT, 1, "인증번호가 일치하지 않습니다.")
    ;

    HttpStatus httpStatus;
    int code;
    String message;

    ServiceErrorType(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
    public String getMessage() {
        return this.message;
    }
    public Integer getCode() {
        return code;
    }
}
