package com.github._45deg.pdfunbinder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class MainController {
    @FXML private BorderPane root;
    @FXML private Label openinglabel;
    @FXML private Label selectinglabel;
    @FXML private TreeView treeview;

    @FXML protected void handleSubmitAction(ActionEvent actionEvent) {

    }

    @FXML
    public void initialize() {
        openinglabel.setText("Drag & Drop a PDF File.");

        root.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) { handleDragOver(event); }
        });

        root.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) { handleDragDropped(event); }
        });
    }

    private void handleDragOver(DragEvent event){
        if (event.getDragboard().hasFiles()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    private void handleDragDropped(DragEvent event){
        // data dropped
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            File file = db.getFiles().get(0);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }
}
