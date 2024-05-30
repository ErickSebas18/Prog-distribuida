package com.distribuida;

import com.distribuida.db.Persona;
import jakarta.persistence.*;

import java.util.function.Function;

public class Principal {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-distribuida");

        EntityManager em = emf.createEntityManager();

        var p = new Persona();
        p.setId(1);
        p.setNombre("Pablito");
        p.setDireccion("Quito");
        p.setEdad(20);

        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

        System.out.println("Listando Personas...");
        TypedQuery<Persona> qry = em.createQuery("Select o from Persona o order by id asc", Persona.class);

        Function<Persona, Object> f = x -> x.getNombre();
        qry.getResultStream().map(f).forEach(System.out::println);

        em.close();
        emf.close();
    }
}
