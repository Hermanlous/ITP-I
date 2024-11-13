![Logo](img/PixelArtLogo.png)

# PixelArt

Åpne i [Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2024/gr2452/gr2452?new).

Sjekk ut [nettsiden her](https://pixelart-5awn.onrender.com/). 
**NB: Det kan ta veldig lang tid for siden å laste, les hvorfor [her](#deployment)**

PixelArt er en enkel tegne-app for pixelkunst som lar deg velge mellom ulike farger og tegne på et pikselbasert kanvas. Appen er designet for å være fri for forstyrrende elementer, og tilbyr en plattform der kreative sjeler kan uttrykke seg gjennom pixelkunst.

## Innhold
1. [Funksjoner](#funksjoner)
2. [Teknologier](#teknologier)
3. [Komme i gang](#komme-i-gang)
4. [Java Backend & JavaFX Client](#java)
   - [Build](#java-build)
   - [Run](#java-run)
   - [Testing](#java-testing)
   - [Test Coverage](#java-test-coverage)
   - [Code Quality](#java-code-quality)
5. [React Frontend](#react)
   - [Build](#react-build)
   - [Run](#react-run)
   - [Testing](#react-testing)
   - [Test Coverage](#react-test-coverage)
   - [Code Quality](#react-code-quality)
6. [Kode Evaluering](#kode-evaluering)
7. [Git Standard](#git-standard)
8. [API Reference](#api-reference)
9. [Deployment](#deployment)
10. [Utviklere](#utviklere)

## Funksjoner
- Benytt deg av flere fine av farger
- Lukk appen og åpne appen når det behager, uten at kunstverket ditt forsvinner.
- Benytt deg av to ulike klienter, som JavaFX app og webapplikasjon.

## Teknologier

### Java Stack
- Java 17
- JavaFX 17.0.8
- Spring-boot 3.2.11
- Maven 3.11.0
- TestFX 4.0.16-alpha
- JUNIT 5.10
- Mockito 5.14.1
- Jacoco 3.1.2
- Checkstyle 3.5.0
- Spotbugs 4.8.6

### React Stack
- Node Package Manager 10.8.3
- Typescript 5.6.3
- React 18.3.1
- Tailwind 3.4.14
- Vite 5.4.8
- Vitest 2.1.4
- Jest 29.7.0
- eslint 9.12.0
- prettier 3.3.3
- Typestrict:strict

## Komme i gang
Klon repoet og naviger til prosjektet:
```bash
git clone git@gitlab.stud.idi.ntnu.no:it1901/groups-2024/gr2452/gr2452.git
cd gr2452
```

## Java

### Java Maven Build

```bash
cd pixelart
mvn clean install
```

### Java Run

#### Spring Boot Server

```bash
cd pixelart/server
mvn spring-boot:run
```
For å lukke, trykk `ctrl` + `c`, deretter trykk `y` + `enter`.
**Viktig: Stopp server når du skal kjøre tester i maven, eller kjører `mvn clean install`!**

#### JavaFX Client

```bash
cd pixelart/ui
mvn javafx:run
```

### Java Testing


```bash
cd pixelart
mvn test
```

#### Java Test Coverage

For å se testdekningsgrad etter å ha kjørt `mvn test` eller `mvn clean install`:
1. Åpne target-mappen i modulen som testes.
2. Åpne site/jacoco mappen.
3. Åpne `index.html` i en nettleser.

##### Core
I core har vi lagt vekt på å teste både vellykkede og mislykkede situasjoner på backenden. Vi har tatt i bruk Mock for å simulere API-kall og dermed testet lagring og henting av kanvaser til filen. I tillegg har vi testet at både Grid og Pixel initialiseres korrekt.

##### UI
I UI har vi lagt vekt på å teste den ulike funksjonaliteten som brukeren interagerer med når de bruker applikasjonen vår. Vi har derfor testet både åpning av applikasjonen, simulering av klikk på kanvaset, lagring av kanvas, samt oppretting av knapper for å velge farge.

##### Server
I Server har vi lagt vekt på å teste de uike situasjonene som kan oppstå som følge av api-kall. For å best kunne sikre kvalitet har vi testet situasjon uten eksisterende fil, med korrupt JSON, samt sukessfulle situasjoner.

### Java Code Quality

#### Checkstyle

```bash
cd pixelart
mvn checkstyle:check
```

Vi har 1 stående checkstyle violation. Den lar vi stå, for at appen skal fungere.

#### Spotbugs

```bash
cd pixelart
mvn spotbugs:spotbugs
mvn spotbugs:gui
```

## React

### React Build


```bash
cd react/pixelart
npm i
```

### React Run


```bash
cd react/pixelart
npm run dev
```
For å lukke, trykk `ctrl` + `c`, deretter trykk `y` + `enter`.

### React Testing


```bash
cd react/pixelart
npm run test
npm run coverage
```

### React Test Coverage

Etter å ha kjørt `npm run coverage`:
- Du vil få en rapport i terminal
- En coverage-mappe blir generert under react/pixelart/ med en index.html fil. Åpne denne filen i nettleser for mer informasjon.

#### Components
I components har vi hatt hovedfokus på å rendre komponenter riktig. Her har det vært spesielt viktig at de riktige elementene blir visualisert og mocket på en realistisk og anvendelig måte. Vi tester likevel endring i kanvaset, gjennom fireEvents.

I kanvaset så vi ikke nødvendigheten bak å teste at musen forlater der den var. Derfor har Canvas.tsx, relativt lav testdekningsgrad.

#### Pages
Her er det kun App.tsx som er en side, denne testfilen er størst for her er det mest å teste. Igjen mocker vi API og tester hovedsakelig rendering. Her simulerer vi klikk på kanvaset og andre endringer samt om at ting blir korrekt renderet.

#### Hooks
Hooks blir mocket gjennom andre komponenter. Hovedsakelig App.tsx

### React Code Quality


```bash
cd react/pixelart
npm i
npx eslint .
```

I terminalen vil det oppstå en rapport på potensielle "error" og "problems". Her har vi fire, og vi konkluderte at disse ikke hadde stor innvirkning på prosjektet.

Vi benytter oss av Typescript strict med eslint, som gir oss en svært streng kodesjekk. Dette gir oss god oversikt og høy kodekvalitet.

## Kode Evaluering
Kode evalueringen til gruppen bestod av flere punkter. Vi hadde alle et ansvar ovenfor hverandre og gå over kode som hadde blitt produsert fra sprint til sprint, og gjennomgang av kode for hver "pull-request".

*Verifisering:*
Dette går ut på at en forstår funksjonen til koden og at den utfyller ønsket utfall. På denne måten kan alle på gruppen knytte videre utvikling til gammel kode uten at det blir redundans og misforståelser.

*Leselighet:* Koden skal være leselig, og skal ikke fremstå som ukjent for noen. Her er det viktig at man benytter seg av anvendelige konvensjoner, og er tydelig dersom deler av koden er vanskelig å forstå seg på.

*Testbarhet:* Koden skal være testbar. Koden skal være utformet slik at koden vil være mulig å teste etter beste evne.

*Skalerbarhet:* Koden skal være skalerbar, dette vil si at koden skal kunne anvendes til fremtidig utvikling uten å måtte skrive om store deler. Koden som da skrives om, skal være skalerbar.

*Bærekraft:* Koden skal være bærekraftig. Dette refererer for eksempel til rest-kall, redundans og ytelse.

Punktene over er utført etter beste evne, likevel er punktene fremdeles relevante i videre utvikling av prosjektet.


## Git Standard
*Development:* Vi bestemte oss for å lage en "Development-branch". Denne branchen er det nærmeste vi kommer fullført arbeid rett etter "master-branch", som skal være ferdig produkt. I "Development-branch" er her vi forgreiner oss fra, ettersom at "Development" er forgreinet fra "master". "Development-branch" er også her vi sammenskjører alt før den eventuelle mergingen tilbake igjen i "master".

Dette gjør at vi alltid har en fungerende "master-branch", og vi kan jobbe og sammenkjøre i "Development".

*Issues:* Lager vi ut ifra brukerhistoriene våre. Her tar vi utgangspunkt i flere funksjonalitetsområder og beskrivende navn og deskripsjon.

*Labels:* Benytte vi oss både på "merge-requests" og i "issues", dette er merkelapper som hjelper oss å legge et ekstra lag av identifikasjon på utviklingsoppgavene våre.

*Branches:* Branches knytter vi til "issues", og benytter oss av funksjonalitetsområder, som "testing, feature, enhancement, docs, chore, fix, bug". Dette hjelper oss å holde styr på branches, gjøre de riktige endringene på riktig branches. På denne måten blir det ikke noe rot.

*Commits:* Dette er meldinger som beskriver endringene vi har gjort. Vi har satt et krav om at de skal være informative og beskrivende, men ikke overveldende. Commit meldingene knytter vi til "issues" og gjøres i henhold til riktig branch.

Vi har også bestemt oss for å squashe commits, og heller ha en besrkivende commit som sier alt en har gjort. Vi opplevde denne metoden svært givende og strukterert.

*Merge- request:* Dette erfarte vi som en god standard. Ved å benytte oss av merge requests, fikk flere parter en viktigere del i hverandres arbeidsoppgaver, vi fikk anvend kode evaluering og ga gruppen et ekstra lag av sikkerhet.

## API Reference

### Spring-boot RestAPI

RestAPI'et når du kjører [server](#java-run) er satt opp på `http//localhost:8080/canvas`

```http
GET localhost:8080/canvas
```

```http
PUT localhost:8080/canvas
```

Canvas elementet som returneres er en todimensjonal array som ser slik ut.
JSON eksempel initialisert med kun fargen hvit:
```
[
    [ "#FFFFFF", "#FFFFFF", "#FFFFFF", ... ],
    [ "#FFFFFF", "#FFFFFF", "#FFFFFF", ... ],
    ...
]
```
See further [documentation](/pixelart/docs/release3/REST.md) on the API

## Deployment

For hosting på web har vi brukt [Render](https://render.com/).

**NB: Begge tjenestene kjører på Render sin gratis plan og 
ved inaktivitet stoppens instansene, noe som kan føre til tap av kanvas data og forsinke forespørsler med 50 sekunder eller mer.**

Deployment er inspirert av [denne guiden](https://hostingtutorials.dev/blog/free-spring-boot-host-with-render).

#### Frontend
- React-frontend: [pixelart-5awn.onrender.com](https://pixelart-5awn.onrender.com/).
- Docker image: [hub.docker.com/r/johannesaas/pixelart-frontend](https://hub.docker.com/r/johannesaas/pixelart-frontend)

#### Backend
- Sprin Boot server: [pixelart-server.onrender.com](https://pixelart-server.onrender.com/canvas).
- Docker image: [hub.docker.com/r/johannesaas/pixelart-server](https://hub.docker.com/r/johannesaas/pixelart-server)

## Utviklere

- August Solli Middelkoop @augustm
- Axel Herman Rørholt Lous @ahlous
- Johannes Hansen Aas @johhaa
- Oline Koolen Bjerketvedt @olinekb

![Logo](img/PixelArtLogoLong.png)