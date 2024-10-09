package br.com.study.carros.domain.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
  private static final String PERSISTENCE_UNIT = "carros-jpa";
  private static EntityManagerFactory emf = null;

  private static EntityManagerFactory getFactory() {
    if (emf == null) {
      emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }
    return emf;
  }

  public static void closeFactory() {
    if (emf != null) emf.close();
  }

  public static EntityManager getEntityManager() {
    return getFactory().createEntityManager();
  }
}
