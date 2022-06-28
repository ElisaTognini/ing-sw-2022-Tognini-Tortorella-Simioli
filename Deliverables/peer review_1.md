# Peer-Review 1: UML

Alberto Sandri, Alberto Nidasio, Matteo Pignataro

Gruppo GC09

Valutazione del diagramma UML delle classi del gruppo GC19.

## Lati positivi

L'implementazione di uno Strategy pattern per la classe `Board` permette di inizializzare lo stato del gioco in maniera differente per le varie tipologie di partite, rendendo possibile una futura implementazione di differenti scenari iniziali.

L'aggregazione di più isole è stata organizzata tramite una classe `MergedIslands`. Se meglio sviluppata permette una migliore distinzione, gestione delle isole e dei loro raggruppamenti rispetto a fondere due `Island` in una sola. In questo modo è possibile tenere traccia delle differenti tessere così da rendere più semplice anche la gestione della view.

Nell'implementazione delle carte carattere, l'utilizzo del pattern Decorator può essere una buona soluzione per modificare il comportamento del gioco. Se applicato direttamente sulla classe `Board`, sarebbe possibile ridefinire i metodi necessari alla carta per applicare il suo effetto.

## Lati negativi

Osservando il metodo `chooseCloud(List<Cloud>)` della classe Player, ci siamo accorti che potrebbe esserci un'idea sbagliata della dinamica di gioco rispetto al pattern Model View Controller distribuito. Sembra infatti che questo metodo debba contattare il Client per proporgli la scelta della `Cloud`, attendendo quindi la risposta. In questo modo viene meno il concetto di Client/Server in cui dev'essere il Server in attesa di un'azione dal Client, e non viceversa. La stessa osservazione può essere fatta rispetto al metodo `chooseAssistantCard()`.

L'enumerazione `PlayerNumber` che, a quanto sembra, indica il numero di giocatori della partita, non dovrebbe essere un attributo della classe `Player` ma dovrebbe appartenere a `Board`, visto che viene implementato il pattern `Strategy` per distinguere le modalità di gioco.

Il metodo `moveMotherNature(AssistantCard)`, all'interno della classe `Board`, dovrebbe avere come parametro il numero di passi per cui muovere madre natura perché il giocatore potrebbe spostarla di un numero inferiore rispetto al massimo indicato sulla carta assistente giocata.

Errori di battitura:
- La classe Game è definita come `abstract`;
- Nella classe Island ci si riferisce alla classe `Tower` ma non è definita;
- La classe `Cloud` utilizzata in `Player` dovrebbe riferirsi alla classe `CloudTile`;
- `StudentContainer` viene utilizzato in `DiningRoom` potrebbe essere collegata da una freccia;
- La classe `DiningRoomExpert` dovrebbe ereditare da `DiningRoom` invece che viceversa;

## Confronto tra le architetture

Rispetto alle classi `Board` e `Game`, e rispetto al pattern `Strategy`, è interessante la possibilità di definire diverse modalità di gioco. Nel nostro modello abbiamo una sola classe `Game` che racchiude le informazioni di tutta la Board. Le diverse modalità di gioco vengono distinte all'interno di `Game` tramite il numero di giocatori inseriti, il quale viene letto dai diversi metodi della classe per variare il loro effetto.

E' interessante la suddivisione della `SchoolBoard` in diverse classi, noi abbiamo optato per singole liste all'interno della plancia così da aggregare i componenti direttamente in un'unica classe.