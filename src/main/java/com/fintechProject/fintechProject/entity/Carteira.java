package com.fintechProject.fintechProject.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "carteiras")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, unique = true)
    @OneToOne(fetch = FetchType.LAZY)
    private Usuario usuario;





}
