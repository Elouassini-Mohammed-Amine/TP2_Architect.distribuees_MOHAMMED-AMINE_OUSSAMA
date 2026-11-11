package com.tp2.demo.controller;

import com.tp2.demo.model.Cours;
import com.tp2.demo.dto.EtudiantDto;
import com.tp2.demo.repository.CoursRepository;
import com.tp2.demo.service.EtudiantClient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/cours")
public class CoursController {

    private final CoursRepository repo;
    private final EtudiantClient etudiantClient;

    public CoursController(
            CoursRepository repo,
            EtudiantClient etudiantClient) {

        this.repo = repo;
        this.etudiantClient = etudiantClient;
    }

    @GetMapping
    public List<Cours> all() {
        return repo.findAll();
    }

    @GetMapping("/enrichis")
    public List<Map<String, Object>> getCoursEnrichis() {

        List<Map<String, Object>> resultat =
                new ArrayList<>();

        for (Cours c : repo.findAll()) {

            Map<String, Object> item = new LinkedHashMap<>();

            item.put("id", c.getId());
            item.put("titre", c.getTitre());
            item.put("etudiantId", c.getEtudiantId());

            try {
                EtudiantDto etudiant =
                    etudiantClient.getEtudiantById(
                        c.getEtudiantId()
                    );

                item.put(
                    "etudiantNom",
                    etudiant.getNom()
                );

            } catch (Exception ex) {
                item.put("etudiantNom", "INCONNU");
            }

            resultat.add(item);
        }

        return resultat;
    }

    @GetMapping("/{id}")
    public Cours get(@PathVariable int id) {
        return repo.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cours non trouve"
                    )
                );
    }

    @PostMapping
    public ResponseEntity<Cours> create(
            @RequestBody Cours c) {

        c.setId(null);
        Cours saved = repo.save(c);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public Cours update(
            @PathVariable int id,
            @RequestBody Cours c) {

        get(id);
        c.setId(id);

        return repo.save(c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        if (!repo.delete(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cours non trouve"
            );
        }

        return ResponseEntity.noContent().build();
    }
}
