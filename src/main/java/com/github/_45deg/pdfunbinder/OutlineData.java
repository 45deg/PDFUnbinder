package com.github._45deg.pdfunbinder;

import java.util.ArrayList;
import java.util.List;

public class OutlineData {
    private String title;
    private Integer startPage;

    private OutlineData parent;
    private List<OutlineData> children;
    private OutlineData next;

    public OutlineData(String title, Integer startPage) {
        this.title = title;
        this.startPage = startPage;
    }

    public String getTitle() {
        return title;
    }
    public Integer getStartPage() {
        return startPage;
    }

    public Integer getEndPage(boolean includeOffset) {
        if(next == null) {
            return parent.getEndPage(includeOffset);
        }
        return next.startPage - (includeOffset ? 0 : 1);
    }

    public List<OutlineData> getChildren() {
        return children;
    }

    public void addChildren(OutlineData child) {
        if(children == null) {
            children = new ArrayList<OutlineData>();
        }
        child.setParent(this);
        children.add(child);
    }

    public void setNext(OutlineData next) {
        this.next = next;
    }

    public void setParent(OutlineData parent) {
        this.parent = parent;
    }

    @Override @Deprecated
    public String toString() {
        return this.getTitle() + " (" + this.getStartPage() + "-" + this.getEndPage(false) + ")";
    }

}
