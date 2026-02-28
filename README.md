# Vorbereitung für den Workshop
_Funktional-reaktives Event-Ticketing mit Mutiny, Quarkus und Containern_

## 1. Überblick

### 1.1 Ziel dieses Dokuments
### 1.2 Unterstützte Plattformen
- Windows 10/11
- macOS (Intel/Apple Silicon)
- Linux (Ubuntu/Fedora/... )

## 2. Java 25 LTS
Für den Workshop kommt Java 25 LTS zum Einsatz (JDK, nicht nur JRE). Bitte Java vor dem Workshop installieren und prüfen, dass `java` auf der Kommandozeile verfügbar ist.

### 2.1 Installation

Es wird ein JDK 25 (Long-Term Support) benötigt. Geeignete Distributionen sind z.B.:

- Oracle JDK 25
- Eclipse Temurin / Adoptium JDK 25
- Andere JDK‑Distributionen mit vollständiger Java‑SE‑Implementierung

#### 2.1.1 Windows 10/11

1. Einen JDK‑25‑Installer für Windows (x64 oder ARM, passend zum System) von der Website des Anbieters herunterladen.
2. Den Installer ausführen und dem Assistenten folgen.
3. Das Installationsverzeichnis merken, z.B.  
   `C:\Program Files\Java\jdk-25`.

#### 2.1.2 macOS (Intel und Apple Silicon)

1. Das JDK‑25‑Paket für macOS (Intel oder ARM) herunterladen.
2. Das `.pkg`‑Paket öffnen und dem Installer folgen.
3. Das JDK liegt üblicherweise unter `/Library/Java/JavaVirtualMachines/`.

#### 2.1.3 Linux

1. Entweder die Pakete der Distribution oder ein Tar‑Archiv vom JDK‑Anbieter verwenden.
2. Typische Varianten:
    - Paketmanager (z.B. `apt`, `dnf`, `pacman`)
    - Manuelle Installation unter `/opt` oder einem eigenen Pfad
3. Sicherstellen, dass das `bin`‑Verzeichnis des JDK im `PATH` liegt.

### 2.2 Konfiguration

Damit Build‑Tools und IDEs das richtige JDK finden, sollten `JAVA_HOME` und `PATH` korrekt gesetzt sein.

#### 2.2.1 Windows

1. Systemeigenschaften → „Erweiterte Systemeinstellungen“ → „Umgebungsvariablen“ öffnen.
2. Eine Systemvariable `JAVA_HOME` anlegen (falls noch nicht vorhanden) und auf das JDK‑Verzeichnis setzen, z.B.  
   `C:\Program Files\Java\jdk-25`.
3. Die Variable `Path` um  
   `%JAVA_HOME%\bin` erweitern.

#### 2.2.2 macOS / Linux (bash/zsh)

In der Shell‑Konfiguration (z.B. `~/.bashrc`, `~/.zshrc`) folgende Zeilen einfügen und den Pfad an das installierte JDK anpassen:

```sh
export JAVA_HOME="/pfad/zum/jdk-25"
export PATH="$JAVA_HOME/bin:$PATH" 
```

Anschließend die Konfigurationsdatei neu laden, z.B.:

```sh
source ~/.bashrc
# oder
source ~/.zshrc
```
### 2.3 Funktionsprüfung
Ein neues Terminal bzw. eine neue Eingabeaufforderung öffnen und prüfen:

`java -version`

Die Ausgabe sollte ungefähr so aussehen (Version und Build können abweichen):

```sh
java version "25" 20xx-xx-xx LTS
Java(TM) SE Runtime Environment (build 25.x.x+xx-LTS)
Java HotSpot(TM) 64-Bit Server VM (build 25.x.x+xx-LTS, mixed mode, sharing)
```

**Wichtig:**
Es sollte keine Fehlermeldung wie „Befehl nicht gefunden“ oder „'java' is not recognized...“ erscheinen.
Falls mehrere Java‑Versionen installiert sind, sicherstellen, dass Java 25 die Standard‑Version ist (z.B. durch Anpassen von JAVA_HOME und PATH).

## 3. Container-Runtime (Docker oder Podman/Rancher Desktop)

Für den Workshop kommt Quarkus im Dev-Modus zusammen mit Dev Services zum Einsatz.  
Dev Services starten benötigte Infrastruktur (z.B. Datenbanken) automatisch in Containern. Dafür wird eine lauffähige Container-Runtime wie Docker oder Podman/Rancher Desktop benötigt.

### 3.1 Wahl der Runtime

Du brauchst **genau eine** der folgenden Optionen:

- **Docker Desktop** (Windows, macOS)
- **Podman Desktop** (Windows, macOS, Linux)
- **Rancher Desktop** (Windows, macOS) mit aktivierter Docker-API-Kompatibilität

Empfehlung:

- Unter Windows/macOS ist Docker Desktop der einfachste Weg, sofern die Lizenzbedingungen passen.
- Wenn Docker-Desktop-Lizenz oder Unternehmensrichtlinien ein Thema sind, sind Podman Desktop oder Rancher Desktop gute Alternativen.

### 3.2 Installation

> Bitte die Installation und Grundkonfiguration **vor** dem Workshop erledigen, damit wir direkt mit dem Quarkus-Dev-Modus arbeiten können.

#### 3.2.1 Docker Desktop (Windows / macOS)

1. Download von der offiziellen Website:
    - https://www.docker.com/products/docker-desktop
2. Installer starten und den Anweisungen folgen.
3. Nach der Installation Docker Desktop starten und warten, bis der Daemon läuft  
   (das Docker-Icon sollte „running“ anzeigen).

Hinweis zur Lizenz:  
Docker Desktop ist für viele private/kleinere Szenarien kostenlos, für Unternehmen gelten bestimmte Lizenzbedingungen. Im Zweifelsfall bitte vorab mit der eigenen Organisation klären.

#### 3.2.2 Podman Desktop

Podman ist eine Docker-kompatible, daemonlose Container-Engine und kann von Quarkus Dev Services genutzt werden.

1. Podman Desktop herunterladen:
    - https://podman-desktop.io/downloads
2. Installer für das eigene Betriebssystem ausführen.
3. Podman Desktop starten und die geführte Einrichtung abschließen (inkl. „Podman Machine“ auf macOS/Windows, falls angeboten).

Wichtig:  
Bei der Installation auf macOS/Windows unbedingt den **Docker-Kompatibilitätsmodus** aktivieren, wenn Podman danach fragt. Dadurch wird u.a. der Docker-Socket (`/var/run/docker.sock`) bereitgestellt und die Nutzung mit Quarkus/Testcontainers erleichtert.[web:46][web:49]

#### 3.2.3 Rancher Desktop

Rancher Desktop stellt ebenfalls eine Docker-kompatible Container-Umgebung bereit.

1. Download:
    - https://rancherdesktop.io
2. Installer ausführen und Rancher Desktop starten.
3. In den Einstellungen als Container-Engine z.B. `nerdctl` mit Docker-API-Kompatibilität oder die Docker-Option wählen (je nach Version/GUI).
4. Sicherstellen, dass die Docker-CLI (`docker`) im PATH verfügbar ist oder dass Quarkus/Testcontainers über eine kompatible Schnittstelle zugreifen können.

### 3.3 Konfiguration für Quarkus Dev Services

Quarkus Dev Services nutzen in der Regel Testcontainers und erwarten eine funktionierende Docker-kompatible Umgebung.

#### 3.3.1 Docker

Mit Docker Desktop ist im Normalfall keine zusätzliche Konfiguration nötig:

- Docker Desktop muss laufen.
- `docker` muss im Terminal verfügbar sein.

Quarkus Dev Services erkennen die Docker-Umgebung automatisch, sobald ein Dev-Service-fähiges Extension (z.B. Datenbank) eingebunden ist und keine eigene externe Konfiguration angegeben wird.

#### 3.3.2 Podman Desktop

Damit Podman als Drop-in-Ersatz für Docker funktioniert, braucht es die Docker-API-Kompatibilität.

Auf macOS/Windows:

- In Podman Desktop bei der Installation oder in den Einstellungen den **Docker-Kompatibilitätsmodus** aktivieren.
- Sicherstellen, dass `podman machine` läuft (wird von Podman Desktop typischerweise automatisch gestartet).

Auf Linux:

- Podman Socket als User-Service aktivieren:
  ```sh
  systemctl --user enable podman.socket --now
  ```

Danach `DOCKER_HOST` so setzen, dass Docker-kompatible Clients (Testcontainers/Quarkus) den Podman-Socket verwenden:

`export DOCKER_HOST=unix://$(podman info --format '{{.Host.RemoteSocket.Path}}')`

Diese Zeile am besten in `~/.bashrc` oder `~/.zshrc` eintragen.

#### 3.3.3 Rancher Desktop
Für Rancher Desktop ist wichtig, dass eine Docker-kompatible CLI und API aktiv sind:

In den Einstellungen von Rancher Desktop:

- Docker-kompatiblen Modus auswählen (je nach Version z.B. „dockerd (moby)“ oder entsprechende Option).
- Sicherstellen, dass docker im Terminal verfügbar ist oder dass `DOCKER_HOST` auf die von Rancher bereitgestellte Socket-Adresse zeigt.

### 3.4 Funktionsprüfung
Vor dem Workshop sollte die Container-Umgebung einmal getestet werden.

#### 3.4.1 Version prüfen
Im Terminal:

`docker version`

oder, falls Podman als CLI verwendet wird:

`podman version`

Wenn eine Versionsausgabe erscheint und kein Fehler wie „command not found“ oder „Cannot connect to the Docker daemon“ angezeigt wird, ist die Basis in Ordnung.

#### 3.4.2 Test-Container starten
Als Schnelltest kann ein einfacher Container gestartet und wieder entfernt werden:

```sh 
docker run --rm hello-world
# oder
podman run --rm hello-world
```

Wenn der Container das Test-Message-Log ausgibt und sich danach beendet, funktioniert die Container-Runtime grundlegend.

Dieser Test sollte vor dem Workshop einmal durchgeführt werden.

## 4. Maven

Für den Workshop kommt ein Quarkus-Projekt mit Maven zum Einsatz.  
Im Projekt ist ein **Maven Wrapper** enthalten, sodass keine separate Maven-Installation zwingend nötig ist. Wer möchte, kann Maven zusätzlich global installieren.  
Das Quarkus-CLI ist **nicht** erforderlich, alle notwendigen Schritte laufen über Maven bzw. den Maven Wrapper.

Falls bereits eine lokale Maven-Installation vorhanden ist, kann diese selbstverständlich weiterverwendet werden, solange sie eine aktuelle 3.x-Version ist.

### 4.1 Maven Wrapper (empfohlen)

Der Maven Wrapper (`mvnw` / `mvnw.cmd`) liegt im Projektverzeichnis (nach dem Repository-Checkout) und lädt automatisch die passende Maven-Version herunter.

Im Projektordner:

```sh
./mvnw -version   # macOS / Linux
# oder
mvnw.cmd -version # Windows
```
Wenn eine Maven-Version ausgegeben wird (z.B. „Apache Maven 3.x.x“), funktioniert der Wrapper.

### 4.2 (Optional) Maven global installieren
Falls du Maven unabhängig vom Projekt verwenden möchtest:

#### 4.2.1 Download
Ein Apache Maven 3.x Archiv von der offiziellen Website herunterladen.

Archiv in ein Verzeichnis deiner Wahl entpacken, z.B.:

Windows: `C:\tools\apache-maven-3.x.x`

macOS/Linux: `/opt/apache-maven-3.x.x` oder `~/tools/apache-maven-3.x.x`

#### 4.2.2 Umgebungsvariablen setzen
Windows

Systemeigenschaften → „Erweiterte Systemeinstellungen“ → „Umgebungsvariablen“ öffnen.

Neue Systemvariable `MAVEN_HOME` anlegen und auf das Maven-Verzeichnis setzen, z.B.
`C:\tools\apache-maven-3.x.x`.

Path um folgenden Eintrag erweitern:
`%MAVEN_HOME%\bin`.

macOS / Linux (bash/zsh)

In `~/.bashrc` oder `~/.zshrc` ergänzen (Pfad anpassen):

```sh
export MAVEN_HOME="$HOME/tools/apache-maven-3.x.x"
export PATH="$MAVEN_HOME/bin:$PATH"
```

Konfiguration neu laden:

```sh
source ~/.bashrc
# oder
source ~/.zshrc
```

### 4.3 Funktionsprüfung
In einem neuen Terminal:

`mvn -version`
oder mit Wrapper im Projekt:

`./mvnw -version`
Die Ausgabe sollte eine Maven-Version anzeigen, z.B.:

```sh
Apache Maven 3.x.x (...)
Java version: 25, vendor: ...
```

Wenn eine Version erscheint und keine Fehlermeldung wie „command not found“, ist Maven einsatzbereit.

## 5. Visual Studio Code

Für den Workshop eignet sich Visual Studio Code als leichtgewichtige IDE mit gutem Java- und Quarkus-Support.

### 5.1 Installation von VS Code

1. VS Code von der offiziellen Website herunterladen:  
   https://code.visualstudio.com
2. Installer ausführen und den Anweisungen folgen.
3. VS Code nach der Installation einmal starten.

### 5.2 Notwendige Extensions

In VS Code den Extensions‑Bereich öffnen (Symbolleiste links, Icon mit den vier Quadraten) und folgende Erweiterungen installieren:

- **Extension Pack for Java**  
  Bietet Java-Sprache, Debugging, Testunterstützung und Projektverwaltung.
- **Quarkus Tools for Visual Studio Code**  
  Unterstützt Quarkus-spezifische Features (z.B. `application.properties`, Dev-Mode-Integration).
- (Optional) **REST Client** oder **Thunder Client**  
  Praktisch zum Testen der HTTP-APIs direkt aus VS Code heraus.

Nach der Installation VS Code ggf. neu starten, damit alle Funktionen aktiv sind.

### 5.3 JDK in VS Code auswählen

VS Code sollte das installierte Java 25 JDK automatisch erkennen. Falls mehrere JDKs vorhanden sind, lohnt sich ein kurzer Blick in die Java-Einstellungen:

1. In VS Code `Strg+Shift+P` (bzw. `Cmd+Shift+P` auf macOS) öffnen.
2. Nach „Java: Configure Java Runtime“ suchen und ausführen.
3. Prüfen, ob für Projekte das JDK 25 ausgewählt ist; falls nötig dort umstellen.

Damit ist sichergestellt, dass sowohl Build als auch Dev-Modus auf Java 25 laufen.

### 5.4 Kurzer Check mit dem Workshop-Projekt

Sobald das Workshop-Repository geklont ist:

1. VS Code öffnen.
2. Über „File → Open Folder…“ (oder „Ordner öffnen…“) das Workshop-Projekt auswählen.
3. Im integrierten Terminal von VS Code (`` Ctrl+` ``) ins Projektverzeichnis wechseln, falls noch nicht dort.
4. Dev-Modus starten:

```sh
./mvnw quarkus:dev   # macOS / Linux
# oder
mvnw.cmd quarkus:dev # Windows
```

## 6. Git und Workshop-Repository

Für den Workshop wird ein Git-Repository verwendet, das den Beispielcode und die Übungen enthält.

### 6.1 Git installieren

Falls Git noch nicht installiert ist:

- Windows:
   - Git for Windows unter https://git-scm.com/downloads herunterladen und installieren.
- macOS:
   - Entweder Xcode Command Line Tools (`xcode-select --install`) oder Git von https://git-scm.com installieren.
- Linux:
   - Über den Paketmanager, z.B.:
     ```sh
     sudo apt install git        # Debian/Ubuntu
     sudo dnf install git        # Fedora
     sudo pacman -S git          # Arch
     ```

### 6.2 Workshop-Repository klonen

1. Ein Verzeichnis für den Workshop auswählen, z.B. `~/workshops/bob-2026`.
2. Im Terminal in dieses Verzeichnis wechseln.
3. Das Repository klonen:

```sh
git clone https://github.com/htiemeyer/bob2026.git
cd bob2026
```
### 6.3 Erster Start im Dev-Modus
Um Build-Dependencies und Container-Images vorab zu laden, lohnt es sich, das Projekt mindestens einmal zu bauen und den Dev-Modus zu starten:

```sh
./mvnw compile
./mvnw quarkus:dev   # macOS / Linux
# oder
mvnw.cmd quarkus:dev # Windows
```
Wenn der Dev-Modus gestartet ist, kann die Dev UI im Browser geöffnet werden:

http://localhost:8080/q/dev

Anschließend den Dev-Modus mit Ctrl+C wieder stoppen.

# Checkliste: Bin ich bereit für den Workshop?

Diese Punkte sollten vor Workshop-Beginn erfüllt sein:

- [ ] **Java 25 LTS** ist installiert  
  → `java -version` zeigt eine Java-25-LTS-Version an.

- [ ] **Container-Runtime** ist installiert und läuft  
  → `docker version` oder `podman version` funktioniert ohne Fehlermeldung.  
  → Ein Test-Container wie `docker run --rm hello-world` läuft durch.

- [ ] **Maven** ist verfügbar  
  → Entweder `mvn -version` oder im Projektordner `./mvnw -version` zeigt eine Maven-3.x-Version an.

- [ ] **Visual Studio Code** ist installiert  
  → „Extension Pack for Java“ und „Quarkus Tools for Visual Studio Code“ sind eingerichtet.  
  → Das JDK 25 ist in VS Code als Java-Laufzeit ausgewählt.

- [ ] **Git** ist installiert  
  → `git --version` zeigt eine Version an.

- [ ] **Workshop-Repository** ist geklont  
  → Projektordner lokal vorhanden.

- [ ] **Quarkus Dev-Modus** läuft  
  → Im Projektordner lässt sich `./mvnw quarkus:dev` (bzw. `mvnw.cmd quarkus:dev`) starten.  
  → Die Dev UI ist unter `http://localhost:8080/q/dev` im Browser erreichbar.
