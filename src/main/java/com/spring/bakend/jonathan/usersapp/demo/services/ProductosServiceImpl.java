package com.spring.bakend.jonathan.usersapp.demo.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private ProveedorRepository proveedorRepository;

    public ProductosServiceImpl(ProductoRepository repository,

            ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;

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
        Optional<Proveedor> provedorOptional = proveedorRepository.findById(producto.getProveedor().getId());
        if (repository.existsByNombre(producto.getNombre())) {
            throw new IllegalArgumentException("El nombre ya está en uso.");
        }
        if (provedorOptional.isPresent()) {
            Proveedor proveedor = provedorOptional.get();
            producto.setCodigo_producto(this.generarCodigoProducto());

            producto.setProveedor(proveedor);

            return repository.save(producto);
        } else {
            throw new IllegalArgumentException("proveedor no existe");

        }

    }

    @Override
    @Transactional
    public Optional<Producto> update(Producto producto, Long id) {

        Optional<Producto> ProductoOptional = repository.findById(id);

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
            throw new IllegalStateException("No se puede eliminar el registro porque está relacionado con otros datos.",
                    e);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error inesperado al intentar eliminar el registro con ID " + id, e);
        }
    }

    private String generarCodigoProducto() {
        // Obtener el último producto registrado por ID
       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    String fechaHora = sdf.format(new Date());
    return "PROD-" + fechaHora; // Código como "P0001", "P0002", etc.
    }

    @Override
    public List<Producto> guardarProductos(List<Producto> productos) {
        List <Producto> productoSS=new ArrayList<>();

        productos.forEach(producto -> {
            Optional<Proveedor> provedorOptional = proveedorRepository.findById(producto.getProveedor().getId());

            producto.setCodigo_producto(this.generarCodigoProducto());
            if (provedorOptional.isPresent()) {
                Proveedor proveedor = provedorOptional.get();

                producto.setProveedor(proveedor);
                productoSS.add(producto);

            } else {
                throw new IllegalArgumentException("proveedor no existe");

            }

        });

        return repository.saveAll(productoSS);



    }

    @Override
    @Transactional(readOnly = true)

    public boolean validarProducto(Producto producto) {
        Optional<Proveedor> provedorOptional = proveedorRepository.findById(producto.getProveedor().getId());
        if (repository.existsByNombre(producto.getNombre())) {
            throw new IllegalArgumentException("El nombre ya está en uso.");
        }
        if (provedorOptional.isPresent()) {
            Proveedor proveedor = provedorOptional.get();
            if (producto.getCodigo_producto()== null ){

            producto.setCodigo_producto(this.generarCodigoProducto());
            }
            
            producto.setProveedor(proveedor);

            return true;

        } else {
            throw new IllegalArgumentException("proveedor no existe");

        
              }
        

        
        
        


    }

   

    

}
