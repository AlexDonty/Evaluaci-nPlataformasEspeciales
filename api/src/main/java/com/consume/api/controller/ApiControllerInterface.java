package com.consume.api.controller;

import com.consume.api.commons.requestDto.OperationDto;
import com.consume.api.commons.responseDto.OpClientResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface ApiControllerInterface {

    @Operation(
            summary = "Nueva operación",
            description = "Crea una nueva operación en el sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación creada exitosamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida"
            )
    })
    @Tag(
            name = "Operaciones",
            description = "Operaciones relacionadas con la gestión de operaciones"
    )
    ResponseEntity<OpClientResponse> operation(@Valid @RequestBody OperationDto operationDto);

    @Tag(
            name = "AES Encryption",
            description = "Operaciones relacionadas con la encriptación AES"
    )
    ResponseEntity<Map<String, String>> aes(@RequestParam String data) throws NoSuchAlgorithmException;

}
