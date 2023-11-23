package com.example.democrud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {

    private Integer pageNo = 0;
    private Integer pageSize = 10;

    private Sort.Direction sort = Sort.Direction.ASC;

    private String sortByColumn = "id";

    public Pageable getPageable(PageRequest pageRequest) {
        Integer page = Objects.nonNull(pageRequest.getPageNo()) ? pageRequest.pageNo : this.pageNo;
        Integer size = Objects.nonNull(pageRequest.getPageSize()) ? pageRequest.pageNo : this.pageSize;
        Sort.Direction sort = Objects.nonNull(pageRequest.getSort()) ? pageRequest.getSort() : this.sort;
        String sortByColumn = Objects.nonNull(pageRequest.getSortByColumn()) ? pageRequest.getSortByColumn() : this.sortByColumn;

        return org.springframework.data.domain.PageRequest.of(page, size, sort, sortByColumn);
    }
}
