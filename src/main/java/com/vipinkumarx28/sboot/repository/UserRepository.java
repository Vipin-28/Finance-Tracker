package com.vipinkumarx28.sboot.repository;


import com.vipinkumarx28.sboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUserName(String userName);

    public Optional<User> findUserByEmailId(String email);
    
    public Optional<User> findByEmailId(String emailId);
    public Optional<User> findByPhoneNumber(String phoneNumber);
}
