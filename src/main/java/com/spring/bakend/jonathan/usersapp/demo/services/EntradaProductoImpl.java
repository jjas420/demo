package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.spring.bakend.jonathan.usersapp.demo.entities.EntradaProducto;
import com.spring.bakend.jonathan.usersapp.demo.entities.EntradaProductoProducto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Proveedor;
import com.spring.bakend.jonathan.usersapp.demo.repositories.EntradaProductoProductoRepository;
import com.spring.bakend.jonathan.usersapp.demo.repositories.EntradaProductoRepository;
import com.spring.bakend.jonathan.usersapp.demo.repositories.ProductoRepository;
import com.spring.bakend.jonathan.usersapp.demo.repositories.ProveedorRepository;

import jakarta.transaction.Transactional;

@Service
public class EntradaProductoImpl implements EntradaProductoService {
    ProveedorServiceImpl proveedorServiceImpl;
    EntradaProductoProductoRepository entradaProductoProductoRepository;
    EntradaProductoRepository entradaProductoRepository;

    ProductosServiceImpl productosServiceImpl;

    private ProductoRepository repositoryproducto;

    @Autowired 
    private entradaProductoProductoService entradaProductoProductoService;

    public EntradaProductoImpl(ProveedorServiceImpl proveedorServiceImpl,
            com.spring.bakend.jonathan.usersapp.demo.repositories.EntradaProductoProductoRepository entradaProductoProductoRepository,
            EntradaProductoRepository entradaProductoRepository, ProductosServiceImpl productosServiceImpl,
            ProductoRepository repositoryproducto) {
        this.proveedorServiceImpl = proveedorServiceImpl;
        this.entradaProductoProductoRepository = entradaProductoProductoRepository;
        this.entradaProductoRepository = entradaProductoRepository;
        this.productosServiceImpl = productosServiceImpl;
        this.repositoryproducto = repositoryproducto;
    }

    @Override
    @Transactional

    public List<EntradaProducto> findAll() {


       return entradaProductoRepository.findAll();


    }

    @Override
    public Optional<EntradaProducto> findById(@NonNull Long id) {

        return  entradaProductoRepository.findById(id);

    }


    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
    }

    @Override
    @Transactional
    public EntradaProducto save(EntradaProducto entradaProducto, List<Producto> productos) {

        EntradaProducto savedEntrada = entradaProductoRepository.save(entradaProducto);

        for (Producto producto : productos) {
            if (producto.getId() == null) {

                productosServiceImpl.save(producto);

                if (repositoryproducto.existsByNombre(producto.getNombre())) {
                    producto= repositoryproducto.findByNombre(producto.getNombre()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                    EntradaProductoProducto entradaProductoProducto = new EntradaProductoProducto();
                    entradaProductoProducto.setEntradaProducto(savedEntrada);
                    entradaProductoProducto.setProducto(producto);
                    entradaProductoProducto.setCantidad(producto.getStock());
                    entradaProductoProductoRepository.save(entradaProductoProducto);



                }

            } else {
                EntradaProductoProducto entradaProductoProducto = new EntradaProductoProducto();
                entradaProductoProducto.setEntradaProducto(savedEntrada);
                entradaProductoProducto.setProducto(producto);
                entradaProductoProducto.setCantidad(producto.getStock());

                Producto productoviejo = productosServiceImpl.findById(producto.getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                productoviejo.setStock(productoviejo.getStock() + producto.getStock());

                productosServiceImpl.update(productoviejo, productoviejo.getId());

                entradaProductoProductoRepository.save(entradaProductoProducto);
            }

            // Guardar la relación
        }

        return savedEntrada;

    }

    @Override
    public Optional<EntradaProducto> update(EntradaProducto entradaProducto, List<Producto> productos, Long id) {
   // Aquí se extrae la instancia de EntradaProducto del Optional.
   EntradaProducto entrada = entradaProductoRepository.findById(id)
   .orElseThrow(() -> new RuntimeException("Entrada no encontrada"));
    entrada.setFecha(entradaProducto.getFecha());
    entrada.setProveedor(entrada.getProveedor());
    


List<EntradaProductoProducto> productosOriginales = entrada.getProductos();
List<EntradaProductoProducto> productosAEliminar = new ArrayList<>();

// Iterar sobre la lista de productos actualizados
for (Producto productoNuevo : productos) {
    if (productoNuevo.getId() == null) {
        productosServiceImpl.save(productoNuevo);
    }
    
Optional<EntradaProductoProducto> productoExistente = productosOriginales.stream()
   .filter(ep -> ep.getProducto().getId().equals(productoNuevo.getId()))
   .findFirst();

if (productoExistente.isPresent()) {
   // Si el producto ya existe en la entrada, actualizamos la cantidad
   EntradaProductoProducto entradaProductoProducto = productoExistente.get();

   // Restar la cantidad anterior (lo que ya se había sumado previamente)
   Long cantidadAnterior = entradaProductoProducto.getCantidad();

   entradaProductoProducto.setCantidad( productoNuevo.getStock()); // Sumar la nueva cantidad

   // Guardamos la relación actualizada
   entradaProductoProductoRepository.save(entradaProductoProducto);

   // Actualizamos el stock del producto en la base de datos
   Producto productoBD = productosServiceImpl.findById(productoNuevo.getId())
           .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
           productoBD.setStock(productoBD.getStock() - cantidadAnterior);
   productoBD.setStock(productoBD.getStock() + productoNuevo.getStock());
   productosServiceImpl.update(productoBD, productoBD.getId());
} else {
   // Si el producto no está en la entrada, lo agregamos
   EntradaProductoProducto nuevaRelacion = new EntradaProductoProducto();
   nuevaRelacion.setEntradaProducto(entrada);
   nuevaRelacion.setProducto(productoNuevo);
   nuevaRelacion.setCantidad(productoNuevo.getStock());

   entradaProductoProductoRepository.save(nuevaRelacion);

   // Actualizamos el stock del producto en la base de datos
   Producto productoBD = productosServiceImpl.findById(productoNuevo.getId())
           .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
   productoBD.setStock(productoBD.getStock());
   productosServiceImpl.update(productoBD, productoBD.getId());
}
}

// Eliminar los productos que no están en la lista de productos actualizados
for (EntradaProductoProducto productoOriginal : productosOriginales) {
    // Verificamos si el producto original no está en la lista de productos actualizados
    if (productos.stream().noneMatch(p -> p.getId().equals(productoOriginal.getProducto().getId()))) {
        productosAEliminar.add(productoOriginal);
        System.out.println("productos que no estan asociados para eliminar"+ productoOriginal.getProducto().getId()  );
        System.out.println("cantidad " + productoOriginal.getCantidad());
    }
}
Producto productoBD =new Producto();
// Eliminar los productos que ya no están en la lista (eliminar de la relación)
for (EntradaProductoProducto productoAEliminar : productosAEliminar) {
    // Aseguramos que eliminamos correctamente la relación en la tabla intermedia
    System.out.println("nombre: "+productoAEliminar.getProducto().getNombre() +" id:"+ productoAEliminar.getId() );
    Long cantidadAnterior = productoAEliminar.getCantidad();

     productoBD = productosServiceImpl.findById(productoAEliminar.getProducto().getId())
    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    productoBD.setStock(productoBD.getStock() - cantidadAnterior);
    productosServiceImpl.update(productoBD, productoBD.getId());
    entradaProductoProductoRepository.eliminarProducto(productoAEliminar.getId());


    if (productoBD.getStock()<=0) {
        System.out.println("entro aqui");

        repositoryproducto.eliminarProducto(productoBD.getId());        
    }
    
}

return Optional.of(entrada);


}

        
   
    }

  
