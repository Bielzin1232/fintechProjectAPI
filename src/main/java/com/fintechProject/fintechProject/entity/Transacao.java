package com.fintechProject.fintechProject.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id",nullable = false,unique = true,updatable = false)
    private UUID transacaoID;



    @Column(name = "chave_idempotencia", unique = true, nullable = false, updatable = false)
    private String chaveIdempotencia;


    @CreationTimestamp
    @Column(updatable = false,nullable = false,name = "transacao_data")
    private LocalDateTime data;


    @Column(nullable = false,name = "transacao_valor",precision = 19,scale = 2)
    private BigDecimal valor;
    @ManyToOne()
    @JoinColumn(name = "destinatario_id",nullable = false)
    private Carteira destinatario;

    @ManyToOne
    @JoinColumn(name = "remetente_id")
    private Carteira remetente;



}
