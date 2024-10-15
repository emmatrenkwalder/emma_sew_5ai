package sew_emma.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//Annotations in Java sind Meta-Informationen, die Klassen, Methoden oder Variablen zusätzliche
// Hinweise geben, ohne den eigentlichen Code zu verändern. Vordefinierte Annotations wie @Override,
// @Deprecated oder @SuppressWarnings kannst du in deinen Java-Klassen verwenden, um Compiler und Entwickler zu unterstützen.
//
//Ein Build-Tool in Java automatisiert Aufgaben wie das Kompilieren und Verwalten von Abhängigkeiten.
// Bekannte Tools sind Maven und Gradle. Du kannst ein neues Projekt erstellen und es bauen, indem du entsprechende
// Konfigurationsdateien (pom.xml für Maven oder build.gradle für Gradle) nutzt und Befehle wie mvn install oder
// gradle build ausführst.
//
//Spring Boot ist ein Framework, das die Entwicklung von Java-Anwendungen vereinfacht,
// indem es standardisierte Konfigurationen bietet und die Integration von Spring-Komponenten erleichtert.
// MVC steht für Model-View-Controller und wird in Spring Boot durch Controller (für die Logik), Models (für die Daten)
// und Views (für die Darstellung) umgesetzt. Du kannst mit Spring Boot entwickeln und debuggen, indem du Debugging-Tools
// und Log-Ausgaben nutzt. Ein neues Projekt lässt sich über den Spring Boot Initializer starten, und der Entwicklungsserver kann mit mvn spring-boot:run oder gradle bootRun ausgeführt werden.
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
//Ein Build-Tool ist ein Programm, das den Prozess der Erstellung und Verwaltung von Softwareprojekten automatisiert.
//Maven
//
//Beschreibung: Maven ist ein weit verbreitetes Build-Tool für Java-Projekte, das auf XML-basierten Konfigurationsdateien (pom.xml) basiert. Es bietet eine standardisierte Struktur für Projekte und verwaltet Abhängigkeiten, Builds und Deployment-Prozesse.
//Website: Apache Maven
//Maven verwendet XML-Konfigurationsdateien (pom.xml) und ist weit verbreitet für Java-Projekte.


//1. Was bedeutet REST?
//REST steht für Representational State Transfer. Es ist ein Architekturstil für die Entwicklung von web-basierten APIs. REST verwendet HTTP-Methoden und Ressourcen, die durch URLs identifiziert werden, um Interaktionen zwischen Clients und Servern zu ermöglichen. Die Kernprinzipien von REST sind:
//
//Ressourcenbasiert: Jede URL repräsentiert eine Ressource (z.B. Benutzer, Artikel).
//Verwendung von HTTP-Methoden: GET (Abrufen), POST (Erstellen), PUT (Aktualisieren), DELETE (Löschen).
//Stateless: Jede Anfrage vom Client an den Server muss alle notwendigen Informationen enthalten, da der Server keine Sitzung speichert.
//Cacheable: Antworten können so gestaltet werden, dass sie vom Client oder von Zwischenstationen (Caches) gespeichert werden können.

//HATEOAS steht für Hypermedia As The Engine Of Application State. Es ist ein Konzept innerhalb der REST-Architektur, das besagt, dass die API nicht nur Daten zurückgibt, sondern auch Informationen darüber enthält, welche weiteren Aktionen auf den Ressourcen durchgeführt werden können. HATEOAS ermöglicht es Clients, durch Hyperlinks, die in den API-Antworten enthalten sind, durch die Anwendung zu navigieren.
//
//Struktur von HATEOAS:
//
//Links: Die API-Antworten enthalten Links zu verwandten Ressourcen oder zu Aktionen, die der Client als nächstes ausführen kann.
//Dynamische Entdeckung: Clients entdecken verfügbare Operationen und Ressourcen dynamisch, indem sie die Links in den API-Antworten folgen.


//Spring Boot erleichtert die Entwicklung von Spring-Anwendungen durch automatische Konfiguration und vordefinierte Standards.
//MVC steht für Model-View-Controller und wird in Spring Boot durch Controller, Model-Klassen und View-Technologien umgesetzt.
//Entwicklung und Debugging werden durch Tools und IDEs unterstützt, die Spring Boot-spezifische Funktionen und Log-Ausgaben bieten.
//Projektinstallation erfolgt über den Spring Boot Initializer, und der Entwicklungsserver kann durch Maven-Befehle oder direkt aus der IDE gestartet werden.

//2. Erstelle das Datenbank-Schema
//Spring Boot und Hibernate können das Schema basierend auf deinen JPA-Entitäten automatisch erstellen und verwalten.
// Dazu verwendest du die Annotationen von JPA, um deine Datenbankstruktur zu definieren.