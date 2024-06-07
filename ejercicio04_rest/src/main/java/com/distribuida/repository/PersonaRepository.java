package com.distribuida.repository;

import com.distribuida.db.Persona;
import java.util.List;

public interface PersonaRepository {

    Persona findById(Integer id);
    List<Persona> findAll();
    void insert(Persona persona);
    void delete(Integer id);
    void update(Persona persona);
}
