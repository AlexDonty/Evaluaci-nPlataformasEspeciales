package com.consume.save.service;

import com.consume.save.commons.requestDto.OperationDto;
import com.consume.save.commons.requestDto.UserDto;
import com.consume.save.commons.responseDto.OpClientResponse;
import com.consume.save.commons.responseDto.ResponseDto;
import org.springframework.data.domain.Page;

public interface OperationService {

    OpClientResponse createOperation(OperationDto operationDto);

    OpClientResponse updateOperation(OpClientResponse operationDto);

    Page<OpClientResponse> obtenerOperaciones(int page, int size, String sortBy, String direction);

    ResponseDto user(UserDto userDto);
}
