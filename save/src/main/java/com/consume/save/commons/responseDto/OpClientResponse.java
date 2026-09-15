package com.consume.save.commons.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpClientResponse implements Serializable {

    private long id;
    private String estatus;
    private String referencia;
    private String operacion;

}
