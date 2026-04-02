package com.br.ejb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@Table(name = "BENEFICIO")
@NoArgsConstructor
@AllArgsConstructor
public class Beneficio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean ativo = true;

    @Version
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Long version;

}