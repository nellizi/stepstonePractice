package com.likelion.stepstone.user;

import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    void deleteByUserId(String userId);

    Optional<UserEntity> findByUserId(String userId);

    Optional<UserEntity> findByName(String name);

}
