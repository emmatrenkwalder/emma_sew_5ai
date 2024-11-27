package sew_emma.example.demo;

import java.util.List;

public interface SongData {
    Long getId();
    String getTitle();
   List< String > getGenre();
    Integer getLength();
    ArtistProjection getArtist();

}
// Daten effizient zu handhaben. Indem Sie nur die benötigten Daten abfragen
//In der Regel wird ein solches Interface in Verbindung mit einer Datenbank verwendet, häufig im Kontext von Java Persistence API (JPA) oder Spring Data JPA, um eine spezifische Projektion zu erstellen. Eine Projektion erlaubt es, nur die benötigten Daten zu laden, anstatt das gesamte Entity-Objekt.