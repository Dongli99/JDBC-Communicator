package com.jdbcfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
    // FXML elements
    @FXML
    private Button searchbtn;
    @FXML
    private TableView<Music> table;
    @FXML
    private TextField idTxt;

    // Variables
    ObservableList<Music> data = FXCollections.observableArrayList();
    String[] cols;


    // Event Handlers
    public void listAll(ActionEvent event){
        Music m = new Music();
        loadAll(m);
    };

    public void listOne(ActionEvent event){
        Music m = new Music();
        int id = Integer.parseInt(idTxt.getText());
        try {
            loadOne(m, id);
        } catch (Exception e) {
            errorWarning(e.getMessage());
            e.printStackTrace();
        }
    };

    // Methods
    public void loadAll(Entity e){ // load the whole table to the tableview
        table.getColumns().clear();
        cols = e.getVarNames();
        data.clear();
        data = e.listAll(); // select all entities data
        for (String c: cols) { // add colNames to tableView
            TableColumn col = new TableColumn<>(c);
            col.setCellValueFactory(new PropertyValueFactory<>(c));
            table.getColumns().add(col);
        }
        table.setItems(data);
    }

    public void loadOne(Entity e, int id) throws Exception { // load the selected row to table view
        table.getColumns().clear();
        cols = e.getVarNames();
        data.clear();
        data.add((Music) e.searchEntity(id));// select one
        for (String c: cols) { // add colNames to tableView
            TableColumn col = new TableColumn<>(c);
            col.setCellValueFactory(new PropertyValueFactory<>(c));
            table.getColumns().add(col);
        }
        table.setItems(data);
    }

    private void errorWarning(String message) { // pop a warning window to display error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}