package com.study.projeto.domain.dao.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EntityManagerProvider {
    private static final EntityManagerProvider INSTANCE = new EntityManagerProvider();
    private EntityManagerFactory factory;
    private static final String PERSISTENCE_UNIT = "vendas_online";

    private EntityManagerProvider(){
        this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    public static EntityManagerProvider getInstance(){
        return INSTANCE;
    }

    public EntityManagerFactory getFactory(){
        return factory;
    }

    public EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}
