package com.example.zzyzzy.semiprojectv1.repository;

import com.example.zzyzzy.semiprojectv1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserid(String userid);
    boolean existsByEmail(String email);

    Optional<User> findByUserid(String userid);
}
