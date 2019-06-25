package com.ziwang.user.dao;

import com.ziwang.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findUserByMobile(String mobile);

    @Query("select u from User u where u.username = ?1 or u.mobile =?1")
    Optional<User> findUserByUsername(String username);
}
