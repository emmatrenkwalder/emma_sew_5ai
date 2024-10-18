package sew_emma.example.demo;
// Diese Klasse repräsentiert einen Künstler.
// Ein Künstler kann mehrere Lieder haben (One-to-Many Beziehung).
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

// In einer One-to-Many-Beziehung hat ein Datensatz in der Künstler-Tabelle (Artist) mehrere Datensätze in der Lied-Tabelle (Song). Das bedeutet, dass ein Künstler mehrere Songs haben kann.
@Entity // Annotation markiert die Klasse als JPA-Entity, die einer Datenbanktabelle zugeordnet wird
public class Artist {
    @Id //    @Id  // Markiert dieses Feld als Primärschlüssel der Entity

    @GeneratedValue(strategy = GenerationType.IDENTITY)//// Gibt an, dass die ID automatisch von der Datenbank generiert wird

    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;

    public Artist() {}

    public Artist(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
