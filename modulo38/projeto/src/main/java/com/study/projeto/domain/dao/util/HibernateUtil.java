package com.study.projeto.domain.dao.util;

import java.util.HashMap;
import java.util.Map;
import javax.naming.CommunicationException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import org.hibernate.exception.JDBCConnectionException;

public class HibernateUtil {
  private final Map<String, EntityManagerFactory> emfMap = new HashMap<>();
  private static HibernateUtil INSTANCE;

  int numAttempts = 0;

  private HibernateUtil() {
    createDataBases();
  }

  public static HibernateUtil getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new HibernateUtil();
    }
    return INSTANCE;
  }

  public Map<String, EntityManagerFactory> getEntityManagerFactoryMap() {
    return emfMap;
  }

  public void createDataBases() {
    do {
      numAttempts++;
      try {
        addEntityManagerFactory(Constants.Databases.PERSISTENCE_UNIT_NAME_DB1);

        if (isOpen(Constants.Databases.PERSISTENCE_UNIT_NAME_DB1)) {
          break;
        }

      } catch (PersistenceException e) {
        Throwable cause = e.getCause();
        if (cause != null
            && (cause instanceof CommunicationException
                || cause instanceof JDBCConnectionException)) {
          continue;
        }
        throw new RuntimeException(
            "Exception occurred when creating EntityManagerFactory for the named "
                + "persistence unit: ",
            e);
      }

    } while (numAttempts <= Constants.Databases.NUMBER_ATTEMPTS);
  }

  private void addEntityManagerFactory(String persistenceUnitName) {
    if (!emfMap.containsKey(persistenceUnitName)) {
      EntityManagerFactory managerFactory =
          Persistence.createEntityManagerFactory(persistenceUnitName);
      emfMap.put(persistenceUnitName, managerFactory);
    }
  }

  private boolean isOpen(String persistenceUnit) {
    return emfMap.get(persistenceUnit).isOpen();
  }
}
