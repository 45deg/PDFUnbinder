package com.github._45deg.pdfunbinder.outline;

import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;

public class OutlineMapper {
    private OutlineData root;

    public OutlineMapper(RootOutlineData root) {
        this.root = root;
    }

    public void mapTo(TreeView treeview) {
        CheckBoxTreeItem<OutlineData> rootItem =
                new CheckBoxTreeItem<OutlineData>(root);
        treeview.setCellFactory(CheckBoxTreeCell.<OutlineData>forTreeView());
        treeview.setRoot(rootItem);
        walk(rootItem, root);
        rootItem.setExpanded(true);
        rootItem.setIndependent(true);
        treeview.setShowRoot(false);
    }

    private void walk(CheckBoxTreeItem<OutlineData> target, OutlineData outline){
        CheckBoxTreeItem<OutlineData> item = new CheckBoxTreeItem<OutlineData>(outline);
        item.setIndependent(true);
        target.getChildren().add(item);

        if(outline.getChildren() != null) {
            for (OutlineData child : outline.getChildren()) {
                walk(item, child);
            }
        }
    }
}
