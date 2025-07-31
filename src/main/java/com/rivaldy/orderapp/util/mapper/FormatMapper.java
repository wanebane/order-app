package com.rivaldy.orderapp.util.mapper;

import com.rivaldy.orderapp.model.response.GeneralResponse;

public class FormatMapper {

    public GeneralResponse toGeneralResponse(String message){
        return toGeneralResponse(message, null);
    }

    public GeneralResponse toGeneralResponse(String message, Object data){
        return GeneralResponse.builder()
                .message(message)
                .data(data)
                .build();
    }
}
