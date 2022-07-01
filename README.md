# ERIANTYS
![](https://img.dungeondice.it/49229-large_default/eriantys.jpg)

## The Project

This project brings Eriantys, a children board game by Cranio Creations, to life as a Java multiplayer videogame. 

It was developed by a team of three people, Elisa Tognini, Mara Tortorella and Emilia Simioli, as a Java 17 project with Maven Integration.

The game works on Windows, MacOS, and Linux (Although GUI visualization is not optimal in this OS).

## Overview 

Eriantys is based on a client-server architecture, where multiple clients can connect to the same server and, after the socket has been created, play concurrent matches. 

The server is able to handle client disconnecting throughout the game, but it does not allow reconnections. If a player disconnects, the server notifies the other players and ends the match, therefore you need to start another match to play again. 

After running the jars, the user can choose to play the game thorugh a command line interface or through a graphic user interface, implemented with JavaFX.

## How to play
### Server

The server can be launched using the executable jar Server.jar and writing the following command in the prompt (note: follow the *java -jar* instruction with the Server.jar file path.)

```bash
java -jar Server.jar
```


### Client

The Client can be launched using the executable jar Client.jar. (note: as noted for the server, attention needs to be paid to the Client.jar file path.)

```bash
java -jar Client.jar
```

After the client being launched, the user is asked to choose which interface (GUI or CLI) they want to play with and then they have to insert the IP address that the server is currently running on.

## Implemented Functionalities

| Functionality  |   |
|---|---|
| Complete rules  | ✅	  |
| Socket  | ✅  |
| GUI  | ✅    |
|  CLI |  ✅ |
|  Multiple games | ✅  |
|  12 Character Cards | ✅  |

## Tests

Project was tested using JUnit unit tests. Running all written tests with coverage results in 82% coverage for Controller package, and 96% coverage for pack Model, as required in projet requirements. 

## In-Game notes

To properly understand this part of the readme, basic knowledge of Eriantys game rules is required.

#### basic rules playing aid

To move students, simply click on a student in your entrance and then click either on an island or on your dining room. To pick a cloud, simply click on it. 

After moving three students during a player's turn, mother nature will move on its own and conquered or merged islands will become visible after picking a cloud tile.

After picking a cloud tile, the turn of the current player ends and either the next turn or a new round starts.

#### expert rules playing aid
To choose a character card, click on it. 

Any character card that modifies influence or Mother Nature's path will NOT have a visible effect if chosen after moving the third student of the turn; as per game rules, Mother Nature moves after three students have been removed from a player's entrance. After that, influence on the island MN lands on is automatically calculated, therefore certain character cards will not modify the outcome if chosen after influence evaluation has already happened. 

When playing using the graphic interface, the player will be requested to take some actions depending on the character card(s) they choose during the game:   
- **card 1, card 9, card 11, card 12**: a new stage will appear displaying all five colors. You only need to click on one, then press the "done" button.
- **card 2**: a new stage will appear displaying all five professors. Click on all the professors you would like to control, then click on "done".
- **card 3, card 5, card 1**: a new stage will appear displaying all the islands: click on the one you choose, then click on done.
- **card 4**: a new stage will appear: click on 1 or 2 to choose the number of additional moves.
- **card 7**: first stage is for students on card: click up to three. Second stage is for students in your entrance; once again, click up to three.
- **card 10**: first stage is for choosing students in entrance: click up to two then press "done". Second stage is for students in your dining room; again, choose up to two students. 
If playing using CLI, all instructions will be shown on screen.





