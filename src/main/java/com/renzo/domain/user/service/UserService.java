package com.renzo.domain.user.service;


import com.renzo.core.exception.ApiException;
import com.renzo.core.type.ServiceErrorType;
import com.renzo.domain.user.entity.User;
import com.renzo.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ServiceErrorType.NOT_FOUND));
    }

    public User getByEmailAndPassword(String email, String password){
        return userRepository.findByEmailAndPassword(email,password)
                .orElseThrow(()-> new ApiException(ServiceErrorType.NOT_FOUND));
    }

    public User getByNicknameAndPassword(String nickname, String password) {
        return userRepository.findByNicknameAndPassword(nickname, password)
                .orElseThrow(() -> new ApiException(ServiceErrorType.NOT_FOUND));
    }

    public User getByPhonenumberAndPassword(String phonenumber, String password){
        return userRepository.findByPhonenumberAndPassword(phonenumber, password)
                .orElseThrow(()-> new ApiException(ServiceErrorType.NOT_FOUND));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ServiceErrorType.NOT_FOUND));
    }

    public void phoneNumberValidationCheck(String phonenumber){
        if (userRepository.findByPhonenumber(phonenumber).isPresent()) {
            new ApiException(ServiceErrorType.DUPLICATE);
        }
    }

    public void emailValidationCheck(String email){
        if(userRepository.findByEmail(email).isPresent()){
            new ApiException(ServiceErrorType.DUPLICATE);
        }
    }

    public void nicknameValidatoinCheck(String nickname){
        if(userRepository.findByNickname(nickname).isPresent()){
            new ApiException(ServiceErrorType.DUPLICATE);
        }
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user){
        return userRepository.save(user);
    }


}
