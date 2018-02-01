package com.github._45deg.pdfunbinder;

import com.github._45deg.pdfunbinder.outline.OutlineBuilder;
import com.github._45deg.pdfunbinder.outline.OutlineData;
import com.github._45deg.pdfunbinder.outline.OutlineMapper;
import com.github._45deg.pdfunbinder.outline.RootOutlineData;
import com.github._45deg.pdfunbinder.pdf.PageExtractor;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class MainController {
    @FXML
    public Button submitbutton;
    @FXML
    private Pane root;
    @FXML
    private Label openinglabel;
    @FXML
    private Label selectinglabel;
    @FXML
    private TreeView treeview;

    private Set<OutlineData> selectedItems = new HashSet<>();
    private PdfDocument pdfDocument;

    @FXML
    public void initialize() {
        openinglabel.setText("Drag & Drop a PDF File.");

        root.setOnDragOver(this::handleDragOver);
        root.setOnDragDropped(this::handleDragDropped);
        submitbutton.setOnAction(this::handleSubmitAction);
    }

    private void handleSubmitAction(ActionEvent actionEvent) {
        if(pdfDocument == null) return;

        final File directory = new DirectoryChooser().showDialog(root.getScene().getWindow());
        if(directory != null) {
            String dirPath = directory.getAbsolutePath();
            PageExtractor extractor = new PageExtractor(pdfDocument);
            try {
                for(OutlineData chapter : selectedItems) {
                    String dest = dirPath + File.separator + chapter.getTitle() + ".pdf";
                    extractor.writePages(dest, chapter.getStartPage(), chapter.getEndPage(false));
                }
            } catch (FileNotFoundException e) {
                showError(e);
            } finally {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Process");
                alert.setHeaderText("Result");
                alert.setContentText("Exported " + selectedItems.size() + " files.");
                alert.showAndWait();
            }
        }
    }

    private void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    private void handleDragDropped(DragEvent event) {
        // data dropped
        Dragboard db = event.getDragboard();

        if (db.hasFiles()) {
            final File in = db.getFiles().get(0);
            Platform.runLater(() -> {
                try {
                    pdfDocument = new PdfDocument(new PdfReader(in));
                    RootOutlineData rootItem = RootOutlineData.createRootOutline(in.getName(), pdfDocument);
                    (new OutlineBuilder(pdfDocument)).buildOutline(rootItem);
                    (new OutlineMapper(rootItem)).mapTo(treeview);

                    selectedItems.clear();
                    setEventHandlerToCheckbox((CheckBoxTreeItem<OutlineData>)treeview.getRoot());
                    submitbutton.setDisable(true);
                    openinglabel.setText(in.getName());
                } catch (Exception e) {
                    showError(e);
                }
            });
        }

        event.setDropCompleted(true);
        event.consume();
    }

    private void handleItemChecked(CheckBoxTreeItem<OutlineData> item , boolean newValue) {
        if(newValue) {
            selectedItems.add(item.getValue());
        } else {
            selectedItems.remove(item.getValue());
        }

        int size = selectedItems.size();
        selectinglabel.setText(size + " chapter" + (size == 1 ? "" : "s") + " selected.");
        submitbutton.setDisable(size < 1);
    }

    private void showError(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Load Error: " + e.toString());
        alert.showAndWait();
    }

    private void setEventHandlerToCheckbox(CheckBoxTreeItem<OutlineData> item) {
        item.selectedProperty().addListener((o, oldValue, newValue) -> { handleItemChecked(item, newValue); });

        for(TreeItem<OutlineData> child: item.getChildren()) {
            setEventHandlerToCheckbox((CheckBoxTreeItem<OutlineData>) child);
        }
    }

}