package com.example.cursomc.services;

import java.util.Optional;

import com.example.cursomc.domain.Pedido;
import com.example.cursomc.repositories.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServices {
    
    @Autowired
    private PedidoRepository repo;

    public Pedido find(Integer id){
        Optional<Pedido> obj = repo.findById((id));

        return obj.orElseThrow(() -> new com.example.cursomc.services.exceptions.ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + 
            Pedido.class.getName()));
    }
}
