package com.distribuida.service;

import com.distribuida.db.Persona;

import java.util.List;

public interface PersonaService {

    Persona findById(Integer id);
    List<Persona> findAll();
    void insert(Persona persona);
    void delete(Integer id);
    void update(Persona persona);

}
