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

```
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
