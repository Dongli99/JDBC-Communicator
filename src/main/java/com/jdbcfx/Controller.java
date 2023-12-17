package com.jdbcfx;

import com.jdbcfx.entity.Entity;
import com.jdbcfx.entity.Music;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.ArrayList;

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
    public void loadAll(Entity entity){ // load the whole table to the tableview
        initTableView(entity);
        data = entity.listAll(); // select all entities data
        table.setItems(data);
        handleClickUpdate();
    }

    public void loadOne(Entity entity, int id) throws Exception { // load the selected row to table view
        initTableView(entity);
        data.add((Music) entity.searchEntity(id));// select one
        table.setItems(data);
        handleClickUpdate();
    }

    private void handleClickUpdate() {
        for (Entity entity: data){
            entity.getUpdate().setOnAction(ev->{

            });
        }
    }

    private void initTableView(Entity entity){
        table.getColumns().clear();
        cols = entity.getVarNames();
        for (String c: cols) { // add colNames to tableView
            TableColumn col = new TableColumn<>(c);
            col.setCellValueFactory(new PropertyValueFactory<>(c));
            table.getColumns().add(col);
        }
        data.clear();
    }

    private void errorWarning(String message) { // pop a warning window to display error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}