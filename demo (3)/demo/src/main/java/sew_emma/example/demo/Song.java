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
    //What are Collections?
    //
    //A collection is a framework that provides an architecture to store and manipulate groups of objects.
    //In Java, collections include Lists, Sets, and Maps.
    //List: An ordered collection, allows duplicates (e.g., a list of songs).
    //Set: A collection that does not allow duplicates.
    //Map: A collection of key-value pairs, like a dictionary.
    //How to use Collections within Spring Boot?
    //
    //In Spring Data JPA, collections are used to map one-to-many or many-to-many relationships between entities. For example, a User might have many Orders, or an Artist might have many Songs.
    //Collections are often used to represent the "many" side of a relationship, while the "one" side is typically represented by a single entity.
    //Applying the Use of Collections to a Field:
    //
    //When you create entities, you can use collections to represent relationships between them. Here's how you can apply collections to a field within your Spring Boot applicatio
    @ElementCollection
    //1. @ElementCollection
    //Die @ElementCollection Annotation wird verwendet, um eine einfache Sammlung von Werten zu speichern, die keine eigenständige Entität sind (z. B. eine Liste von Strings, Integern, etc.). Wenn du eine Sammlung von einfachen Werten in einer Entität speichern möchtest, kannst du @ElementCollection verwenden.
    //
    //In deinem Fall speicherst du eine Liste von String-Werten (also Genres) in einer Tabelle, die diese Sammlung von Werten repräsentiert.
    @CollectionTable(name="song_genres",joinColumns = @JoinColumn(name="song_id"))
    @Column(name="genre")
    private List<String> genres =new ArrayList<>(); //
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
