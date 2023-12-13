package com.jdbcfx;

import java.sql.*;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

abstract class DataAccess {
    // -- This abstract class will handle the connection to database --

    // JDBC secret strings
    private final String DRIVER = DatabaseConfig.getDriver();
    private final String DATABASE_URL = DatabaseConfig.getUrl();
    private final String USERNAME = DatabaseConfig.getUser();
    private final String PASSWORD = DatabaseConfig.getPassword();

    // JDBC variables
    Connection conn;
    Statement st;
    PreparedStatement selectAllPst, selectWherePst, insertPst, updatePst;
    ResultSet rs;
    ResultSetMetaData md;

    // Instance variables
    String[] colNames;
    ArrayList<String[]> data; // store all the data
    int nCols;
    int[] dataTypes;

    // Constructor
    public DataAccess(){
        closeConnection();
        openConnection();
    }

    // Methods
    public void openConnection(){
        // open the connection to database
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Wrong Driver.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(){
        // close the connection to database
        try{
                if (conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                System.out.println("Connection failed to close");
                e.printStackTrace();
            }
    }

    public void setTableVars() {
        // set the instance variables, colNames and nCols
        try {
            md = rs.getMetaData();
            nCols = md.getColumnCount();
            colNames = new String[nCols];
            dataTypes = new int[nCols];
            for (int i = 0; i < nCols; i++) {
                colNames[i] = md.getColumnName(i+1).toLowerCase();
                dataTypes[i] = md.getColumnType(i+1);
            }
        } catch (SQLException e) {
            System.out.println("Result MetaData failed to obtain");
            e.printStackTrace();
        }
    }

    public String[] getRow(){
        // get one row of data and store it into the
        String[] record = new String[nCols];
        for (int i = 0; i < nCols; i++) {
            try {
                record[i] = rs.getString(i+1);
            } catch (SQLException e) {
                System.out.println("Failed to retreive the record");
                e.printStackTrace();
            }
        }
        return record;
    }

    public Object parseData(int colIndex, String colValue){
        // this method will parse the value to specific data
        switch (dataTypes[colIndex]){
            case 2:
                case 4: // int
                return parseInt(colValue);
            case 8: // double
                return parseDouble(colValue);
            default: // string, to simplify the project
                return colValue;
        }
    }

    public abstract void insertRow(Entity e); // implement insert command

    public abstract void updataRow(Entity e); // implement update command

    public abstract ArrayList<String[]> selectTable(); // select all data in table and return

    public abstract void setData(); // conduct select all and refresh data variable

    public abstract String[] selectById(int id);
    public abstract void commitChange();
}
