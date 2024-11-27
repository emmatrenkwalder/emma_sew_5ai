package sew_emma.example.demo;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//: Gibt an, dass diese Konfiguration priorisiert wird, falls es mehrere Sicherheitskonfigurationen gibt. Eine niedrige Zahl (z. B. 1) bedeutet hohe Priorität.
@Configuration
@Order(1)// Marks this class as a configuration class for Spring
public class Security {

    public static class SecurityConfig {

        /**
         * authentication ->identity
         * authorization : correct permissions
         * S
         *
         */
//Erstellung der Session:
//
//Wenn ein Benutzer sich erfolgreich authentifiziert (z. B. durch Login), erstellt der Server eine Sitzung (Session) und weist dieser Sitzung eine eindeutige Session-ID zu.
//Diese Session-ID wird im Session-Cookie gespeichert und an den Client (Browser) gesendet.
//Speicherung im Browser:
//
//Der Browser speichert den Session-Cookie temporär. Solange der Cookie gültig ist (oder bis der Browser geschlossen wird), wird dieser automatisch mit jeder HTTP-Anfrage an die gleiche Domäne gesendet.
//Identifizierung des Benutzers:
//
//Der Server verwendet die Session-ID, um die gespeicherten Daten für den Benutzer zu identifizieren (z. B. Benutzerrollen, Berechtigungen oder andere Zustandsinformationen).
//Beispiel:
//
//Login-Flow:
//Der Benutzer sendet seine Anmeldedaten an den Server.
//Der Server prüft die Daten und erstellt eine Session. Die Session-ID wird im Cookie zurückgesendet.
//Folgende Anfragen:
//Der Browser sendet den Session-Cookie bei jeder Anfrage mit.
//Der Server liest die Session-ID aus und erkennt den Benutzer.
        //Ein Session-Cookie ist eine kleine Textdatei, die vom Server an den Browser gesendet wird, um Sitzungsinformationen zu speichern. Es dient dazu, den Benutzer über verschiedene HTTP-Anfragen hinweg zu identifizieren, ohne dass der Benutzer sich bei jeder Anfrage erneut authentifizieren muss.
      //Die SecurityFilterChain ist der Kern der Sicherheitskonfiguration. Sie definiert:
        //Wie Anfragen authentifiziert und autorisiert werden.
        //Welche Endpunkte zugänglich sind und welche geschützt sind.
        //Zusätzliche Sicherheitsmechanismen wie CSRF, CORS und Sitzungsverwaltung.
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth //,"/api/songs/**","/api/artists/**"S
//                            .requestMatchers("/api/benutzer/register", "/api/benutzer/login","/api/benutzer/logout","/login","/error","/favicon.ico").permitAll()
//                          //          .requestMatchers("/api/songs/**", "/api/artists/**").authenticated()
//                                    .requestMatchers("/api/songs/**", "/api/artists/**").permitAll()
//                          //  .requestMatchers("/**").authenticated()// Public endpoints
//                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .anyRequest().permitAll() // Protect all other endpoints
//Deaktiviert den CSRF-Schutz (Cross-Site Request Forgery), der standardmäßig in Spring Security aktiviert ist.
//Warum deaktiviert?
//CSRF ist bei REST-APIs oft nicht notwendig, insbesondere wenn ausschließlich stateless Authentifizierungsmechanismen wie Tokens verwendet werden.
                    )
                    /*
                    .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint((request, response, authException) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\": \"Unauthorized\"}");
                    }))
                    /*
                     */
                   .formLogin(form -> form.permitAll()); // Disable form-based login
            //.httpBasic(httpBasicConfigurer -> httpBasicConfigurer.disable());
          //  System.out.println(http.build().toString());
            return http.build();

        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowCredentials(true);
            configuration.addAllowedOrigin("http://localhost:8081"); // Frontend URL
            configuration.addAllowedHeader("*");
            configuration.addAllowedMethod("*");
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
        @Bean
        public UserDetailsService userDetailsService(BenutzerRepository benutzerRepository) {
            return username -> {
                UserDetails user = benutzerRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                System.out.println("User found: " + user.getUsername()); // Add logging here
                return user;
            };
        }

        /**
         * Provides a password encoder for encoding and validating passwords.
         */
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }


    }
}
