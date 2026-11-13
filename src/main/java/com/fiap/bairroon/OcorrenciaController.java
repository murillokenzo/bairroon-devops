package com.fiap.bairroon;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    private final OcorrenciaRepository repo;
    private final BairroRepository bairros;

    public OcorrenciaController(OcorrenciaRepository repo, BairroRepository bairros) {
        this.repo = repo;
        this.bairros = bairros;
    }

    @GetMapping
    public List<Ocorrencia> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ocorrencia> buscar(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> criar(@Valid @RequestBody Ocorrencia o) {
        Bairro b = o.getBairro().getId() == null ? null
                : bairros.findById(o.getBairro().getId()).orElse(null);
        if (b == null) {
            return ResponseEntity.badRequest().body("Bairro inexistente");
        }
        o.setId(null);
        o.setBairro(b);
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(o));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @Valid @RequestBody Ocorrencia o) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Bairro b = o.getBairro().getId() == null ? null
                : bairros.findById(o.getBairro().getId()).orElse(null);
        if (b == null) {
            return ResponseEntity.badRequest().body("Bairro inexistente");
        }
        o.setId(id);
        o.setBairro(b);
        return ResponseEntity.ok(repo.save(o));
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
