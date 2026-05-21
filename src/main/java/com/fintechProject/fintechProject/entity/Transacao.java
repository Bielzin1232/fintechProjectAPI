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
    private UUID transacaoID;



    @Column(unique = true)
    private String chaveIdempotencia;


    @CreationTimestamp
    @Column(updatable = false,nullable = false)
    private LocalDateTime data;


    @Column(nullable = false)
    private BigDecimal valor;
    @ManyToOne()
    @JoinColumn(name = "destinatario_id",nullable = false)
    private Carteira destinatario;

    @ManyToOne
    @JoinColumn(name = "remetente_id",nullable = false)
    private Carteira remetente;



}
