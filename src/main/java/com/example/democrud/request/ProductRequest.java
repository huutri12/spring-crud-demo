package com.example.democrud.request;

import com.example.democrud.entity.CategoryEntity;
import com.example.democrud.utils.Enum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * ProductRequest class
 *
 * @author TRI
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private Long id;
    private String name;
    private Double price;
    private LocalDateTime createdAt;
    private String updatedBy;
    private String createdBy;
    private LocalDateTime updatedAt;
    private CategoryEntity categoryEntity;
    private Boolean isDeleted;
    private Integer page;
    private Integer size;
    private Enum.CommonSortEnum sort;
    private Enum.CategorySortEnum sortByColumn;
}
