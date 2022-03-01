package com.example.cursomc.services;

import java.util.Optional;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.repositories.CategoriaRepository;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServices {
    
    @Autowired
    private CategoriaRepository repo;
    
    public Categoria find(Integer id){
        Optional<Categoria> obj = repo.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + 
            Categoria.class.getName()));
    }

    public Categoria insert(Categoria obj){
        obj.setId(null); // processo para verificar que realmente o id não existe informado ele como nulo.
        return repo.save(obj);
    }

    public Categoria update(Categoria obj){
        find(obj.getId()); // buscando objeto no banco e caso não exista é chamado a exceção criada no método find.
        return repo.save(obj);
    }

}
