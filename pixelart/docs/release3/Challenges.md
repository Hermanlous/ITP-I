# Våre to største utfordringer

## Utfordring 1

### Feil filsti gir FileNotFound-error

Innlevering av øving 2 viste seg å bli mer utfordrende enn først antatt, da det oppsto en feil i funksjonaliteten for å lese fra- og skrive til JSON-filen vår kvelden vi skulle levere. Det viste seg at som følge av at vi hadde opprettet en egen modul for persistens hvor vi så plasserte json-filen, ble filstien vi tok i bruk feil. Vi fikk en FileNotFound-error, og vi satte oss ned og feilsøket forskjellene fra denne versjonen av koden, og koden som fungerte kun timer tidligere. Hovedforskjellen var at i den fungerende koden, brukte vi en absolutt filsti, noe vi innså ikke er god praksis, og dermed valgte å endre. Løsningen vi kom frem til var enkel og effektiv. Løsningen vi gikk for var å flytte filen tilbake til core-modulen hvor funksjonaliteten lå. Ved å gjøre dette fungerte det å bruke relativ filsti, som igjen førte til at funksjonaliteten fungerte som ønsket.
I retrospekt ville vi gjort en endring på løsningen vår. Da vi flyttet filen la vi den nemlig ikke i en mappe som het persistence, noe som kunne bidra til å skape forvirring om hva funksjonaliteten til json-filen var. Vår endring ville derfor ha vært å ha lagt filen i en mappe som het persistence, slik at det tydelig er markert hva filens funksjon er, i tillegg til å minimere antall “flytende” filer i kodebasen vår.

## Utfordring 2

### Java og React håndtere HEX koder forskjellig

Utbyggingen av ny React frontend og samtidig utvikle funksjonalitet på JavaFX viste seg å være svært motiverende, og ga grunnlaget til spennende sprinter frem mot innlevering. Problemet oppsto da React og Java hadde to ulike måter å håndtere farger og hexkoder på. Løsningen i seg selv var ikke komplisert, men det avlet frem et nytt problem, synkronisering. Synkroniseringen for hvordan man håndterte farger i React og JavaFX. Vi måtte håndtere data gjennom restAPIet på en god og effektiv måte. Gjennom agile utvikling fikk vi satt opp god struktur for en god løsning. Siden vi strevde mest med data henting fra Java, tilrettela vi Typescripten slik at Java ikke trengte å bekymre seg for om koden til Typescript ikke funket. Likevel ble det vanskelig å fullføre produktet fra sprint til sprint, og vi stagnerte ofte. Vi bestemte oss da for å slå sammen det som funket, og parprogrammerte jevnlig. På denne måte fikk vi flere øyne på problemet. Tilbakemeldings seanser hvor vi drøftet hva som funket, ikke funket og hva som måtte være hovedfokuset til neste sprint. Etter at vi fikk til Java-koden og henting og bytting av data fra restAPIet til å funke, var det mye enklere og tilrettelegge Typescript koden til å gjøre det samme. På denne måten utviklet vi programmet gradvis, slik at ingen aspekter lå langt foran et annet aspekt. Gjennom sprint-vis revurdering og hyppige møter fikk vi til det vi hadde sett for oss. Produktet er langt fra perfekt, men vi landet på ønsket utfall når det kom til oppgaven vår.

[Tilbake til innholdsfortegnelsen](README.md)
