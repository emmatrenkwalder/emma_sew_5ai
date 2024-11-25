package sew_emma.example.demo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@Entity
public class Benutzer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role ="USER"; // Rolle des Benutzers (z. B. "ADMIN" oder "USER")

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Methoden aus UserDetails implementieren

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Hier gibt es eine Liste mit einer einzelnen Rolle (Authority)
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Das Konto ist nie abgelaufen, dies kann angepasst werden
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Das Konto ist nicht gesperrt, dies kann angepasst werden
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Die Anmeldedaten sind nicht abgelaufen, dies kann angepasst werden
    }

    @Override
    public boolean isEnabled() {
        return true; // Der Benutzer ist immer aktiviert, dies kann angepasst werden
    }
}
