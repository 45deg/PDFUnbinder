package com.github._45deg.pdfunbinder.outline;

import com.itextpdf.kernel.pdf.*;

import java.util.Map;

/*
 * Builds an outline tree from PDFDocument
 * */

public class OutlineBuilder {
    private PdfDocument pdfDoc;
    private Map<String, PdfObject> names;

    public OutlineBuilder(PdfDocument pdfDoc) {
        this.pdfDoc = pdfDoc;
    }

    public void buildOutline(RootOutlineData rootItem){
        PdfOutline pdfOutlines = pdfDoc.getOutlines(false);
        names = pdfDoc.getCatalog().getNameTree(PdfName.Dests).getNames();
        walkOutlines(rootItem, pdfOutlines);
    }

    private void walkOutlines(OutlineData target, PdfOutline outline) {
        if(outline != null) {
            if (outline.getDestination() != null) {
                String title = outline.getTitle();
                int pageNumber = pdfDoc.getPageNumber((PdfDictionary) outline.getDestination().getDestinationPage(names));
                // System.out.println(title + " p" + pageNumber + ":" + outline.getDestination().getPdfObject().getType());
                OutlineData outlineObj = new OutlineData(title, pageNumber);
                target.addChildren(outlineObj);
                target = outlineObj;
            }

            for (PdfOutline child : outline.getAllChildren()) {
                walkOutlines(target, child);
            }

            // Set next field
            OutlineData prev = null;
            for (OutlineData child : target.getChildren()) {
                if (prev != null) prev.setNext(child);
                prev = child;
            }
        }
    }
}
