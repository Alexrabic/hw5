package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void check() {
        assertEquals(-1, homeWork.calculate("1 + 2 * ( 3 - 4 )"));
        assertEquals(1252, homeWork.calculate("4 + 78 * ( 20 - 4 )"));
        assertEquals(2, homeWork.calculate("sqrt(4)"));
        assertEquals(4, homeWork.calculate("sqrt(16)"));
        assertEquals(4, homeWork.calculate("pow(2,2)"));
    }

}