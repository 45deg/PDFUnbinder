package com.github._45deg.pdfunbinder;

import com.github._45deg.pdfunbinder.outline.OutlineBuilder;
import com.github._45deg.pdfunbinder.outline.OutlineMapper;
import com.github._45deg.pdfunbinder.outline.RootOutlineData;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;

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
            try {
                File in = db.getFiles().get(0);
                PdfDocument pdfDocument = new PdfDocument(new PdfReader(in));
                RootOutlineData rootItem = RootOutlineData.createRootOutline(in.getName(), pdfDocument);
                (new OutlineBuilder(pdfDocument)).buildOutline(rootItem);
                (new OutlineMapper(rootItem)).mapTo(treeview);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Load Error: " + e.toString());
                alert.showAndWait();
            }
            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }
}
