package com.github._45deg.pdfunbinder;

public class OutlineData {
    private String title;
    private Integer startPage;
    private Integer endPage;

    public OutlineData(String title, Integer startPage) {
        this.title = title;
        this.startPage = startPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    @Override
    public String toString() {
        return this.getTitle() + "(" + this.getStartPage() + ")";
    }
}
