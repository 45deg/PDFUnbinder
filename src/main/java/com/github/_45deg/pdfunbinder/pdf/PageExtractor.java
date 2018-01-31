package com.github._45deg.pdfunbinder.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.FileNotFoundException;

public class PageExtractor {
    private PdfDocument source;

    PageExtractor(PdfDocument source){
        this.source = source;
    }

    public void writePages(String dest, Integer from, Integer to) throws FileNotFoundException {
        PdfDocument resultDoc = new PdfDocument(new PdfWriter(dest));
        resultDoc.initializeOutlines();
        source.copyPagesTo(from, to, resultDoc);
        resultDoc.close();
    }
}
