package com.github._45deg.pdfunbinder.pdf;

import com.github._45deg.pdfunbinder.common.PdfTestBase;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PageExtractorTest extends PdfTestBase {

    @Test
    void writePages() throws IOException {
        PageExtractor extractor = new PageExtractor(pdfDocument);
        File outFile = File.createTempFile("test_", "_tmp.pdf");
        extractor.writePages(outFile.getAbsolutePath(), 1, 5);

        PdfDocument outPdfDocument = null;
        try {
            outPdfDocument = new PdfDocument(new PdfReader(outFile));
            assertEquals(5, outPdfDocument.getNumberOfPages());
        } finally {
            if(outPdfDocument != null)
                outPdfDocument.close();
        }
    }
}