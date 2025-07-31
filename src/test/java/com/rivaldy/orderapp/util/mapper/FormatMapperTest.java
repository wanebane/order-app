package com.rivaldy.orderapp.util.mapper;

import com.rivaldy.orderapp.model.response.GeneralResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatMapperTest {

    private FormatMapper formatMapper;

    @BeforeEach
    void setUp() {
        formatMapper = new FormatMapper();
    }

    @Test
    void toGeneralResponse_WithMessageOnly_ShouldReturnCorrectResponse() {
        String testMessage = "Test message";

        GeneralResponse response = formatMapper.toGeneralResponse(testMessage);

        assertNotNull(response);
        assertEquals(testMessage, response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void toGeneralResponse_WithMessageAndData_ShouldReturnCorrectResponse() {
        String testMessage = "Success response";
        Object testData = new Object() {
            public final String field1 = "value1";
            public final int field2 = 123;
        };

        GeneralResponse response = formatMapper.toGeneralResponse(testMessage, testData);

        assertNotNull(response);
        assertEquals(testMessage, response.getMessage());
        assertEquals(testData, response.getData());
    }

    @Test
    void toGeneralResponse_WithNullMessage_ShouldReturnResponseWithNullMessage() {
        GeneralResponse response = formatMapper.toGeneralResponse(null);

        assertNotNull(response);
        assertNull(response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void toGeneralResponse_WithMessageAndNullData_ShouldReturnResponseWithNullData() {
        String testMessage = "Message with null data";

        GeneralResponse response = formatMapper.toGeneralResponse(testMessage, null);

        assertNotNull(response);
        assertEquals(testMessage, response.getMessage());
        assertNull(response.getData());
    }
}