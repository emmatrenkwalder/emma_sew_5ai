package sew_emma.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:8081") // Hier den Port des Frontends anpassen
@RestController
@RequestMapping("/api/benutzer")
public class BenutzerController {

    private final BenutzerRepository benutzerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public BenutzerController(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Benutzer benutzer, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ungültige Eingabe: " + result.getFieldError().getDefaultMessage());
        }

        if (benutzerRepository.findByUsername(benutzer.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Benutzername ist bereits vergeben.");
        }

        benutzer.setPassword(passwordEncoder.encode(benutzer.getPassword())); // Passwort verschlüsseln
        benutzerRepository.save(benutzer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Benutzer erfolgreich registriert.");
    }

    // Login eines Benutzers
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Benutzer benutzer) {
        Benutzer existingUser = benutzerRepository.findByUsername(benutzer.getUsername())
                .orElse(null);

        if (existingUser == null || !passwordEncoder.matches(benutzer.getPassword(), existingUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ungültige Anmeldedaten.");
        }

        UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(existingUser,null,List.of(new SimpleGrantedAuthority(existingUser.getRole())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("Login erfolgreich.");
    }

    // Liste aller Benutzer (Optional, z. B. nur für Admins)
    @GetMapping
    public ResponseEntity<?> getAllBenutzer() {
        return ResponseEntity.ok(benutzerRepository.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Benutzer benutzer) {
        if (benutzerRepository.findByUsername(benutzer.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Benutzername bereits vergeben.");
        }

        // Passwort hashen
        benutzer.setPassword(passwordEncoder.encode(benutzer.getPassword()));

        // Benutzer speichern
        benutzerRepository.save(benutzer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Benutzer erfolgreich erstellt.");
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout (HttpServletRequest request , HttpServletResponse response){
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("logout erfolgreich");
    }
}