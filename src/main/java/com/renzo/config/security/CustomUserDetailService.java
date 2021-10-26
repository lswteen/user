package com.renzo.config.security;

import com.renzo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * UserDetails 를 받아서 권한을 처리해야한다면
     * session에 저장을 해야하겠지만 지금은 권한 처리하지 않고 그냥
     * 전달하고 토큰을 생성하도록 하겠습니다.
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userService.getByEmail(username);
    }
}
