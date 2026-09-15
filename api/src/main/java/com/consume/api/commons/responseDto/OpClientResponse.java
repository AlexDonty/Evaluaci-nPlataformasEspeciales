package com.consume.api.commons.responseDto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OpClientResponse implements Serializable {

    private long id;
    private String estatus;
    private String referencia;
    private String operacion;

}
