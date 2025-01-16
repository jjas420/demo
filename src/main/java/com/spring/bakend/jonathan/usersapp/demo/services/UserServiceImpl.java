package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bakend.jonathan.usersapp.demo.entities.Role;
import com.spring.bakend.jonathan.usersapp.demo.entities.User;
import com.spring.bakend.jonathan.usersapp.demo.models.IUser;
import com.spring.bakend.jonathan.usersapp.demo.models.UserRequest;
import com.spring.bakend.jonathan.usersapp.demo.repositories.RoleRepository;
import com.spring.bakend.jonathan.usersapp.demo.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;
      @Autowired
    private PasswordEncoder  passwordEncoder;
    private RoleRepository  roleRepository;
 
    public UserServiceImpl(UserRepository repository,RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository= roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return((List <User>) this.repository.findAll()).stream().map(user->{

            boolean admin= user.getRoles().stream().anyMatch(role-> "ROLE_ADMIN".equals(role.getName()));
            user.setAdmin(admin);
            return user;
        
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return this.repository.findAll(pageable).map(
            user->{

                boolean admin= user.getRoles().stream().anyMatch(role-> "ROLE_ADMIN".equals(role.getName()));
                user.setAdmin(admin);
                return user;
            
            }); 

     
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(@NonNull Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El email ya está en uso.");
        }
        if (repository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }
     
        
        user.setRoles(getRoles(user));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Transactional
    @Override
    public Optional<User> update(UserRequest user, Long id) {

        Optional<User> userOptional = repository.findById(id);

     
     

           if (userOptional.isPresent()) {

            User userDb = userOptional.get();
            if (repository.existsByEmail(user.getEmail())) {
                throw new IllegalArgumentException("El email ya está en uso.");
            }
            if (repository.existsByUsername(user.getUsername())) {
                throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
            }
            
            userDb.setEmail(user.getEmail());
            userDb.setLastname(user.getLastname());
            userDb.setName(user.getName());
            userDb.setUsername( user.getUsername());
           
            
            userDb.setRoles(getRoles(user));

            
            return Optional.of(repository.save(userDb));
        }
        return Optional.empty();
    }

    
    @Transactional
    @Override
    public void deleteById(Long id) {
         try {
        repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
        throw new IllegalArgumentException("El registro con ID " + id + " no existe.", e);
    } catch (DataIntegrityViolationException e) {
        throw new IllegalStateException("No se puede eliminar el registro porque está relacionado con otros datos.", e);
    } catch (Exception e) {
        throw new RuntimeException("Ocurrió un error inesperado al intentar eliminar el registro con ID " + id, e);
    }

    }
    private List<Role> getRoles(IUser user) {
        List <Role> roles= new ArrayList<>();

        Optional <Role> OptionalRoleUser= roleRepository.findByName("Role_USER");
        OptionalRoleUser.ifPresent(roles::add);
        if(user.isAdmin()){
            Optional <Role> OptionalRoleAdmin= roleRepository.findByName("Role_ADMIN");
            OptionalRoleAdmin.ifPresent(roles::add);
        }
        return roles;
    }

}
