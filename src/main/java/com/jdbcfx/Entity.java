package com.jdbcfx;

import javafx.collections.ObservableList;

abstract class Entity {
    // -- This abstract class will be the blueprint of entities --

    // Instance Variable
    DataAccess dataAccess; // the dataAccess will be a bridge between Entity and database
    int nVars;
    String[] varNames;
    ObservableList<Entity> allEntities;

    // constructors
    public Entity(){}
    public Entity(String[] records){}

    // Getters
    public int getnVars() { return nVars; }
    public String[] getVarNames() { return varNames; }

    // Abstract Methods
    public abstract void initEntity();
    public abstract void addOne();
    public abstract void updateOne(String variable, String value);
    public abstract Entity searchEntity(int id) throws Exception;
    public abstract ObservableList listAll();
}
