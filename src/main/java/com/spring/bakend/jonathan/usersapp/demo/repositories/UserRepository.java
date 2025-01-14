package com.spring.bakend.jonathan.usersapp.demo.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.spring.bakend.jonathan.usersapp.demo.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{

    @SuppressWarnings("null")
    Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String name);
    boolean existsByEmail(String email);  

    boolean existsByUsername(String username);



}
