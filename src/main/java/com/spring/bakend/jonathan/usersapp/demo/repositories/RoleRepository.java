package com.spring.bakend.jonathan.usersapp.demo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.spring.bakend.jonathan.usersapp.demo.entities.Role;


public interface RoleRepository extends CrudRepository<Role,Long> {

    Optional <Role> findByName(String name);


}
