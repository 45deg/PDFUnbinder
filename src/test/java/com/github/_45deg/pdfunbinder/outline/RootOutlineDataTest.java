package com.github._45deg.pdfunbinder.outline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RootOutlineDataTest extends PdfTestBase {

    @BeforeEach
    void before() throws IOException {
        super.before();
    }

    @Test
    void createRootOutline() {
        RootOutlineData rootOutline = RootOutlineData.createRootOutline("sample.pdf", pdfDocument);

        assertEquals(new Integer(1), rootOutline.getStartPage());
        assertEquals(new Integer(15), rootOutline.getEndPage(false));
        assertEquals("sample.pdf",rootOutline.getTitle());
    }

}