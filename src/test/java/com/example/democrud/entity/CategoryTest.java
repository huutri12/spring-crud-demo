package com.example.democrud.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    public void testInitNoConstructor() {
        Category category = new Category();
        Assertions.assertNull(category.getName());
        Assertions.assertNull(category.getUpdatedAt());
    }

    @Test
    public void testSetterGetter() {
        Category category = new Category();
        category.setName("abc");
        Assertions.assertEquals("abc", category.getName());
    }

}