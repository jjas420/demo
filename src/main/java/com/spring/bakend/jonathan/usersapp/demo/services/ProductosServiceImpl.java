package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Proveedor;
import com.spring.bakend.jonathan.usersapp.demo.repositories.ProductoRepository;
import com.spring.bakend.jonathan.usersapp.demo.repositories.ProveedorRepository;

@Service
public class ProductosServiceImpl implements ProductoService {

    private ProductoRepository repository;

    public ProductosServiceImpl(ProductoRepository repository) {

        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)

    public List<Producto> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)

    public Optional<Producto> findById(@NonNull Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {

        producto.setCodigo_producto(this.generarCodigoProducto() );

    
        return repository.save(producto);

    }

    @Override
    @Transactional
    public Optional<Producto> update(Producto producto, Long id) {

                Optional<Producto> ProductoOptional= repository.findById(id);

                if (ProductoOptional.isPresent()) {
                    Producto productobd = ProductoOptional.get();
                    productobd.setNombre(producto.getNombre());
                    productobd.setPrecio(producto.getPrecio());
                    productobd.setProveedor(producto.getProveedor());
                    productobd.setStock(producto.getStock());
                    
        
                    return Optional.of(repository.save(productobd));
                    
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

    private String generarCodigoProducto() {
        // Obtener el último producto registrado por ID
        Long ultimoId = repository.findTopByOrderByIdDesc().getId();  // Busca el último ID registrado
        String prefijo = "P";  // Prefijo para los productos
        long cantidadProductos = ultimoId + 1;  // El nuevo código será el último ID + 1
        return prefijo + String.format("%04d", cantidadProductos);  // Código como "P0001", "P0002", etc.
    }


    }

