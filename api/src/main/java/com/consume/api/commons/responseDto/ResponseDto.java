package com.consume.api.commons.responseDto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ResponseDto implements Serializable {

    private int status;
    private String message;
}
