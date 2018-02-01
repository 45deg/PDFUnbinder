package com.github._45deg.pdfunbinder.common;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;

public abstract class PdfTestBase {

    protected PdfDocument pdfDocument;
    protected File inFile;

    @BeforeEach
    protected void before() throws IOException {
        inFile = new File(getClass().getClassLoader().getResource("sample.pdf").getFile());
        pdfDocument = new PdfDocument(new PdfReader(inFile));
    }

    @AfterEach
    protected void after(){
        if(pdfDocument != null)
            pdfDocument.close();
    }
}
