package com.spring.bakend.jonathan.usersapp.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.spring.bakend.jonathan.usersapp.demo.entities.User;

public interface UserRepository extends CrudRepository <User, Long> {

}
