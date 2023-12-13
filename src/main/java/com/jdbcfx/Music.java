package com.jdbcfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Music extends Entity{

    // Instance variables
    private int id;
    private int singer;
    private String title;
    private int year;

    // Constructors
    public Music(){
        super();
        initEntity();
    }

    public Music(String[] record){
        initEntity();
        id = (int) dataAccess.parseData(0, record[0]);
        singer = (int) dataAccess.parseData(1, record[1]);
        title = (String) dataAccess.parseData(2, record[2]);
        year = (int) dataAccess.parseData(3, record[3]);
    }

    public Music(int id, int singer, String title, int year){
        initEntity();
        this.id = id;
        this.singer = singer;
        this.title = title;
        this.year = year;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getSinger() { return singer; }
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public void setId(int id) { this.id = id; }
    public void setSinger(int singer) { this.singer = singer; }
    public void setTitle(String title) { this.title = title; }
    public void setYear(int year) { this.year = year; }

    // Methods
    @Override
    public void initEntity(){
        // initialize the connection and metadata
        if (dataAccess == null) // check if the connection has been built
            dataAccess = new MusicData();
        if (nVars==0){
            nVars = 4;
            varNames = dataAccess.colNames;
        }
    }

    @Override
    public void addOne() {
        initEntity();
        dataAccess.insertRow(this);
    }

    @Override
    public void updateOne(String variable, String value) {
        initEntity();
        // update a music
        switch (variable){
            case "id":
                this.setId((int) dataAccess.parseData(0, value));
                break;
            case "singer":
                this.setSinger((int) dataAccess.parseData(1, value));
                break;
            case "title":
                this.setTitle((String) dataAccess.parseData(2, value));
                break;
            case "year":
                this.setYear((int) dataAccess.parseData(3, value));
                break;
        }
        dataAccess.updataRow(this);
    }

    @Override
    public Entity searchEntity(int id) throws Exception {
        initEntity();
        String[] record = dataAccess.selectById(id);
        Music music = new Music(record);
        return music;
    }

    @Override
    public ObservableList<Entity> listAll() {
        // create the list of allEntities
        allEntities = FXCollections.observableArrayList();
        dataAccess.selectTable();
        for (String[] record: dataAccess.data) {
            Music music = new Music();
            music.setId((int) dataAccess.parseData(0, record[0]));
            music.setSinger((int) dataAccess.parseData(1, record[1]));
            music.setTitle((String) dataAccess.parseData(2, record[2]));
            music.setYear((int) dataAccess.parseData(3, record[3]));
            allEntities.add(music);
        }
        return allEntities;
    }
}
