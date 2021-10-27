package com.renzo.domain.user.repository;


import com.renzo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhonenumber(String phonenumber);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmailAndPassword(String email, String password);
}
