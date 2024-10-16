package sew_emma.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Loaddatabase {

    @Bean
    CommandLineRunner initDatabase(Singrespository songRepository, Atristrepository artistRepository) {
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

            // Erstelle mehrere Songs und verknüpfe sie mit den Artists
            Song song1 = new Song();
            song1.setTitle("Song One");
            song1.setGenre("Pop");
            song1.setLength(210);  // Länge in Sekunden
            song1.setArtist(artist1);  // Verknüpft mit Artist One
            songRepository.save(song1);

            Song song2 = new Song();
            song2.setTitle("Song Two");
            song2.setGenre("Rock");
            song2.setLength(180);
            song2.setArtist(artist1);  // Verknüpft mit Artist One
            songRepository.save(song2);

            Song song3 = new Song();
            song3.setTitle("Song Three");
            song3.setGenre("Jazz");
            song3.setLength(240);
            song3.setArtist(artist2);  // Verknüpft mit Artist Two
            songRepository.save(song3);

            Song song4 = new Song();
            song4.setTitle("Song Four");
            song4.setGenre("Classical");
            song4.setLength(300);
            song4.setArtist(artist2);  // Verknüpft mit Artist Two
            songRepository.save(song4);

            Song song5 = new Song();
            song5.setTitle("Song Five");
            song5.setGenre("Hip Hop");
            song5.setLength(150);
            song5.setArtist(artist3);  // Verknüpft mit Artist Three
            songRepository.save(song5);

            Song song6 = new Song();
            song6.setTitle("Song Six");
            song6.setGenre("Electronic");
            song6.setLength(220);
            song6.setArtist(artist3);  // Verknüpft mit Artist Three
            songRepository.save(song6);

            System.out.println("Testdaten wurden erfolgreich in die Datenbank geladen!");
        };
    }
}
