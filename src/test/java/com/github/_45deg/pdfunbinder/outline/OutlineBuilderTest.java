package com.github._45deg.pdfunbinder.outline;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

class OutlineBuilderTest extends PdfTestBase {

    private OutlineBuilder outlineBuilder;
    private RootOutlineData rootItem;

    @BeforeEach
    void before() throws IOException {
        super.before();
        rootItem = RootOutlineData.createRootOutline(inFile.getName(), pdfDocument);
        outlineBuilder = new OutlineBuilder(pdfDocument);
    }

    @Test
    void buildOutline() {
        outlineBuilder.buildOutline(rootItem);

        List<OutlineData> c0 = rootItem.getChildren();

        // title
        assertEquals("Header 1", c0.get(1).getTitle());
        assertEquals("Subheader 1", c0.get(1).getChildren().get(0).getTitle());
        assertEquals("SubSubHeader 1", c0.get(1).getChildren().get(0).getChildren().get(0).getTitle());

        // pages
        assertEquals(new Integer(1), c0.get(1).getStartPage());
        assertEquals(new Integer(5), c0.get(2).getStartPage());
        assertEquals(new Integer(8), c0.get(3).getStartPage());

        // Unicode
        assertEquals("見出し 1", c0.get(4).getTitle());
    }
}