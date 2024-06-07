package com.distribuida.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@Configuration
@ComponentScan(basePackages = "com.distribuida")
public class JpaConfig {

    private EntityManagerFactory emf;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        System.out.println("*** Jpaconfig::entityManagerFactory");
        return Persistence.createEntityManagerFactory("pu-distribuida");
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory emf) {
        System.out.println("*** Jpaconfig::entityManager");
        return emf.createEntityManager();
    }

}