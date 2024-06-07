package com.distribuida.repository;

import com.distribuida.db.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PersonaRepositoryImpl implements PersonaRepository {

    @Autowired
    private EntityManager em;

    @Override
    public Persona findById(Integer id) {
        return this.em.find(Persona.class, id);
    }

    @Override
    public List<Persona> findAll() {
        return this.em.createQuery("Select p from Persona p order by id asc", Persona.class).getResultList();
    }

    @Override
    public void insert(Persona persona) {

        em.getTransaction().begin();
        em.persist(persona);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Integer id) {
        em.getTransaction().begin();
        this.em.remove(this.findById(id));
        em.getTransaction().commit();
    }

    @Override
    public void update(Persona persona) {
        em.getTransaction().begin();
        this.em.merge(persona);
        em.getTransaction().commit();
    }
}
