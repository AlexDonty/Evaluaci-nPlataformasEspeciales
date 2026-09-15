package com.consume.api.service.impl;

import com.consume.api.commons.requestDto.OperationDto;
import com.consume.api.commons.responseDto.OpClientResponse;
import com.consume.api.config.Client;
import com.consume.api.service.OperationService;
import com.consume.api.utils.AES256Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final Client client;

    @Value("${api.key}")
    private String apiKey;

    @Override
    public OpClientResponse createOperation(OperationDto operationDto) {
        final String clave = operationDto.getSecreto();
        final String secreto = AES256Utils.decrypt(clave, apiKey);
        operationDto.setSecreto(secreto);
        return client.createOperation(operationDto);
    }
}
