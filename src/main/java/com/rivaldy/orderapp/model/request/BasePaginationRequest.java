package com.rivaldy.orderapp.model.request;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class BasePaginationRequest {
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "id";
    private Sort.Direction sortDirection = Sort.Direction.DESC;

    public Pageable toPageable(){
        return PageRequest.of(page, size, sortDirection, sortBy);
    }
}
