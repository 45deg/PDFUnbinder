package com.github._45deg.pdfunbinder.outline;

import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class OutlineMapperTest {

    private TreeView tree;
    private RootOutlineData root;
    private OutlineData c1, sc1, c2;

    @BeforeEach
    void before(){
        tree = new TreeView();

        root = new RootOutlineData("root",1,7);
        c1 = new OutlineData("c1", 1);
        sc1 = new OutlineData("sc1", 1);
        c2 = new OutlineData("sc2", 5);
        c1.addChildren(sc1);
        c1.setNext(c2);
        root.addChildren(c1);
        root.addChildren(c2);

        (new OutlineMapper(root)).mapTo(tree);
    }

    @Test
    void mapTo() {
        TreeItem rootNode = tree.getRoot();
        System.out.println(rootNode);
        TreeItem c1Node = (TreeItem) rootNode.getChildren().get(0);
        TreeItem sc1Node = (TreeItem) c1Node.getChildren().get(0);
        TreeItem c2Node = (TreeItem) rootNode.getChildren().get(1);

        assertEquals(root, rootNode.getValue());
        assertEquals(c1, c1Node.getValue());
        assertEquals(sc1, sc1Node.getValue());
        assertEquals(c2, c2Node.getValue());

        assertEquals(rootNode, c1Node.getParent());
        assertEquals(c1Node, sc1Node.getParent());
    }

    @Test
    void mapToText() {
        assertEquals("root (1-7)", tree.getRoot().getValue().toString());
    }
}