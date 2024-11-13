package com.study.projeto.domain.dao.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

public final class EntityManagerProvider {
    private static EntityManagerProvider INSTANCE;
    private Map<String,EntityManagerFactory> entityManagerFactoryMap;

    private EntityManagerProvider(){
        this.entityManagerFactoryMap = HibernateUtil.getInstance().getEntityManagerFactoryMap();
    }

    public static EntityManagerProvider getInstance(){
        if(INSTANCE == null){
            INSTANCE = new EntityManagerProvider();
        }
        return INSTANCE;
    }

    public EntityManager getEntityManager(String persistenceUnit){
        return entityManagerFactoryMap.get(persistenceUnit).createEntityManager();
    }
}
