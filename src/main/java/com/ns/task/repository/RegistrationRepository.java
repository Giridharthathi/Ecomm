package com.ns.task.repository;

import com.ns.task.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String mail);
}
