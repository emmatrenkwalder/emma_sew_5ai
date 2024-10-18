package sew_emma.example.demo;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin (origins = "http://localhost:8081")//, allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final Singrespository songRepository;
    private final Atristrepository artistRepository;
    public SongController(Singrespository songRepository, Atristrepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository=artistRepository;
    }

    @GetMapping
    public Page<Song> getSongs(Pageable pageable) { // paginierung
        return songRepository.findAll(pageable); // Pageable pageable: Diese Methode gibt ein Page<Song>-Objekt zurück, das die abgerufenen Songs sowie Informationen über die Gesamtzahl der Seiten und die aktuelle Seite enthält.
// seite von songs
     //   Dies ist ein Parameter, der von Spring automatisch gefüllt wird. Der Pageable-Parameter enthält Informationen darüber, welche Seite von Daten der Client anfordert und wie viele Datensätze pro Seite zurückgegeben werden sollen.
        //        Du kannst die Parameter in der Anfrage definieren, z. B. page (die Seite, die du anforderst) und size (die Anzahl der Elemente pro Seite). Spring Data JPA interpretiert diese Parameter und erstellt ein Pageable-Objekt.
    }

    @PostMapping
    public ResponseEntity<Song> createSong(@Valid @RequestBody Song song) {
        // Überprüfe, ob der Artist existiert
        if (song.getArtist() == null || !artistRepository.existsById(song.getArtist().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Bad Request, wenn der Artist nicht existiert
        }

        // Lade den Artist aus der Datenbank, um sicherzustellen, dass er persistiert ist
        Artist artist = artistRepository.findById(song.getArtist().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));

        // Setze den geladenen Artist im Song
        song.setArtist(artist);

        // Speichere den Song
        Song savedSong = songRepository.save(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSong);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id,@Valid @RequestBody Song updatedSong) {
        if (!songRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        updatedSong.setId(id);// setzt id
        Song savedSong = songRepository.save(updatedSong); // speichert
        return ResponseEntity.ok(savedSong); // gibt aktualisierten aus
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        if (!songRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        songRepository.deleteById(id); // löscht anhand der id
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Song>> searchSongs(@RequestParam("query") String query) {
        List<Song> songs = songRepository.searchSongs(query);
        return ResponseEntity.ok(songs);
    }
}