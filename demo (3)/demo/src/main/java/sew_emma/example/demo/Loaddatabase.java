package sew_emma.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Base64;
import java.util.List;

@Configuration
public class Loaddatabase {

    @Bean
    CommandLineRunner initDatabase(PasswordEncoder passwordEncoder, Singrespository songRepository, Atristrepository artistRepository, BenutzerRepository benutzerRepository) {
        return args -> {

            // Erstelle mehrere Artists (Künstler)
            Artist artist1 = new Artist();
            artist1.setName("Artist One");
            artistRepository.save(artist1);

            Artist artist2 = new Artist();
            artist2.setName("Artist Two");
            artistRepository.save(artist2);

            Artist artist3 = new Artist();
            artist3.setName("Artist Three");
            artistRepository.save(artist3);

            // Lese die Standard-Audiodatei und konvertiere sie in Base64
            String defaultMusicData;
            try {
                String filePath = "C:/Users/emmat/Downloads/sew_emma/demo (3)/demo/default.mp3";
                byte[] musicDataBytes = Files.readAllBytes(Paths.get(filePath));
                defaultMusicData = Base64.getEncoder().encodeToString(musicDataBytes);
            } catch (IOException e) {
                System.out.println("Fehler beim Laden der Audiodatei: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            // Erstelle mehrere Songs und verknüpfe sie mit den Artists
            Song song1 = new Song();
            song1.setTitle("Song One");
            song1.setGenre(List.of(new String[]{"Pop"}));
            song1.setLength(210);  // Länge in Sekunden
            song1.setArtist(artist1);
            song1.setMusicData(defaultMusicData); // Verknüpft mit Artist One
            songRepository.save(song1);

            Song song2 = new Song();
            song2.setTitle("Song Two");
            song2.setGenre(List.of(new String[]{"classic", "lala"}));
            song2.setLength(180);
            song2.setArtist(artist1);
            song2.setMusicData(defaultMusicData); // Verknüpft mit Artist One
            songRepository.save(song2);

            Song song3 = new Song();
            song3.setTitle("Song Three");
            song3.setGenre(List.of(new String[]{"jazz", "lala"}));
            song3.setLength(240);
            song3.setArtist(artist2);
            song3.setMusicData(defaultMusicData); // Verknüpft mit Artist Two
            songRepository.save(song3);

            Song song4 = new Song();
            song4.setTitle("Song Four");
            song4.setGenre(List.of(new String[]{"töte mich", "lala"}));
            song4.setLength(300);
            song4.setArtist(artist2);
            song4.setMusicData(defaultMusicData); // Verknüpft mit Artist Two
            songRepository.save(song4);

            Song song5 = new Song();
            song5.setTitle("Song Five");
            song5.setGenre(List.of(new String[]{"kilmeeeeeeeeeeeeee ", "lala"}));
            song5.setLength(150);
            song5.setArtist(artist3);
            song5.setMusicData(defaultMusicData); // Verknüpft mit Artist Three
            songRepository.save(song5);

            Song song6 = new Song();
            song6.setTitle("Song Six");
            song6.setGenre(List.of(new String[]{"noooo", "lala"}));
            song6.setLength(220);
            song6.setArtist(artist3);
            song6.setMusicData(defaultMusicData); // Verknüpft mit Artist Three
            songRepository.save(song6);

            // Testbenutzer erstellen und hinzufügen
            Benutzer benutzer1 = new Benutzer();
            benutzer1.setUsername("hugo");
            benutzer1.setRole("admin");
            // Passwort verschlüsseln
            benutzer1.setPassword(passwordEncoder.encode("securepassword"));
            benutzerRepository.save(benutzer1);

            Benutzer benutzer2 = new Benutzer();
            benutzer2.setUsername("emma");
            benutzer2.setRole("admin");
            // Passwort verschlüsseln
            benutzer2.setPassword(passwordEncoder.encode("anotherpassword"));
            benutzerRepository.save(benutzer2);

            System.out.println("Testdaten wurden erfolgreich in die Datenbank geladen!");
        };
    }
}
