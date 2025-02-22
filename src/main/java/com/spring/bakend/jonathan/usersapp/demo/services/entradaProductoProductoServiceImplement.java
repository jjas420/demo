package com.spring.bakend.jonathan.usersapp.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bakend.jonathan.usersapp.demo.repositories.EntradaProductoProductoRepository;
@Service
public class entradaProductoProductoServiceImplement implements entradaProductoProductoService {
    @Autowired 
    EntradaProductoProductoRepository entradaProductoProductoRepository;

    @Override
    @Transactional
    public void deleteById(Long id) {
        entradaProductoProductoRepository.deleteById(id);
        
    }

}
