package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bakend.jonathan.usersapp.demo.entities.User;
import com.spring.bakend.jonathan.usersapp.demo.repositories.UserRepository;

import io.micrometer.common.lang.NonNull;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {

        return (List) this.repository.findAll();

    }

    @Transactional(readOnly = true)

    @Override
    public Optional<User> findById(@NonNull Long id) {
        return repository.findById(id);
    }

    @Transactional

    @Override
    public User save(User user) {
        return repository.save(user);
      
    }

    @Transactional
    @Override
    public void  deleteById(@NonNull Long id) {

        repository.deleteById(id);
    }


}
