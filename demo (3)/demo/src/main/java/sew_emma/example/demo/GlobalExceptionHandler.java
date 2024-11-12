package sew_emma.example.demo;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation exceptions (e.g., @NotNull, @Size, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Handle optimistic locking failures (409 Conflict)
    @ExceptionHandler(OptimisticEntityLockException.class)
    public ResponseEntity<Map<String, String>> handleOptimisticLockingFailure(OptimisticEntityLockException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Datenkonflikt: Die Daten wurden inzwischen geändert. Bitte laden Sie die Seite neu.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // Handle other general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralExceptions(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ein unerwarteter Fehler ist aufgetreten. Bitte versuche es erneut.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}


//@NotNull: Stellt sicher, dass das annotierte Feld nicht null ist.
//@Size(min = x, max = y): Überprüft, ob die Länge des Strings innerhalb eines bestimmten Bereichs liegt.
//@Min(value) und @Max(value): Stellt sicher, dass numerische Werte innerhalb festgelegter Grenzen liegen.
//@Email: Überprüft, ob ein String im gültigen E-Mail-Format vorliegt.
//@Pattern(regexp = "regex"): Validiert den String anhand eines angegebenen regulären Ausdrucks.
//@Future / @Past: Überprüft Daten, um sicherzustellen, dass sie in der Zukunft oder in der Vergangenheit liegen.

// mit Message ->  @NotBlank(message = "Name cannot be empty")

// Gruppen @NotNull(groups = CreateGroup.class)
//private String name;
//
//@Size(min = 2, groups = UpdateGroup.class)


//@ExceptionHandler(MethodArgumentNotValidException.class)
//public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//    Map<String, String> errors = new HashMap<>();
//    ex.getBindingResult().getFieldErrors().forEach(error -> {
//        errors.put(error.getField(), error.getDefaultMessage());
//    });
//    return ResponseEntity.badRequest().body(errors);
//}
//1. Zentrale Verwaltung von Ausnahmen
//Mit einem globalen Ausnahmehandler kannst du Ausnahmen, die in deiner gesamten Anwendung auftreten, an einem einzigen Ort verwalten. Dies bedeutet, dass du nicht für jeden Controller individuelle Ausnahmebehandlungslogik schreiben musst. Das vereinfacht den Code und fördert die Wiederverwendbarkeit.
//
//2. Einheitliche Fehlerantworten
//Ein globaler Handler ermöglicht es dir, konsistente Fehlermeldungen und -antworten für verschiedene Arten von Ausnahmen zu definieren. Dadurch kannst du sicherstellen, dass der Client immer in einem einheitlichen Format eine Rückmeldung erhält, unabhängig davon, wo der Fehler auftritt.
//
//3. Trennung von Anliegen
//Durch die Verwendung eines globalen Ausnahmehandlers kannst du die Fehlerbehandlung von der Geschäftslogik deiner Controller trennen. Dies führt zu saubererem, übersichtlicherem Code und erleichtert das Testen und die Wartung.
//
//4. Unterstützung für mehrere Ausnahmetypen
//Mit einem einzigen Handler kannst du mehrere Ausnahmetypen behandeln, indem du unterschiedliche Methoden für verschiedene Ausnahmearten definierst. Das ermöglicht eine differenzierte Fehlerbehandlung, je nach Kontext der Ausnahme.
//
//5. Anpassbare Fehlermeldungen
//Du kannst die Fehlermeldungen, die an den Client zurückgegeben werden, anpassen, um nützliche Informationen zu liefern, z. B. spezifische Fehlermeldungen, Statuscodes und weitere relevante Daten.