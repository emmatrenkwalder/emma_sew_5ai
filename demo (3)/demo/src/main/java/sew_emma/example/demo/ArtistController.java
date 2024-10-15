package sew_emma.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/artists")

public class ArtistController {

    private final Atristrepository artistRepository;

    public ArtistController(Atristrepository artistRepository) {
        this.artistRepository = artistRepository;
    }
// CRUD -> CREATE READ UPDATEE DELETE
    @GetMapping()
    public List<Artist> getAllArtists() {
        return artistRepository.findAll(); // alle aus der datenbank
    }
//create neuen
    @PostMapping()
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        Artist savedArtist = artistRepository.save(artist); // speichert in datenbank
        return ResponseEntity.status(201).body(savedArtist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id, @RequestBody Artist artistDetails) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        artist.setName(artistDetails.getName());
        Artist updatedArtist = artistRepository.save(artist);
        return ResponseEntity.ok(updatedArtist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        if (!artistRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        artistRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
