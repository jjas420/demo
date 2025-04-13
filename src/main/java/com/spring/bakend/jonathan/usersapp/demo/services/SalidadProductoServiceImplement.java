package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.bakend.jonathan.usersapp.demo.entities.EntradaProducto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
import com.spring.bakend.jonathan.usersapp.demo.entities.ProductoSalidad;
import com.spring.bakend.jonathan.usersapp.demo.entities.SalidadProductos;
import com.spring.bakend.jonathan.usersapp.demo.repositories.SalidadProductoRepository;
import com.spring.bakend.jonathan.usersapp.demo.services.excepciónPersonalizadas.ExceptionSalidas;

@Service
public class SalidadProductoServiceImplement implements SalidadProductoService {

    @Autowired
    SalidadProductoRepository salidadProductoRepository;

    @Autowired
    ProductoService productoService;

    @Override
    public List<SalidadProductos> findAll() {
        return salidadProductoRepository.findAll();

    }

    @Override
    public Optional<SalidadProductos> findById(@NonNull Long id) {
        return salidadProductoRepository.findById(id);

    }

    @Override
    @Transactional
    public void save(SalidadProductos salidadProductos) {
        List<Producto> productosSinStock = new ArrayList<>();

        // Validar todos los productos antes de hacer cambios
        for (ProductoSalidad ps : salidadProductos.getProductos()) {
            Producto producto = productoService.findById(ps.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < ps.getCantidad()) {
                productosSinStock.add(producto); // Agregar el ProductoSalidad con el error
            }
        }

        if (!productosSinStock.isEmpty()) {
            throw new ExceptionSalidas(productosSinStock);
        } else {
            for (ProductoSalidad ps : salidadProductos.getProductos()) {
                Producto producto = productoService.findById(ps.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                ;

                producto.setStock(producto.getStock() - ps.getCantidad());
                productoService.update(producto, producto.getId());
            }

            salidadProductoRepository.save(salidadProductos);

        }

        // Si todo está ok, hacer el descuento y guardar

    }

    @Override
    @Transactional

    public void update(SalidadProductos nuevaSalida, Long id) {
        // Obtener la salida existente

        SalidadProductos salidaExistente = salidadProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salida no encontrada"));

        // Paso 1: Restaurar el stock de los productos de la salida original
        for (ProductoSalidad psOriginal : salidaExistente.getProductos()) {
            Producto producto = productoService.findById(psOriginal.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Restaurar el stock de los productos de la salida original
            producto.setStock(producto.getStock() + psOriginal.getCantidad());
            productoService.update(producto, producto.getId());
            System.out.println("paso por aqui");
        }

        // Paso 2: Validar los nuevos productos
        List<Producto> productosSinStock = new ArrayList<>();
        for (ProductoSalidad psNuevo : nuevaSalida.getProductos()) {
            Producto producto = productoService.findById(psNuevo.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Verificar si hay suficiente stock
            if (producto.getStock() < psNuevo.getCantidad()) {
                productosSinStock.add(producto);
            }
        }

        if (!productosSinStock.isEmpty()) {
            // Si hay error, restauramos el stock original y lanzamos la excepción
            throw new ExceptionSalidas(productosSinStock);
        }

        // Paso 3: Actualizar el stock con las nuevas cantidades
        for (ProductoSalidad psNuevo : nuevaSalida.getProductos()) {
            Producto producto = productoService.findById(psNuevo.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Actualizar el stock de los productos con la cantidad nueva
            producto.setStock(producto.getStock() - psNuevo.getCantidad());
            productoService.update(producto, producto.getId());
        }

        // Paso 4: Actualizar la salida con los nuevos datos
        salidaExistente.setFecha(nuevaSalida.getFecha());
        salidaExistente.setMotivo(nuevaSalida.getMotivo());
        salidaExistente.setUsuarioResponsable(nuevaSalida.getUsuarioResponsable());
        salidaExistente.setObservaciones(nuevaSalida.getObservaciones());

        // Limpiar productos antiguos
        salidaExistente.getProductos().clear();

        // Agregar nuevos productos
        for (ProductoSalidad psNuevo : nuevaSalida.getProductos()) {
            psNuevo.setSalida(salidaExistente); // Establecer la relación
            salidaExistente.getProductos().add(psNuevo);
        }
        // Guardar la salida actualizada
        salidadProductoRepository.save(salidaExistente);
    }

    @Override
    @Transactional

    public void deleteById(Long id) {
        // Paso 1: Buscar la salida existente por ID
        SalidadProductos salidaExistente = salidadProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salida no encontrada"));

        // Paso 2: Restaurar el stock de los productos de la salida a eliminar
        for (ProductoSalidad psOriginal : salidaExistente.getProductos()) {
            Producto producto = productoService.findById(psOriginal.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Restaurar el stock de los productos de la salida original
            producto.setStock(producto.getStock() + psOriginal.getCantidad());
            productoService.update(producto, producto.getId());
        }

        // Paso 3: Eliminar la salida de la base de datos
        salidadProductoRepository.deleteById(id);
        System.out.println("Salida eliminada correctamente");
    }

}
