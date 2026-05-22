package com.fintechProject.fintechProject.entity;

import com.fintechProject.fintechProject.enums.UserRole;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.br.CPF;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(unique = true,nullable = false,updatable = false,name = "id")
    private Long id;

    private List<ChavePix>


    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "user_role")
    private UserRole role;

    @Column(nullable = false,name = "palavra_passe")
    private String palavraPasse;

    @Column(nullable = false, unique = true,length = 11,name = "cpf")
    private String cpf;

    @Column(nullable = false, unique = true,length = 255,name = "usuario_email")
    private String email;

    @Column(nullable = false,length = 150,name = "usuario_nome")
    private String nome;




    @Column(nullable = false,length = 20,name = "usuario_numero")
    private String numeroTelefone;

    @Column(nullable = false,length = 60,name = "usuario_senha")
    private String senha;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Carteira carteira;

    @CreationTimestamp
    @Column(updatable = false,nullable = false,name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public @Nullable String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
         return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
