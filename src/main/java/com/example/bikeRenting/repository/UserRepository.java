package com.example.bikeRenting.repository;


import com.example.bikeRenting.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    @Query("select u from User u where u.blocked = true")
    List<User> findBlocked();
}
