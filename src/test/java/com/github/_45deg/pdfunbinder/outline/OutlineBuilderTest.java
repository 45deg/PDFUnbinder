package com.github._45deg.pdfunbinder.outline;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

class OutlineBuilderTest {

    private PdfDocument pdfDocument;
    private OutlineBuilder outlineBuilder;
    private RootOutlineData rootItem;

    @BeforeEach
    void before() throws IOException {
        File in = new File(getClass().getClassLoader().getResource("sample.pdf").getFile());
        pdfDocument = new PdfDocument(new PdfReader(in));
        rootItem = RootOutlineData.createRootOutline(in.getName(), pdfDocument);
        outlineBuilder = new OutlineBuilder(pdfDocument);
    }

    @AfterEach
    void after(){
        if(pdfDocument != null)
            pdfDocument.close();
    }

    @Test
    void buildOutline() {
        outlineBuilder.buildOutline(rootItem);

        List<OutlineData> c0 = rootItem.getChildren();

        // title
        assertEquals(c0.get(1).getTitle(),"Header 1");
        assertEquals(c0.get(1).getChildren().get(0).getTitle(),"Subheader 1");
        assertEquals(c0.get(1).getChildren().get(0).getChildren().get(0).getTitle(),"SubSubHeader 1");

        // pages
        assertEquals(c0.get(1).getStartPage(), new Integer(1));
        assertEquals(c0.get(2).getStartPage(), new Integer(5));
        assertEquals(c0.get(3).getStartPage(), new Integer(8));

        // Unicode
        assertEquals(c0.get(4).getTitle(), "見出し 1");
    }
}