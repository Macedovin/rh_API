package com.macedovingithub.rh.controller;

import com.macedovingithub.rh.model.Funcionario;
import com.macedovingithub.rh.repository.FuncionarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioController(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @GetMapping
    public List<Funcionario> listar() {
        return funcionarioRepository.findAll();
    }

    @GetMapping("/{funcionarioId}")
    public ResponseEntity<Funcionario> buscar(@PathVariable Long funcionarioId) {
        return funcionarioRepository.findById(funcionarioId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario cadastrar(@RequestBody Funcionario funcionario) {
        System.out.println();
        return funcionarioRepository.save(funcionario);
    }

    @PutMapping("/{funcionarioId}")
    public ResponseEntity<Funcionario> atualizar(@PathVariable Long funcionarioId, @RequestBody Funcionario funcionario) {
        if(!funcionarioRepository.existsById(funcionarioId)) {
            return ResponseEntity.notFound().build();
        }

        funcionario.setId(funcionarioId);
        Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionario);

        return ResponseEntity.ok(funcionarioAtualizado);
    }

//    DELETE method searched and implemented by myself to complete a CRUD

    @DeleteMapping("/{funcionarioId}")
    public ResponseEntity<Long>  apagar(@PathVariable Long funcionarioId) {
        if(!funcionarioRepository.existsById(funcionarioId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        funcionarioRepository.deleteById(funcionarioId);
        return new ResponseEntity<>(funcionarioId, HttpStatus.OK);
    }
}
