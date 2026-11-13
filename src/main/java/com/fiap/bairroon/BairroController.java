package com.fiap.bairroon;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bairros")
public class BairroController {

    private final BairroRepository repo;

    public BairroController(BairroRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Bairro> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bairro> buscar(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bairro criar(@Valid @RequestBody Bairro b) {
        b.setId(null);
        return repo.save(b);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bairro> atualizar(@PathVariable Long id, @Valid @RequestBody Bairro b) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        b.setId(id);
        return ResponseEntity.ok(repo.save(b));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
