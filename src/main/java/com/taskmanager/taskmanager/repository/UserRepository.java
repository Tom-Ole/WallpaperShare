package com.taskmanager.taskmanager.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.taskmanager.taskmanager.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByFirstname(String firstname);
    Optional<UserEntity> findByLastname(String lastname);



}
