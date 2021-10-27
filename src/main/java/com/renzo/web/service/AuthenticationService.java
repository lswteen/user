package com.renzo.web.service;

import com.renzo.core.exception.ApiException;
import com.renzo.core.type.ServiceErrorType;
import com.renzo.domain.user.entity.User;
import com.renzo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;

    public User anthenticate(String token){
        try {
            // authorization으로부터 type과 credential을 분리
            String[] split = token.split(" ");
            String type = split[0];
            String credential = split[1];

            if ("Basic".equalsIgnoreCase(type)) {
                // credential을 디코딩하여 email과 password를 분리
                String decoded = new String(Base64Utils.decodeFromString(credential));
                String[] emailAndPassword = decoded.split(":");

                User user = userService.getByEmailAndPassword(emailAndPassword[0], emailAndPassword[1]);
                if (user == null)
                    throw new ApiException(ServiceErrorType.UNAUTHORIZED);
                else
                    return user;
            } else {
                throw new ApiException(ServiceErrorType.UNAUTHORIZED);

            }

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
            throw new ApiException(ServiceErrorType.UNAUTHORIZED);
        }
    }
}
