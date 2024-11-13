# Bærekraftig utvikling

## Energieffektive programmeringsspråk

Java et relativt godt programmeringsspråk, og i et bærekraftsperspektiv er det lurest å fortsette med dette språket. Grunnen til dette er at videre utvikling av funksjonalitet er generelt lavt når det kommer til energiforbruk. Siden vi nå benytter oss av videre utvikling med Java, men også en React frontend som tar i bruk Typescript, er vi utsatt med tanke på at Typescript er mer kostbart en java. Fremover kan man ta en vurdering om man vil fortsette med Typescript, gå videre med kun Java, eller jobbe med web-applikasjonen med Javascript. Et motargument er at Typescript bidrar til en god bærekraftig utvikling gjennom god kodekvalitet sjekker, ved hjelp av Typescript:strict, eslint og prettier. Gjennom disse verktøyene som er tilgjengelig med Typescript oppfordrer det til god standard og kvalitet. Noe som igjen kan føre til god bærekraft gjennom forutsigbarhet, gode feilmeldinger og god dokumentasjon.

## Rest API

En god bærekraftig løsning som tilstreber minimalt med unødvendig kall frem og tilbake mellom backend og frontend. Vår integrasjon har til gode å optimalisere og komme med flere gode bærekraftige løsninger rundt dette aspektet. Frem til nå er hver endring på kanvaset vårt et kall til backenden. I React klienten er det en debounce mekanisme som sørger for at det må gå minst 5 sekunder mellom hver PUT request. Fremover kunne vi hatt en redigerings modus. Denne modusen gjør at man kan gjøre endringer og heller trykke på en knapp når du ønsker å oppdatere kanvaset.

## Optimalisering

Optimalisering er en kontinuerlig prosess i utviklingen av applikasjonen vår, og vi har lagt vekt på å forbedre både hastighet og ressursbruk. Vi har fokusert på hvordan applikasjonen håndterer store mengder data og utfører operasjoner på disse. Vi har allerede redusert unødvendige beregninger, men for fremtiden ser vi på muligheten for å implementere algoritmer som er mer effektive, både i tid og i energiforbruk. En annen forbedring kan være å vurdere muligheten for å bruke caching på kritiske områder, noe som kan redusere belastningen på serverne og forbedre responstiden.

## Nettverkseffektivitet

Nettverkseffektivitet er et nøkkelområde for bærekraft, spesielt når applikasjonen vår krever kommunikasjon mellom frontend og backend. Vi har allerede tatt skritt for å minimere unødvendige API-kall, men videre arbeid med å redusere datamengden som sendes mellom klient og server er viktig.

## Komprimering av data

Komprimering av data er et annet tiltak vi har vurdert for å forbedre applikasjonens bærekraft. For eksempel kan gjentagende piksler med samme verdi representeres som mye mindre data. 

### Kunstig intelligens

Vi har brukt kunstig intelligens i utviklingen, men ukritisk bruk kan det forsinke prosessen. Vi har definert tre regler: dokumentasjon, forståelse og nødvendighet. Alle må dokumentere bruk av AI-generert kode, slik at formålet er tydelig. Koden må også forstås før bruk, da AI-generert kode ofte krever gjennomgang og forståelse for å sikre reproduserbarhet. Med ytterlig bruk av kildehenvisning kan man lette arbeidet til fremtidige utviklere som skal jobbe på prosjektet.

## Mørk-bakgrunn

Vi har også tatt en titt på konseptet «dark-mode». Vi har jobbet oss litt inn i et hjørne med en tegne app, som forutsetter at vi har et hvitt kanvas. En løsning vi kom frem til er å ha en mørk bakgrunn rundt selve kanvaset vårt. Dette er ikke en funksjon som man kan skru av og på, men heller en standard integrasjon som gjør at en blir tvunget til å ha en mørk bakgrunn på applikasjonen.

[Tilbake til innholdsfortegnelsen](README.md)
