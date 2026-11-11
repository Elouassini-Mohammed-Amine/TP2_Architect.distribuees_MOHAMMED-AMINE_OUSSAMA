package com.tp2.demo.repository;


import com.tp2.demo.model.Cours;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CoursRepository {

    private final Map<Integer, Cours> store =
            new ConcurrentHashMap<>();

    private final AtomicInteger sequence = new AtomicInteger(1);

    public List<Cours> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Cours> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    public Cours save(Cours cours) {
        if (cours.getId() == null) {
            cours.setId(sequence.getAndIncrement());
        }

        store.put(cours.getId(), cours);
        return cours;
    }

    public boolean delete(int id) {
        return store.remove(id) != null;
    }
}
