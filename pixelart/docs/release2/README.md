# Release 2

## Endringer
- Vi delte opp prosjektet i to moduler, én for kjernekoden (logikk og serialisering) og en for brukergrensesnittet.
- Lagt til filoppdatering (JSON matrise). Pixel-grid lagres som json i en matrise hvor hvert pixel endten er "W" eller "B" fom forteller om pixelet er svart eller hvitt.
- Vi har forbedret tester.


## Arkitektur
Arkitekturen har to muduler, core og ui. I core modulen er kjerneklassen GridManager og i ui modulen er brukergrensesnittet.

### Core
I core er kjerneloggikken for lesing, oppdatering og lagring av lerretet. Den tar seg av fillagring, og henter lagrede data ved oppstart slik at UI kan hente lerretet som er lagret. 

### UI
UI har frontendklassen som styrer fxml filen. Den bruker kjerneklassen GridManager fra core til å hente matrisen som forteller tilstanden til hvert pixel. Deretter lager den et pixel-grid som brukeren kan trykke på. Når et pixel oppdateres kaller den på GridManager i core til å oppdatere filen.

![Diagram](ClassDiagram.png)
PlantUML kode: se vedlegg A 

## Valg vi tatt

### Gitlab
Vi har prøvd å forbedre vår bruk a standard prosedyre. Vi har begynt å bruke standard commit melding oppsett. Med bruk av denne malen:

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```
Vi har som mål at alle commits som er synlige på main branchen følger denne malen.
Vi sletter overflødige grener. Vi har valgt å bruke squash commits for å slå sammen tidligere commits når vi merger til master.


## Bruk av KI
[Release 2](ai-tools.md)


### Vedlegg A

PlantUML code

```
@startuml

class pixelart.core.GridManager {
    -int gridSize = 100
    -int pixelSize = 10
    -Canvas[][] grid
    -JSONArray[] jsonGrid
    -String filepathJson = "jsonCanvas.json"
    
    +GridManager()
    -void initializeGrid()
    +Canvas[][] getGrid()
    +void updatePixel(int row, int col, boolean isBlack)
    +void loadState()
    -void saveJsonState()
    -String readJsonFile()
}

class pixelart.ui.App {
    +void start(Stage primaryStage)
}

class pixelart.ui.AppController {
    -GridPane gridPane
    -GridManager gridManager

    +void initialize()
    -void pixelClick(int row, int column, MouseEvent event)
}

class pixelart.server.JsonFileTests {
    +void testSaveJsonCanvasState()
    +void testLoadState()
}

pixelart.core.GridManager --> pixelart.ui.AppController : "uses"
pixelart.ui.AppController --> pixelart.core.GridManager : "manages"
pixelart.ui.App --> pixelart.ui.AppController : "manages"
pixelart.server.JsonFileTests --> pixelart.core.GridManager : "tests"

@enduml
```