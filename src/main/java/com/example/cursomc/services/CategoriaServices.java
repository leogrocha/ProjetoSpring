package com.example.cursomc.services;

import java.util.Optional;
import java.util.List;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.repositories.CategoriaRepository;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;
import com.example.cursomc.services.exceptions.DataIntegrityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

    public void delete(Integer id){
        find(id); // Verifica se existe o id e caso não existe gera a exceção do método
        try {
            repo.deleteById(id);
        } catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll(){
        return repo.findAll();
    }


    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

}
