package com.example.democrud.request;

import com.example.democrud.utils.Enum;
import com.example.democrud.utils.Enum.CategorySortEnum;
import com.example.democrud.utils.Enum.CommonSortEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * CategoryRequest class
 *
 * @author TRI
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private Long id;
    private String name;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private String afterName;
    private boolean isDeleted;
    private Integer page;
    private Integer size;
    private CommonSortEnum sort;
    private CategorySortEnum sortByColumn;
}
