package com.consume.api.commons.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OperationDto implements Serializable {

    @NotBlank(message = "El campo operacion no puede estar vacío")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ ]+$",
            message = "El campo operacion solo se permiten letras sin espacios"
    )
    @Schema(
            description = "Tipo de operación",
            example = "Venta"
    )
    private String operacion;

    @Digits(
            integer = 10,
            fraction = 2,
            message = "El importe debe tener máximo 10 enteros y 2 decimales"
    )
    @Schema(
            description = "Importe de la operación",
            example = "100.50"
    )
    private BigDecimal importe;

    @NotBlank(message = "El campo cliente no puede estar vacío")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ ]+$",
            message = "El campo cliente solo se permiten letras sin espacios"
    )
    @Schema(
            description = "Nombre del cliente",
            example = "Juan Pérez"
    )
    private String cliente;

    @NotBlank(message = "El campo secreto no puede estar vacío")
    @Schema(
            description = "Secreto de la operación",
            example = "Secreto123"
    )
    private String secreto;
}
