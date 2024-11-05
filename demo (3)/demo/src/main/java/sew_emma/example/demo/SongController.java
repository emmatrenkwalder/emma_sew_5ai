package sew_emma.example.demo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    public SongController(Singrespository songRepository, Atristrepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
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
    public ResponseEntity<Song> updateSong(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("artistId") Long artistId,
            @RequestParam("genre") String genre,
            @RequestParam("length") Integer length,
            @RequestParam(value = "musicFile", required = false) MultipartFile musicFile) throws IOException {

        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        song.setTitle(title);
        song.setGenre(genre);
        song.setLength(length);
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        song.setArtist(artist);

        if (musicFile != null && !musicFile.isEmpty()) {
            song.setMusicData(Base64.getEncoder().encodeToString(musicFile.getBytes())); // Encode new music data
        }

        Song updatedSong = songRepository.save(song);
        return ResponseEntity.ok(updatedSong);
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
    public ResponseEntity<List<SongData>> searchSongs(@RequestParam("query") String query) {
        List<SongData> songs = songRepository.searchSongs(query);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/projection")
    public ResponseEntity<List<SongData>> getAllProjectedSongs() {
        List<SongData> songs = songRepository.findAllProjectedBy();
        return ResponseEntity.ok(songs);
    }
    @GetMapping("/{id:\\d+}/data")
    public ResponseEntity<String> getSongData(@PathVariable Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(song.getMusicData());
    }
}


