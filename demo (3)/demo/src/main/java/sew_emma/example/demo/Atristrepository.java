package sew_emma.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
//Einführung in PagingAndSortingRepository
//PagingAndSortingRepository ist eine Schnittstelle, die von Spring Data JPA bereitgestellt wird. Sie erweitert die Funktionalität von CrudRepository und bietet zusätzliche Methoden zur Unterstützung von Paginierung und Sortierung von Datensätzen in einer Datenbank.
//
//Vorteile von Paginierung
//Leistungsoptimierung: Anstatt alle Datensätze auf einmal zu laden, ermöglicht die Paginierung das Laden von Datensätzen in kleinen, handhabbaren Gruppen (Seiten). Dies reduziert den Speicherverbrauch und die Ladezeiten, insbesondere bei großen Datensätzen.
//Verbesserte Benutzererfahrung: Bei der Anzeige von Daten in einer Benutzeroberfläche ist es oft besser, diese in Seiten anzuzeigen, um die Navigation zu erleichtern. Benutzer können durch die Seiten blättern, anstatt eine lange Liste von Elementen zu durchsuchen.
@Repository
public interface Atristrepository extends JpaRepository<Artist, Long> {
    @Query("SELECT s FROM Song s JOIN s.artist a")
    Page<Song> findSongsWithArtistNames(Pageable pageable);
}
//Bereitstellung von Methoden wie findAll(Pageable pageable) und findAll(Sort sort).
//wann?
//Bei großen Datensätzen ist es wichtig, Paginierung zu verwenden, um zu vermeiden, dass alle Datensätze auf einmal geladen werden. Dies kann den Speicherverbrauch erheblich reduzieren und die Leistung verbessern.
//Beispiel: Wenn du eine Musikbibliothek mit Tausenden von Songs hast, wäre es ineffizient, sie alle auf einmal zu laden.
//Paginierung trägt dazu bei, die Benutzererfahrung zu verbessern, indem sie eine handhabbarere Anzahl von Datensätzen auf einmal anzeigt.
//Benutzer können durch Seiten navigieren, anstatt durch eine lange Liste zu scrollen, was es einfacher macht, bestimmte Elemente zu finden.
//Bei der Erstellung von REST-APIs kann Paginierung die Leistung deiner Anwendung verbessern, indem nur ein Teil der Daten bei jeder Anfrage gesendet wird.
//Dies ist besonders nützlich, wenn der Client nicht alle Daten auf einmal benötigt.

//client pitfalls
//eistungsprobleme:
//
//Bei der clientseitigen Paginierung werden alle Daten auf der Client-Seite geladen und dann mithilfe von JavaScript in Seiten unterteilt. Dies kann zu Leistungsproblemen führen, insbesondere bei großen Datensätzen.
//Der Browser kann langsam oder unresponsive werden, wenn er mit zu vielen Daten auf einmal umgehen muss.
// Erhöhte Ladezeit:
//
//Da alle Daten auf einmal abgerufen werden müssen, können die Ladezeiten erheblich länger werden, was die Benutzer frustrieren kann.
//Benutzer müssen länger warten, um mit den Daten zu interagieren, was die Benutzererfahrung beeinträchtigt.
//3. Datenstörungen:
//
//Bei der clientseitigen Paginierung besteht das Risiko, veraltete Daten anzuzeigen, es sei denn, der gesamte Datensatz wird häufig aktualisiert.
//Änderungen an den Daten auf der Serverseite werden nicht angezeigt, bis der gesamte Datensatz neu geladen wird.
//4. Bandbreitennutzung:
//
//Die Übertragung eines großen Datenvolumens über das Netzwerk kann zu einem erhöhten Bandbreitenverbrauch führen, was in mobilen oder Bandbreiten-limitierten Szenarien problematisch sein kann.
