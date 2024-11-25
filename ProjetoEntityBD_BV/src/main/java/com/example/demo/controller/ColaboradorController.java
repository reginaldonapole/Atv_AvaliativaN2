package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Colaborador;
import com.example.demo.repository.ColaboradorRepository;
import com.example.demo.responses.Response;

@RestController
@RequestMapping("/colaborador")
@CrossOrigin ( origins = "http://127.0.0.1:5500/" , allowCredentials = "true")
public class ColaboradorController {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    // 1. Listar todos os colaboradores
    @GetMapping
    public List<Colaborador> Get() {
        return colaboradorRepository.findAll();
    }

    // 2. Buscar colaborador por ID
    @GetMapping("/{id}")
    public ResponseEntity<Colaborador> GetById(@PathVariable(value = "id") long id) {
        Optional<Colaborador> colaborador = colaboradorRepository.findById(id);
        if (colaborador.isPresent())
            return new ResponseEntity<>(colaborador.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 3. Criar novo colaborador
    @PostMapping
    public ResponseEntity<Response<Colaborador>> Post(@Valid @RequestBody Colaborador colaborador, BindingResult result) {
        Response<Colaborador> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        colaboradorRepository.save(colaborador);
        response.setData(colaborador);
        return ResponseEntity.ok(response);
    }

    // 4. Atualizar colaborador
    @PutMapping("/{id}")
    public ResponseEntity<Response<Colaborador>> Put(
            @PathVariable(value = "id") long id,
            @Valid @RequestBody Colaborador newColaborador,
            BindingResult result) {
        Optional<Colaborador> oldColaborador = colaboradorRepository.findById(id);
        Response<Colaborador> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        if (oldColaborador.isPresent()) {
            Colaborador colaborador = oldColaborador.get();
            colaborador.setNome(newColaborador.getNome());
            colaborador.setCpf(newColaborador.getCpf()); // Atualizando CPF
            colaborador.setDepartamento(newColaborador.getDepartamento()); // Atualizando Departamento
            colaboradorRepository.save(colaborador);
            response.setData(colaborador);
            return ResponseEntity.ok(response);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 5. Excluir colaborador
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id) {
        Optional<Colaborador> colaborador = colaboradorRepository.findById(id);
        if (colaborador.isPresent()) {
            colaboradorRepository.delete(colaborador.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
