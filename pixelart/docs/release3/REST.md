# RestAPI

Vi benytter oss av Spring-boot som RestAPI leverandør. Vi har brukt:
- Spring-boot versjon 3.2.11
- Spring-boot initializer: [start.spring.io](https://start.spring.io/)

Gjennom Spring-boot kontrolleren vår har vi tatt for oss @CrossOrigin som tillater oss å laste inn ressurser fra andre tjenester enn seg selv. @RequestMapping, @GetMapping og @PutMapping. @RequestMapping gir oss muligheten til å «mappe» forespørsel fra kontrolleren vår. Forespørslene våre kommer i formen av @GetMapping og @PutMapping, her kunne vi godt ha brukt @PostMapping, men vi følte at @PutMapping oppdaterer kanvaset fullstendig slik vi ønsket. @GetMapping henter kanvaset vårt som en todimensjonal liste, slik var det tilgjengelig og godt tilrettelagt for redigering.
React med typescript:

### GET method
```http
GET localhost:8080/canvas
```
Dersom man sender en GET-forespørsel vil man få returnert dataen som ligger i jsonCanvas.json filen.
Dataen vil bli deserialisert av Spring Boot til en matrise av strenger, hvor hver enkelt streng er en pixel, og hvert array er en rad i matrisen.

Eksempel på returnert hvitt lerret:
```
[
    [ "#FFFFFF", "#FFFFFF", "#FFFFFF", ... ],
    [ "#FFFFFF", "#FFFFFF", "#FFFFFF", ... ],
    ...
]
```

### PUT method
```http
PUT localhost:8080/canvas
```
Sender man en PUT-forespørsel med et kanvas som body, vil det (forsøke) å oppdatere kanvaset i json-filen med oppdaterte verdier for pikslene. De oppdaterte dataene er basert på fargene som er brukt. Dersom forespørselen går gjennom vil verdien `true` returneres.

Eksempel på PUT body med farger:
```
[
    [ "#FF0000", "#0000FF", "#FFFF00", ... ],
    [ "#00FF00", "#FF00FF", "#00FFFF", ... ],
    ...
]
```

## Java klient
I Java har vi laget en GridStateHandler som håndterer lasting fra- og putting til REST-apiet. Når applikasjonen startes opp blir det gjort et forsøk på å hente informasjon fra Rest-apiet, gjennom GridStateHandler som setter opp riktig HTTP klient. Dataen kommer som et array av strenger som gjør det enkelt for oss å konvertere til et kanvas. Med funksjonen pixelClick i GridController kalles funksjonen `postCanvas` som kaller postCanvas i GridStateHandler. Dette kaller en PUT-funksjon som sender hele kanvaset til REST-apiet.

### GET-forespørsel i Java
I java har vi en GridStateHandler som håndterer alle GET-forespørsler. Dersom man sender en GET forespørsel, vil GridStateHandler iterere over dataen og initialisere et nytt Grid-objekt basert på denne dataen. Dette forsøkes hver gang applikasjonen initialiseres. Dersom det ikke evnes å laste et kanvas, initialiseres et nytt, hvitt kanvas.

### PUT-forespørsel i Java
I Java håndterer GridStateHandler alle PUT-forespørsler som sendes. Dersom en PUT-forespørsel sendes, vil denne iterere over kanvaset som sendes, og konvertere dette til en JSON-streng. Deretter danner den en HttpRequest med kanvaset, som så sendes med en PUT-funksjon. Dersom forespørselen gikk gjennom får du verdien `true` 

## React klient

I React delen av prosjektet har vi laget et utility script api.ts som utfører API kall for henting og oppdatering av kanvas dataene. Videre har vi laget to React hooks, `useCanvasData` og useCanvasDataUpdater som bruker utility scriptet. 

### GET-forespørsler i React
Når siden laster inn initialiseres `App.tsx` som bruker `useCanvasData` hooket til å hente canvas data og deretter rendrer Canvas komponenten og sender med dataene som props. 

### PUT-forespørsler i React
Når pixler endres oppdateres kanvasdatene gjennom `handlepixelclick` konstanten som så bruker `useCanvasDataUpdater` hooket til å oppdatere kanvaset. Dette hooket har en debounce mekanisme som sørger for at det må gå minst 5 sekunder før den kaller apiet på nytt. 

[Tilbake til innholdsfortegnelsen](README.md)
