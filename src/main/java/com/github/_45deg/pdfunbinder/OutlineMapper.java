package com.github._45deg.pdfunbinder;

import com.itextpdf.kernel.pdf.*;
import javafx.scene.control.CheckBoxTreeItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OutlineMapper {
    private PdfDocument pdfDoc;
    private Map<String, PdfObject> names;

    private OutlineData previousObject;

    public OutlineMapper(PdfDocument pdfDoc) {
        this.pdfDoc = pdfDoc;
    }

    public void mapTo(CheckBoxTreeItem<OutlineData> rootItem) {
        PdfOutline pdfOutlines = pdfDoc.getOutlines(false);
        names = pdfDoc.getCatalog().getNameTree(PdfName.Dests).getNames();
        walkOutlines(rootItem, pdfOutlines);
    }

    private void walkOutlines(CheckBoxTreeItem<OutlineData> target, PdfOutline outline) {
        if(outline != null) {
            if (outline.getDestination() != null) {
                String title = outline.getTitle();
                int pageNumber = pdfDoc.getPageNumber((PdfDictionary) outline.getDestination().getDestinationPage(names));
                // System.out.println(title + " p" + pageNumber + ":" + outline.getDestination().getPdfObject().getType());
                OutlineData outlineObj = new OutlineData(title, pageNumber);
                CheckBoxTreeItem<OutlineData> item = new CheckBoxTreeItem<OutlineData>(outlineObj);
                item.setIndependent(true);

                target.getChildren().add(item);
                target = item;
            }

            for (PdfOutline child : outline.getAllChildren()) {
                walkOutlines(target, child);
            }
        }
    }

}
