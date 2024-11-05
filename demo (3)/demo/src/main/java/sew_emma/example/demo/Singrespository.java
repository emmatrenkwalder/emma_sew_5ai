package sew_emma.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//Zweck: Kennzeichnet eine Klasse als Data Access Object (DAO) und ermöglicht die Verwaltung von Datenzugriffsoperationen.
@Repository
public interface Singrespository extends JpaRepository<Song, Long> {
    Page<Song> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    //PagingAndSortingRepository: Dieses Interface wird erweitert, um Zugriff auf grundlegende CRUD-Operationen sowie auf Paginierungs- und Sortierfunktionen zu erhalten.
    //findByTitleContainingIgnoreCase: Diese Methode sucht nach Songs, deren Titel einen bestimmten String enthält, unabhängig von Groß- oder Kleinschreibung, und gibt die Ergebnisse paginiert zurück. Der Pageable-Parameter ermöglicht es, die gewünschte Seite und die Größe der Seite zu bestimmen.
//Page<Song>: Der Rückgabewert ist vom Typ Page, was bedeutet, dass die Methode paginierte Ergebnisse zurückgibt.
    @Query("SELECT s FROM Song s")
    List<SongData> findAllProjectedBy();

    // Beispiel mit einer Suchabfrage:
    @Query("SELECT s FROM Song s WHERE s.title LIKE %:query%")
    List<SongData> searchSongs(@Param("query") String query);
}
//package sew_emma.example.demo;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
////Zweck: Kennzeichnet eine Klasse als Data Access Object (DAO) und ermöglicht die Verwaltung von Datenzugriffsoperationen.
//@Repository
//public interface Singrespository extends JpaRepository<Song, Long> {
//    List<Song> findByTitleContainingIgnoreCase(String title);
//    List<Song> findByArtistContainingIgnoreCase(String artist);
//}
//Sie können Methodenparameter verwenden, die in den Methodennamen verwendet werden, um Filterbedingungen anzugeben.
//Beispiel: findByTitleContainingIgnoreCase(String title) sucht nach Titeln, die das angegebene Wort enthalten (Groß- und Kleinschreibung ignorierend).
