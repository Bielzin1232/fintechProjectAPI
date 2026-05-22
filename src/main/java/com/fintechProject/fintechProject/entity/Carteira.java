package com.fintechProject.fintechProject.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "carteiras")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(nullable = false,updatable = false,name = "id")
    private Long id;


    @Version
    private Long versao;



    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, unique = true)
    @OneToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @Column(nullable = false,precision = 19,scale = 2,updatable = false)
    private BigDecimal saldo = BigDecimal.ZERO;




}
