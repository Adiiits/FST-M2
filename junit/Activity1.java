package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {

    //test fixture
    static ArrayList<String> list;

    @BeforeEach
    void setUp() throws Exception {
        list = new ArrayList<String>();
        list.add("alpha"); // at index 0
        list.add("beta"); // at index 1
    }

    //adding insert method
    @Test
    public void insertTest() {
        // Assertion for size
        assertEquals(2, list.size(), "Wrong size");
        // Add new element
        list.add(2, "Garri");
        // Assert with new elements
        assertEquals(3, list.size(), "Wrong size");

        // Assert individual elements
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("beta", list.get(1), "Wrong element");
        assertEquals("Garri", list.get(2), "Wrong element");
    }

    //replace item

   @Test
   public void replaceTest(){
       assertEquals(2, list.size(), "Wrong size");
       list.add(2, "charri");
       // Assert with new elements
       assertEquals(3, list.size(), "Wrong size");
       list.set(1,"Mike");
       assertEquals("Mike", list.get(1), "Wrong element");
       assertEquals("alpha", list.get(0), "Wrong element");
       assertEquals("charri", list.get(2), "Wrong element");

   }


}
