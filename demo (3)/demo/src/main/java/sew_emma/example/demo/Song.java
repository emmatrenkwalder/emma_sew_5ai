package sew_emma.example.demo;

import jakarta.persistence.*;
import sew_emma.example.demo.Artist;

@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private Integer length;

    // Diese Klasse repräsentiert ein Lied.
// Jedes Lied hat einen Titel, ein Genre, eine Länge und gehört zu einem Künstler (Many-to-One Beziehung).
    @ManyToOne //@ManyToOne: Zeigt an, dass jeder Song genau einem Artist zugeordnet is
    private Artist artist; // Diese Annotation zeigt eine Viele-zu-Eins-Beziehung zur Artist-Entity an n:1
    //In einer Many-to-One-Beziehung können viele Datensätze in einer Tabelle (oder Entität) mit einem einzelnen Datensatz in einer anderen Tabelle verknüpft werden. Dies bedeutet, dass ein Datensatz in der „vielen“ Tabelle auf einen Datensatz in der „einzelnen“ Tabelle verweist.

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
