package com.example.democrud.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryEntityTest {

    @Test
    public void testInitNoConstructor() {
        CategoryEntity categoryEntity = new CategoryEntity();
        Assertions.assertNull(categoryEntity.getName());
        Assertions.assertNull(categoryEntity.getUpdatedAt());
    }

    @Test
    public void testSetterGetter() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("abc");
        Assertions.assertEquals("abc", categoryEntity.getName());
    }

}