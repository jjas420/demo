package com.spring.bakend.jonathan.usersapp.demo.services.excepciónPersonalizadas;

import java.util.List;

import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;

public class ExceptionSalidas extends RuntimeException {
    private final List<Producto> productosConError;

    public ExceptionSalidas(List<Producto> productosConError) {
        super("Stock insuficiente en uno o más productos");
        this.productosConError = productosConError;
    }

    public List<Producto> getProductosConError() {
        return productosConError;
    }
}


