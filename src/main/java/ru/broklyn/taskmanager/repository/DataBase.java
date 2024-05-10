package ru.broklyn.taskmanager.repository;

import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;
import jetbrains.exodus.env.*;

public class DataBase {

    private static DataBase instance;
    private final PersistentEntityStore entityStore;

    private DataBase() {
        EnvironmentConfig environmentConfig = new EnvironmentConfig().setLogDurableWrite(true);
        Environment environment = Environments.newInstance(".TaskManagerData", environmentConfig);
        entityStore = PersistentEntityStores.newInstance(environment);
    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public PersistentEntityStore getEntityStore() {
        return entityStore;
    }
}

