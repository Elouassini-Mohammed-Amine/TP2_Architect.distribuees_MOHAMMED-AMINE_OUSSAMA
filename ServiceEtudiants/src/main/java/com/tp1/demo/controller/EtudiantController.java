package com.tp1.demo.controller;

import com.tp1.demo.model.Etudiant;
import com.tp1.demo.repository.EtudiantRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    private final EtudiantRepository repo;

    public EtudiantController(EtudiantRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Etudiant> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Etudiant get(@PathVariable int id) {
        return repo.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Etudiant non trouve"
                    )
                );
    }

    @PostMapping
    public ResponseEntity<Etudiant> create(
            @RequestBody Etudiant e) {

        e.setId(null);
        Etudiant saved = repo.save(e);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public Etudiant update(
            @PathVariable int id,
            @RequestBody Etudiant e) {

        get(id);
        e.setId(id);

        return repo.save(e);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        if (!repo.delete(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Etudiant non trouve"
            );
        }

        return ResponseEntity.noContent().build();
    }
}
