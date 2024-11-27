package sew_emma.example.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sew_emma.example.demo.Artist;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title cannot be empty")
    private String title;
    //@NotBlank(message = "Genre cannot be empty")
    @ElementCollection
    @CollectionTable(name="song_genres",joinColumns = @JoinColumn(name="song_id"))
    @Column(name="genre")
    private List<String> genres =new ArrayList<>();
    @NotNull(message = "Length cannot be null")
    private Integer length;
    @Version //The @Version annotation in the Song entity (private Integer version;) ensures that when a record is fetched and updated, the version field is checked against the current version in the database:
   //- If the versions match, the update proceeds, and the version is incremented.
       //     - If there's a version mismatch (indicating another update has occurred), an OptimisticLockException is thrown.
    private Integer version;
    @NotNull(message = "Artist is mandatory")
    // Diese Klasse repräsentiert ein Lied.
// Jedes Lied hat einen Titel, ein Genre, eine Länge und gehört zu einem Künstler (Many-to-One Beziehung).
    @ManyToOne

    //@ManyToOne: Zeigt an, dass jeder Song genau einem Artist zugeordnet is
    private Artist artist; // Diese Annotation zeigt eine Viele-zu-Eins-Beziehung zur Artist-Entity an n:1
    //In einer Many-to-One-Beziehung können viele Datensätze in einer Tabelle (oder Entität) mit einem einzelnen Datensatz in einer anderen Tabelle verknüpft werden. Dies bedeutet, dass ein Datensatz in der „vielen“ Tabelle auf einen Datensatz in der „einzelnen“ Tabelle verweist.
    @Lob // Für große Daten, z.B. Musikdateien
    private String musicData;
    // Speichern der Musikdatei als Byte-Array
    // Standard constructor
    public Song() {
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<String> getGenre() {
        return genres;
    }

    public void setGenre(List<String>genre) {
        this.genres = genre;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getMusicData() {
        return musicData;
    }

    public void setMusicData(String musicData) {
        this.musicData = musicData;
    }
    // WAs war mein fehler dieses mal ?
   // ich hab bei den probe songs die ich fetche einf keine datei hinzugefügt also war es laut client nicht im richtigebnn format und hat einen corse fehler geworfen
}
