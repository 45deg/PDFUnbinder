package com.github._45deg.pdfunbinder.outline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OutlineDataTest {

    @Test
    void getEndPageFlat() {
        OutlineData c1 = new OutlineData("c1", 1);
        OutlineData c2 = new OutlineData("c2", 5);
        c1.setNext(c2);

        assertEquals(new Integer(4), c1.getEndPage(false));
        assertEquals(new Integer(5), c1.getEndPage(true));
    }

    @Test
    void getEndPageNest() {
        OutlineData c1 = new OutlineData("c1", 1);
        OutlineData sc1 = new OutlineData("sc1", 1);
        OutlineData c2 = new OutlineData("c2", 5);
        c1.setNext(c2);
        c1.addChildren(sc1);

        assertEquals(new Integer(4), sc1.getEndPage(false));
        assertEquals(new Integer(5), sc1.getEndPage(true));
    }

    @Test
    void addChildren(){
        OutlineData c1 = new OutlineData("c1", 1);
        OutlineData sc1 = new OutlineData("sc1", 1);
        OutlineData sc2 = new OutlineData("sc2", 4);

        c1.addChildren(sc1);
        c1.addChildren(sc2);

        assertEquals(2, c1.getChildren().size());
        assertEquals(sc1, c1.getChildren().get(0));
        assertEquals(sc2, c1.getChildren().get(1));
    }
}