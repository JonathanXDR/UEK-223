# Coworking Space

Dieses Projekt ist ein System für Buchungen eines Coworking Spaces, welches mit Quarkus entwickelt wird.

## Datenbank

Die Daten werden in einer PostgreSQL-Datenbank gespeichert. In der Entwicklungsumgebung wird diese
in der [docker-compose-yml](./.devcontainer/docker-compose.yml) konfiguriert.

### Datenbankadministration

Über <http://localhost:5050> ist PgAdmin4 erreichbar. Damit lässt sich die Datenbank komfortabel verwalten.
Der Benutzername lautet `zli@example.com` und das Passwort `zli*123`. Die Verbindung zur PostgreSQL-Datenbank
muss zuerst mit folgenden Daten konfiguriert werden:

- Host name/address: `db`
- Port: `5432`
- Maintenance database: `postgres`
- Username: `postgres`
- Password: `postgres`

## Automatische Tests

Die automatischen Tests können mit `./mvnw quarkus:test` ausgeführt werden. Für die automatischen Tests
wird nicht die PostgreSQL-Datenbank verwendet, sondern eine H2-Datenbank, welche sich im Arbeitsspeicher
während der Ausführung befindet.

## Entwicklungsumgebung

Die Entwicklungsumgebung ist in [Development Containern](https://containers.dev/) organisiert.

### Einrichtung

Die Development Container können auf zwei Wege gestartet werden:

- Mit der Visual Studio Code Extension [Dev Containers](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers).
Die nötigen Dienste starten automatisch.
- Mit Docker Compose, die Datei ist in `.devcontainer/docker-compose.yml` zu finden

### Starten

Gestartet werden kann das Projekt mit der Quarkus Visual Studio Code Extension über den
Befehl `Quarkus: Debug current Quarkus project`.

Alternativ kann das Projekt auch direkt über Maven gestartet werden.

```bash
./mvnw quarkus:dev
```

### Ports

Die folgenden Ports sind in der Entwicklungsumgebung verfügbar:

Webservice: <http://localhost:8080>  
PgAdmin: <http://localhost:5050>  
Swagger UI: <http://localhost:8080/swagger/>

### Testdaten

Es ist sind zwei Testdatensets definiert, welche automatisch für die jeweilige Umgebung geladen werden.

TestDataService.java: `src/main/java/ch/zli/m223/service/TestDataService.java` TestDataServiceTest.java: `src/test/java/ch/zli/m223/service/TestDataServiceTest.java`

## Abweichungen der Planung

1. Bei jeder Route wurde der HTTP-Statuscode 403 (Forbidden) hinzugefügt, falls die erforderlichen Berechtigungen nicht vorhanden sind. Dies ist in der Planung vergessen gegangen.

2. Der Rückgabewert der Post & Put-Methoden wurde von `String` auf den entprechenden Entity geändert, damit man die erstellte/aktualisierte Entity direkt zurückbekommt.
