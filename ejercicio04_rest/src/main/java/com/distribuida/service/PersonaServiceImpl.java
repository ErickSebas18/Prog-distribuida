package com.distribuida.service;

import com.distribuida.db.Persona;
import com.distribuida.repository.PersonaRepository;
import com.distribuida.repository.PersonaRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public Persona findById(Integer id) {
        return this.personaRepository.findById(id);
    }

    @Override
    public List<Persona> findAll() {
        return this.personaRepository.findAll();
    }

    @Override
    public void insert(Persona persona) {
        this.personaRepository.insert(persona);
    }

    @Override
    public void delete(Integer id) {
        this.personaRepository.delete(id);
    }

    @Override
    public void update(Persona persona) {
        this.personaRepository.update(persona);
    }
}
