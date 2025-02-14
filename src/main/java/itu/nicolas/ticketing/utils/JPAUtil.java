package itu.nicolas.ticketing.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory("myPersistenceUnit");
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Erreur de création d'EntityManagerFactory : " + ex);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void close() {
        entityManagerFactory.close();
    }
}
