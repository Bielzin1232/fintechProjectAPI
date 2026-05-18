package com.fintechProject.fintechProject.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "transacoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transcaoID;

    @OneToOne(fetch = FetchType.LAZY)
    private Usuario user;

    @Column(nullable = false)
    private Long valor;

    private String descricao;




}
