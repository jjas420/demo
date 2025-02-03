package com.spring.bakend.jonathan.usersapp.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.spring.bakend.jonathan.usersapp.demo.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{

    @SuppressWarnings("null")
    Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String name);
    boolean existsByEmail(String email);  

    boolean existsByUsername(String username);
    @Query(value = "SELECT * FROM users WHERE name LIKE CONCAT('%', :name, '%')", nativeQuery = true)
   List<User> findByNameContainingNative(@Param("name") String name);
    

}
