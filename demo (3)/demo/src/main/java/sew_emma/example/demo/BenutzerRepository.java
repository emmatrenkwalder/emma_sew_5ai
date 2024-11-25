package sew_emma.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BenutzerRepository extends JpaRepository<Benutzer, Long>  {

    Optional<Benutzer> findByUsername(String username);

   // boolean existsByUsername(String username);
}
