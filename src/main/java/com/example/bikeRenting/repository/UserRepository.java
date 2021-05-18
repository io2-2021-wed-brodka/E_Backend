package com.example.bikeRenting.repository;


import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.model.entity.User;
import com.example.bikeRenting.model.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    @Query("select u from User u where u.status = \'BLOCKED\'")
    List<User> findBlocked();

    @Query("select u from User u where u.status <> :status")
    Set<User> findAllNotStatus(@Param("status") UserStatus status);
}
