# PixelArt
Åpne i Eclipse Che [her](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2024/gr2452/gr2452?new). 

## Om appen
Vi har laget en pixel-kunst app. Vi ønsket å lage en simpel tegne app som ikke har forstyrrende elementer. Vi ønsker å lage en plattform for kreative sjeler som har lyst til å bidra med pixelkunst. Vi har derfor laget en enkel app som oppfyller disse kravene.

[Brukerhistorier](Brukerhistorier.md)
[Bruksanvisning](pixelart/docs/Bruksanvisning.md)

## Oppsett og kjøring
Prosjektet kjøres med Maven. 

### 1. Bygge
Navigere til prosjektet `cd pixelart`
Bygg prosjektet `mvn install`

### 2. Kjøre
Navigere til ui-mappen `cd ui`
Start appen `mvn javafx:run:app`

## Funksjon
I GridManager i core-modulen ligger hovedlogikken til appen. Brukergrensesnittet ligger i UI modulen. Vi bruker javafx med canvas, som initialiserer et 100x100 grid.
Hvis brukeren trykker venstreklikk på et pixel tegner hen, og med høyreklikk kan hen viske. Foreløpig har vi svart og hvit, som gjør at brukeren kan tegne og viske. For hver pixel endring oppdateres en json fil som inneholeder data om lerretet.

## Vår første utviklingsoppgave 
Vi tar utgangspunkt i brukerhistorie 1 og 2.
Vi har konstruert en enkel tegneapp

## Versioner
- Javafx 17.0.8
- Java 11
- Maven compiler 17

## Utgivelser
[Release 1](pixelart/docs/release1/)
[Release 2](pixelart/docs/release2/)

## Brukerhistorier
Her kan lese om brukerhistorier som støtter utviklingsarbeidet:
[Brukerhistorier](Brukerhistorier.md)

## Bruksanvisning
Her kan du lese dokumentasjon om hvordan du bruker appen.
[Bruksanvisning](Bruksanvisning.md)
