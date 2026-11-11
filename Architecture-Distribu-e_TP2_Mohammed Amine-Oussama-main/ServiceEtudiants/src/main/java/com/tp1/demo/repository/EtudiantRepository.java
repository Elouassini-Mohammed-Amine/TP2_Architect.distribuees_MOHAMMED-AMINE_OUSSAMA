
package com.tp1.demo.repository;

import com.tp1.demo.model.Etudiant;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class EtudiantRepository {

    private final Map<Integer, Etudiant> store =
            new ConcurrentHashMap<>();

    private final AtomicInteger sequence = new AtomicInteger(1);

    public List<Etudiant> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Etudiant> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    public Etudiant save(Etudiant etudiant) {
        if (etudiant.getId() == null) {
            etudiant.setId(sequence.getAndIncrement());
        }

        store.put(etudiant.getId(), etudiant);
        return etudiant;
    }

    public boolean delete(int id) {
        return store.remove(id) != null;
    }
}
