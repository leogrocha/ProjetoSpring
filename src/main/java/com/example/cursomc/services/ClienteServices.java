package com.example.cursomc.services;

import java.util.Optional;
import java.util.List;

import com.example.cursomc.domain.Cliente;
import com.example.cursomc.dto.CLienteDTO;
import com.example.cursomc.repositories.ClienteRepository;
import com.example.cursomc.services.exceptions.DataIntegrityException;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ClienteServices {
    
    @Autowired
    private ClienteRepository repo;
    
    public Cliente find(Integer id){
        Optional<Cliente> obj = repo.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + 
            Cliente.class.getName()));
    }

    public Cliente update(Cliente obj){
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj); // buscando objeto no banco e caso não exista é chamado a exceção criada no método find.
        return repo.save(obj);
    }

    public void delete(Integer id){
        find(id); // Verifica se existe o id e caso não existe gera a exceção do método
        try {
            repo.deleteById(id);
        } catch(DataIntegrityException e){
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }
    }

    public List<Cliente> findAll(){
        return repo.findAll();
    }


    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDto(CLienteDTO objDto){
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    private void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

}
