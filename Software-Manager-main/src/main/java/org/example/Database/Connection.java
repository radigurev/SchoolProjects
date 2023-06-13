package org.example.Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.Root;
import org.example.Entity.ProjectType;

import java.util.ArrayList;
import java.util.List;

public interface Connection {
     static EntityManager GetConnection() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("radodeve_SchoolProject");
        return emf.createEntityManager();
    };
}
