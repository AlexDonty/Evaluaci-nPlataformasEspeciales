package com.consume.save.commons.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "operations")
@Data
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String operacion;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal importe;

    @Column(nullable = false)
    private String cliente;

    @Column(nullable = false)
    private String referencia;

    @Column(nullable = false)
    private String estatus;

    @Column(nullable = false)
    private String secreto;
}
