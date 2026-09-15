package com.consume.api.config;

import com.consume.api.commons.requestDto.OperationDto;
import com.consume.api.commons.responseDto.OpClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "clienteApi",
        url = "${cliente.api.url}"
)
public interface Client {

    @PostMapping("/api/v1/operation")
    OpClientResponse createOperation(@RequestBody OperationDto operationDto);
}
