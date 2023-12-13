package com.jdbcfx;

import java.sql.SQLException;
import java.util.ArrayList;

public class MusicData extends DataAccess{
    // -- This class is the concrete class of DataAccess representing data of the Music Entity

    // Constructor
    public MusicData(){
        super();
        setData(); // all basic instance vars get initialed, e.g. nCols, colNames, data
    }

    // Overwrite the abstract methods
    @Override
    public void insertRow(Entity e) {
        Music music = (Music) e;
        try { // build and execute query
            insertPst = conn.prepareStatement("INSERT INTO music(ID, Singer, Title, Year) VALUES (?, ?, ?, ?)");
            insertPst.setInt(1, music.getId());
            insertPst.setInt(2, music.getSinger());
            insertPst.setString(3,music.getTitle());
            insertPst.setInt(4,music.getYear());
            insertPst.executeQuery();
            insertPst.close();
        } catch (SQLException se) {
            System.out.println("Failed to update.");
            se.printStackTrace();
        }
        commitChange();
        setData();
    }

    @Override
    public void updataRow(Entity e) {
        // update a row of data into table
        Music music = (Music) e;
        int id = music.getId();
        if(selectById(id).length==0) {
            // in case the data is not in the table.
            System.out.println("Music not found.");
            return;
        }
        try { // setup and execute updatePst
            updatePst = conn.prepareStatement("UPDATE music SET singer=?, title=?, year=? WHERE id=?");
            updatePst.setInt(1, music.getSinger());
            updatePst.setString(2, music.getTitle());
            updatePst.setInt(3, music.getYear());
            updatePst.setInt(4, music.getId());
            updatePst.execute();
            updatePst.close();
        } catch (SQLException se) {
            System.out.println("Failed to update.");
            se.printStackTrace();
        }
    }

    public void setData() {
        // load all data in music table and store it in a 2-d list
        data = new ArrayList<String[]>();
        String statement = "SELECT * FROM music";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(statement);
            if(nCols == 0) {
                setTableVars();    // nCols, colNames, record size get initialized
            }
            while (rs.next()){
                data.add(getRow()); // set data
            }
        } catch (SQLException e) {
            System.out.println("Failed to select the table");
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String[]> selectTable() {
        // set data to all the records in the table
        setData();
        return data;
    }

    @Override
    public String[] selectById(int id) {
        String[] record = new String[nCols];
        try {
            selectWherePst = conn.prepareStatement("SELECT * FROM music WHERE id = ?");
            selectWherePst.setInt(1, id);
            rs = selectWherePst.executeQuery();
            if (rs.next())
                record = getRow();
        } catch (SQLException se) {
            System.out.println("Failed to select by id");
            se.printStackTrace();
        }
        return record;
    }

    @Override
    public void commitChange() {
        try {
            conn.commit();
        } catch (SQLException se) {
            System.out.println("Failed to commit update statement.");
            se.printStackTrace();
        }
    }
}
