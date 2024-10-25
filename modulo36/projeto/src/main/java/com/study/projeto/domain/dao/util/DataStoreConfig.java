package com.study.projeto.domain.dao.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;


public class DataStoreConfig {

  private final String DATA_BASE_URI =
      "mongodb://admin:admin@localhost:27017";
  private final String DATA_BASE_NAME = "vendasonline";
  private static Datastore datastore;

  private static final DataStoreConfig INSTANCE = new DataStoreConfig();

  private DataStoreConfig() {
    createDataStore();
  }

  private void createDataStore() {
    datastore = Morphia.createDatastore(MongoClients.create(DATA_BASE_URI), DATA_BASE_NAME);
    datastore.getMapper().mapPackage("com.study.projeto.domain.model.nonrelational");
    datastore.ensureIndexes();
  }

  public static Datastore getDataStore() {
    return datastore;
  }
}
