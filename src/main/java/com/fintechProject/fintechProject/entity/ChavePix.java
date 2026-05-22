package com.fintechProject.fintechProject.entity;

import com.fintechProject.fintechProject.enums.TipoPix;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chaves_pix")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChavePix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPix tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
