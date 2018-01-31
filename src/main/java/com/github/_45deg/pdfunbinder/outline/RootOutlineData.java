package com.github._45deg.pdfunbinder.outline;

import com.itextpdf.kernel.pdf.PdfDocument;

/*
* Outline Model for Root Node
* */

public class RootOutlineData extends OutlineData {
    private Integer endPage;

    private RootOutlineData(String title, Integer startPage, Integer endPage) {
        super(title, startPage);
        this.endPage = endPage;
    }

    @Override public Integer getEndPage(boolean includeOffset) {
        return endPage;
    }

    public static RootOutlineData createRootOutline(String title, PdfDocument pdfDocument){
        return new RootOutlineData(title, 0,
                pdfDocument.getPageNumber(pdfDocument.getLastPage()));
    }
}
