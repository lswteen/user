package com.renzo.core.exception;


import com.renzo.core.type.ServiceErrorType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ApiException extends RuntimeException {
    private final ServiceErrorType serviceErrorType;

    public ApiException(ServiceErrorType serviceErrorType) {
        super(serviceErrorType.getMessage());
        this.serviceErrorType = serviceErrorType;

        log.info("ERRORMSG : {} {} {}",
                this.serviceErrorType.getHttpStatus(),
                this.serviceErrorType.getCode(),
                this.serviceErrorType.getMessage());
    }
}
