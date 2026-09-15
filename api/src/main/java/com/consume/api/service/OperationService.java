package com.consume.api.service;

import com.consume.api.commons.requestDto.OperationDto;
import com.consume.api.commons.responseDto.OpClientResponse;

public interface OperationService {

    OpClientResponse createOperation(OperationDto operationDto);
}
