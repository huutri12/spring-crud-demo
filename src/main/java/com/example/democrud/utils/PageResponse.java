package com.example.democrud.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PageResponse<T> {
    private T contents;
    private Integer currentPage;
    private Long totalElements;
    private Integer totalPages;
}
