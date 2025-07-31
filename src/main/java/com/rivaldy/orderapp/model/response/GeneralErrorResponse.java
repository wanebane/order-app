package com.rivaldy.orderapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralErrorResponse {

    private Integer status;
    private Long timestamp;
    private Object errors;
}
