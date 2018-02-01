package com.github._45deg.pdfunbinder.outline;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;

public abstract class PdfTestBase {

    PdfDocument pdfDocument;
    File inFile;

    @BeforeEach
    void before() throws IOException {
        inFile = new File(getClass().getClassLoader().getResource("sample.pdf").getFile());
        pdfDocument = new PdfDocument(new PdfReader(inFile));
    }

    @AfterEach
    void after(){
        if(pdfDocument != null)
            pdfDocument.close();
    }
}
