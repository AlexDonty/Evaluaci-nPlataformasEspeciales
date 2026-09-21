package com.consume.save.controller;

import com.consume.save.commons.requestDto.OperationDto;
import com.consume.save.commons.requestDto.UserDto;
import com.consume.save.commons.responseDto.OpClientResponse;
import com.consume.save.commons.responseDto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Operation(
            summary = "Actualizar operación",
            description = "Actualiza una operación existente en el sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación creada exitosamente"
            )
    })
    @Tag(
            name = "Operaciones",
            description = "Operaciones relacionadas con la gestión de operaciones"
    )
    ResponseEntity<OpClientResponse> updateOperation(@Valid @RequestBody OpClientResponse operationDto);

    @Operation(
            summary = "Obtener operaciones",
            description = "Obtiene una lista paginada de operaciones"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Operaciones obtenidas exitosamente"
            )
    })
    @Tag(
            name = "Operaciones",
            description = "Operaciones relacionadas con la gestión de operaciones"
    )
    ResponseEntity<Page<OpClientResponse>> obtenerOperaciones(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction);

    @Operation(
            summary = "Login usuario",
            description = "Permite a un usuario iniciar sesión en el sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario autenticado exitosamente"
            )
    })
    @Tag(
            name = "Usuarios",
            description = "Operaciones relacionadas con la gestión de usuarios"
    )
    ResponseEntity<ResponseDto> user(@RequestBody UserDto userDto);
}
