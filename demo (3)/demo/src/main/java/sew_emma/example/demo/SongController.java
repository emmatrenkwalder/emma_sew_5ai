package sew_emma.example.demo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sew_emma.example.demo.Atristrepository;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
@CrossOrigin (origins = "http://localhost:8081")//, allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final Singrespository songRepository;
    private final Atristrepository artistRepository;
    private final BenutzerRepository benutzerRepository;
    //private final BCryptPasswordEncoder passwordEncoder;


    public SongController(Singrespository songRepository, Atristrepository artistRepository,BenutzerRepository benutzerRepository) { //BCryptPasswordEncoder passwordEncoder
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.benutzerRepository=benutzerRepository;
       // this.passwordEncoder=passwordEncoder;
    }
// UPLOAD

    @PostMapping("/upload")
    public ResponseEntity<Song> uploadSong(@RequestParam("file") MultipartFile file,
                                           @RequestParam("title") String title,
                                           @RequestParam("artistId") Long artistId) {
        // Überprüfe, ob die Datei leer ist
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Überprüfe, ob der Künstler existiert
        if (artistId == null || !artistRepository.existsById(artistId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            // Überprüfe den MIME-Typ
            String contentType = file.getContentType();
            if (!contentType.startsWith("audio/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
            }

            // Speichern als Base64-kodierten String
            byte[] musicDataBytes = file.getBytes();
            String musicData = Base64.getEncoder().encodeToString(musicDataBytes);

            Song song = new Song();
            song.setTitle(title);
            song.setMusicData(musicData); // Setze die Musikdaten als Base64-String
            song.setArtist(artistRepository.findById(artistId)
                    .orElseThrow(() -> new ResourceNotFoundException("Artist not found")));
            song.setMusicData(musicData);
            Song savedSong = songRepository.save(song);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSong);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping
    public ResponseEntity<Page<Song>> getAllSongs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Song> songs = songRepository.findAll(pageable);
        return ResponseEntity.ok(songs);
    }   

    // Andere Methoden ...


    @PostMapping
    public ResponseEntity<Song> createSong(
            @RequestParam("title") String title,
            @RequestParam("genre") String genre,
            @RequestParam("length") Integer length,
            @RequestParam("artistId") Long artistId,
            @RequestParam("musicFile") MultipartFile musicFile) {

        // Check if the artist exists
        if (artistId == null || !artistRepository.existsById(artistId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Load the artist from the database
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));

        // Create a new Song object
        Song song = new Song();
        song.setTitle(title);
        song.setGenre(genre);
        song.setLength(length);
        song.setArtist(artist);

        // Save the file as a byte array
        try {
            song.setMusicData(Base64.getEncoder().encodeToString(musicFile.getBytes())); // Encode to Base64
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Save the song
        Song savedSong = songRepository.save(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSong);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSong(
            @PathVariable Long id,
            @RequestHeader(value = "If-Match", required = false) String ifMatch, // Optional If-Match header
            @RequestParam("title") String title,
            @RequestParam("artistId") Long artistId,
            @RequestParam("genre") String genre,
            @RequestParam("length") Integer length,
            @RequestParam(value = "musicFile", required = false) MultipartFile musicFile) throws IOException {

        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        // Generate current ETag for comparison
        String currentETag = Integer.toString(song.hashCode());

        // If If-Match header is present and doesn't match the current ETag, return 412 status
        //Konkurrierende (oder parallele) Updates treten auf, wenn mehrere Clients gleichzeitig versuchen, dieselbe Ressource (z. B. eine Datenbankzeile oder ein Objekt) zu aktualisieren
        if (ifMatch != null && !ifMatch.equals(currentETag)) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body("Data has been modified by another user. Please reload and try again.");
        }

        // Proceed with original update functionality falls übereinstimmen
        song.setTitle(title);
        song.setGenre(genre);
        song.setLength(length);

        // Update artist if necessary
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        song.setArtist(artist);

        // Update music data if a new file is provided
        if (musicFile != null && !musicFile.isEmpty()) {
            song.setMusicData(Base64.getEncoder().encodeToString(musicFile.getBytes()));
        }

        Song updatedSong = songRepository.save(song);

        // generiert neuen etag und schickt in antwort zurück
        String updatedETag = Integer.toString(updatedSong.hashCode());
        return ResponseEntity.ok()
                .eTag(updatedETag)
                .body(updatedSong);
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
    public ResponseEntity<Page<SongData>> searchSongs(@RequestParam("query") String query) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<SongData> songs = songRepository.findByTitleContainingIgnoreCase(query, pageable);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/projection")
    public ResponseEntity<List<SongData>> getAllProjectedSongs() {
        List<SongData> songs = songRepository.findAllProjectedBy();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id:\\d+}/data")
    public ResponseEntity<?> getSongData(@PathVariable Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));

       // E TAG generieren
        String eTag = Integer.toString(song.hashCode()); // Keeps it simple

        // Return song with ETag im header
        // ETag eindeutiger wert der zustand des liedes zeigt basierend auf hashcode
      //  Ein ETag (Entity Tag) ist ein Header in HTTP, der eine eindeutige Kennzeichnung einer bestimmten Version einer Ressource darstellt.
        //enn eine Ressource geändert wird, ändert sich auch ihr ETag. Das ermöglicht es dem Server und dem Client zu erkennen, ob eine Ressource seit dem letzten Abruf geändert wurde.
        return ResponseEntity.ok()
                .eTag(eTag)
                .body(song);
    }
    // Ein **ETag** (Entity Tag) ist ein Header in HTTP, der eine eindeutige Kennzeichnung einer bestimmten Version einer Ressource darstellt. Er wird verwendet, um den Zustand einer Ressource zu verfolgen und sicherzustellen, dass Clients nur die aktuellste Version bearbeiten. Wenn eine Ressource geändert wird, ändert sich auch ihr ETag. Das ermöglicht es dem Server und dem Client zu erkennen, ob eine Ressource seit dem letzten Abruf geändert wurde.
    //
    //Der **If-Match-Header** wird in HTTP-Requests verwendet, um die Konsistenz bei Updates sicherzustellen. Ein Client sendet den ETag der Version, die er ändern möchte, im `If-Match`-Header. Der Server prüft dann, ob der ETag der aktuellen Version entspricht. Wenn die ETags übereinstimmen, wird das Update durchgeführt. Falls die Ressource jedoch zwischenzeitlich von einem anderen Client geändert wurde (und der ETag daher nicht mehr stimmt), lehnt der Server das Update mit dem Statuscode `412 Precondition Failed` ab. Dies verhindert, dass parallele Updates Änderungen überschreiben.
}


