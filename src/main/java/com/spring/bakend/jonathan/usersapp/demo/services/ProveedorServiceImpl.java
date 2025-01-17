package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bakend.jonathan.usersapp.demo.entities.Proveedor;
import com.spring.bakend.jonathan.usersapp.demo.entities.User;
import com.spring.bakend.jonathan.usersapp.demo.models.UserRequest;
import com.spring.bakend.jonathan.usersapp.demo.repositories.ProveedorRepository;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    private ProveedorRepository repository;

    public ProveedorServiceImpl(ProveedorRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)

    public List<Proveedor> findAll() {

        return this.repository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Proveedor> findById(@NonNull Long id) {
        return repository.findById(id);


    }

    @Override
    @Transactional

    public Proveedor save(Proveedor proveedor) {
        if (repository.existsByNombre(proveedor.getNombre())) {
            throw new IllegalArgumentException("El nombre ya está en uso.");
        }
      

        return repository.save(proveedor);

    }

    @Override
    @Transactional

    public Optional<Proveedor> update (Proveedor proveedor, Long id) {
        Optional<Proveedor> provedorOptional= repository.findById(id);
        if (provedorOptional.isPresent()) {
            Proveedor proveedorbd = provedorOptional.get();
            proveedorbd.setNombre(proveedor.getNombre());
            proveedorbd.setTelefono(proveedor.getTelefono());

            return Optional.of(repository.save(proveedorbd));
            
        }
        return Optional.empty();
    }

    @Override
    @Transactional
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

}
