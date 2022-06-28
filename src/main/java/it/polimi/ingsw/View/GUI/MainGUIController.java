package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.ActionMessages.*;
import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Utils.Enums.TowerColor;
import it.polimi.ingsw.Utils.NetMessages.BaseServerMessage;
import it.polimi.ingsw.Utils.NetMessages.PlayedCardMessage;
import it.polimi.ingsw.Utils.NetMessages.TurnChangeMessage;
import it.polimi.ingsw.View.GUI.Components.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Class MainGUIController represents the main GUI controller. It is used to handle the main scene of the game,
 * therefore to handle matches.
 *
 * @see Observable
 * */
public class MainGUIController extends Observable {

    @FXML private AnchorPane anchorPane;
    @FXML private HBox cloudHBox;
    @FXML private ImageView mainPlayerSB;
    @FXML private GridPane deckHolder;
    @FXML private GridPane entranceGridPane;
    @FXML private GridPane towersGridPane;
    @FXML private GridPane professorGridPane;
    @FXML private GridPane greenStudentsDRGridPane;
    @FXML private GridPane blueStudentsDRGridPane;
    @FXML private GridPane pinkStudentsDRGridPane;
    @FXML private GridPane yellowStudentsDRGridPane;
    @FXML private GridPane redStudentsDRGridPane;
    @FXML private Label myNickLabel;
    @FXML private HBox opponentSBHbox;
    @FXML private VBox playedCardsVBox;
    @FXML private HBox expertCardsHBox;
    @FXML private Label coinLabel;
    @FXML private ImageView coinImg;
    @FXML private Rectangle DRsurface;
    @FXML private TextArea expertCardTextArea;


    static ArrayList<IslandViewComponent> islandList = new ArrayList<>();
    ArrayList<ImageView> islandsImgs = new ArrayList<>();
    ArrayList<GridPane> cloudGrids = new ArrayList<>();
    ArrayList<StackPane> expertCardStackPanes = new ArrayList<>();
    HashMap<String, TowerColor> towerColors = new HashMap<>();

    static Stage cardStage = new Stage();

    private static PawnDiscColor color;
    private static String studentSource;

    private ArrayList<GridPane> studentGrids;

    static int chosenCard = 0;
    static Parameter param;

    /**
     * Method drawIslands is used to show the islands on the main game scene. This method redraws the islands, so that
     * two or more islands become one in case of merge, and adds mother nature on one of them, specified by
     * mnPosition parameter.
     * A click on an island allows the player (if permitted) to select it as the destination to move a student to.
     *
     * @param islands of type ArrayList - islands to draw.
     * @param mnPosition of type int - mother nature position.
     * */
    public void drawIslands(ArrayList<String> islands, int mnPosition){

        int distance = 300;

        anchorPane.getChildren().removeAll(islandList);
        anchorPane.getChildren().removeAll(islandsImgs);

        islandsImgs.clear();
        islandList.clear();

        for(int i = 0; i < islands.size(); i++){

            String[] s = islands.get(i).split(" ");

            double angle = 2 * i * Math.PI / islands.size();
            double xOffset = distance * Math.cos(angle);
            double yOffset = distance * Math.sin(angle);
            double x = xOffset + 120 + anchorPane.getWidth()/2;
            double y = yOffset - 80 + anchorPane.getHeight()/2;
            IslandViewComponent island = new IslandViewComponent(Integer.valueOf(s[0]));
            island.setId(String.valueOf(i));
            islandList.add(island);
            island.setLayoutX(x + 50);
            island.setLayoutY(y + 40);
            island.setAlignment(Pos.BOTTOM_CENTER);
            ImageView img = new ImageView(new Image("/island2.png"));
            islandsImgs.add(img);
            img.setOnMouseEntered(mouseEvent -> {
                img.setFitHeight(193);
                img.setFitWidth(193);
            });
            img.setOnMouseExited(mouseEvent -> {
                img.setFitHeight(190);
                img.setFitWidth(190);
            });
            img.setOnMouseClicked(mouseEvent -> {
                if(studentSource != null) {
                    if (studentSource.equals("entrance") && color != null) {
                        setChanged();
                        notifyObservers(new MoveStudentToIslandMessage(island.getIslandID(), MainGUIController.color));
                        color = null;
                    }
                }
            });
            img.setLayoutX(x);
            img.setLayoutY(y);
            img.setFitHeight(190);
            img.setFitWidth(190);
            addStudents(islands.get(i), island);
            addTowers(island, islands.get(i));
            anchorPane.getChildren().add(img);
            anchorPane.getChildren().add(island);
        }
        addMotherNature(mnPosition);
    }

    /**
     * Method refreshIslands is used to refresh any elements present on every island, after any user action.
     *
     * @param islands of type ArrayList - islands.
     * @param mnPosition of type int - mother nature position.
     * */
    public void refreshIslands(ArrayList<String> islands, int mnPosition){
        for(int i = 0; i < islands.size(); ++i){
            if(i < islandList.size()) {
                islandList.get(i).getChildren().clear();
                addStudents(islands.get(i), islandList.get(i));
                addTowers(islandList.get(i), islands.get(i));
            }
        }

        addMotherNature(mnPosition);
    }

    /**
     * Method drawSchoolBoards shows the schoolboard(s) belonging to the opponent(s).
     *
     * @param schoolBoards of type ArrayList - schoolboard(s) of the opponent(s).
     * */
    public void drawSchoolBoards(ArrayList<String> schoolBoards){

        for(String sb : schoolBoards){
            String[] vals = sb.split("\n");
            towerColors.put(vals[0], TowerColor.valueOf(vals[4].split(" ")[0]));
            if(!vals[0].equals(GUI.getNickname())){
                opponentSBHbox.getChildren().add(new OpponentSchoolBoardViewComponent(vals[0]));
            }
            makeDRClickable();
            refreshSchoolBoards(schoolBoards);
        }

        mainPlayerSB.setLayoutY(550);
        mainPlayerSB.setLayoutX(13);
        mainPlayerSB.setFitWidth(500);
        mainPlayerSB.setFitHeight(230);
        entranceGridPane.setVgap(5);
        entranceGridPane.setHgap(7);
        towersGridPane.setHgap(7);
        towersGridPane.setVgap(7);

        if(GUI.getMode().equals(GameMode.EXPERT)){
            coinImg.setImage(new Image("/Moneta_base.png"));
        }

    }

    /**
     * Method drawClouds shows the clouds on the main game scene.
     * A click on a cloud allows the player to select it and take to their entrance the student pawns which it hosts.
     *
     * @param clouds of type ArrayList - clouds.
     * */
    public void drawClouds(ArrayList<String> clouds){

        cloudHBox.setLayoutX(anchorPane.getWidth()/2 + 120);
        cloudHBox.setLayoutY(anchorPane.getHeight()/2 - 120);

        if(clouds.size() == 3)
            cloudHBox.setLayoutX(anchorPane.getWidth()/2 + 60);

        for(String c : clouds){
            ImageView cloud = new ImageView(new Image("/cloud_card.png"));
            StackPane sp = new StackPane();
            cloud.setFitWidth(120);
            cloud.setFitHeight(120);
            GridPane cloudGrid = new GridPane();
            cloudGrid.setOnMouseClicked(mouseEvent -> {
                setChanged();
                notifyObservers(new PickCloudMessage(clouds.indexOf(c)));
            });
            cloudHBox.setAlignment(Pos.TOP_CENTER);
            cloudGrid.setHgap(7);
            cloudGrid.setVgap(17);
            cloudGrids.add(cloudGrid);
            sp.getChildren().addAll(cloud,cloudGrid);
            cloudHBox.getChildren().add(sp);
        }

        studentsOnClouds(clouds);

    }

    /**
     * Method drawDeck shows the assistant card deck belonging to the player.
     * A click on the desired assistant card in deck allows the player to use it.
     *
     * @param decks - decks.
     * */
    public void drawDeck(ArrayList<String> decks){

        deckHolder.setLayoutX(13);
        deckHolder.setLayoutY(100);

        deckHolder.setHgap(15);
        deckHolder.setVgap(15);

        playedCardsVBox.setLayoutX(anchorPane.getWidth()/2 + 120);
        playedCardsVBox.setLayoutY(anchorPane.getHeight()/2 - 190);

        for(String d : decks){

            String[] deck = d.split(" ");
            playedCardsVBox.getChildren().add(new PlayedCardLabel(deck[0]));
            if(deck[0].equals(GUI.getNickname())) {
                refreshDeck(d);
            }

        }
    }

    /**
     * Method addStudents is used to add student pawns to the gridpanes of the islands, specifying their color and
     * modifying their source to "island", since they are now set on an island.
     *
     * @param c of type String - string to be split, indicating how many students of each color are on an island.
     * */
    public void addStudents(String c, IslandViewComponent island){
        int i = 0;
        String[] colors = c.split(" ");
        for(int j = 0; j < Integer.valueOf(colors[1]); j++){
            island.add(new StudentViewComponent(PawnDiscColor.BLUE, "island") ,j, 1);
        }
        for(int j = 0; j < Integer.valueOf(colors[2]); j++){
            island.add(new StudentViewComponent(PawnDiscColor.GREEN, "island"), j,2);
            i++;
        }
        for(int j = 0; j < Integer.valueOf(colors[3]); j++){
            island.add(new StudentViewComponent(PawnDiscColor.YELLOW, "island"), j,3);
            i++;
        }
        for(int j = 0; j < Integer.valueOf(colors[4]); j++){
            island.add(new StudentViewComponent(PawnDiscColor.PINK, "island"), j,4);
            i++;
        }
        for(int j = 0; j < Integer.valueOf(colors[5]); j++){
            island.add(new StudentViewComponent(PawnDiscColor.RED, "island"), j,5);
            i++;
        }
    }


    /**
     * Method addMotherNature sets mother nature to its updated position on specified island.
     *
     * @param i of type int - island representing new mother nature position.
     * */
    private void addMotherNature(int i){
        try {
            islandList.get(i).add(new MNViewComponent(), 0, 0);
        } catch(Exception e) {}
    }

    /**
     * Method addTowers adds towers, if any, to conquered islands.
     *
     * @param island of type IslandViewComponent - island on which towers are to be added.
     * @param s of type String - string to be split in order to get number of towers.
     * */
    private void addTowers(IslandViewComponent island, String s){
        String[] data = s.split(" ");

        if(data.length < 7)
            return;

       TowerColor color = towerColors.get(data[6]);

        for(int i = 0; i < Integer.valueOf(data[7]); i++){
            island.add(new TowerViewComponent(color), i, 0);
        }
    }

    /**
     * Method studentsOnClouds refreshes the students to be picked from clouds, setting their source to "cloud".
     *
     * @param clouds of type ArrayList - clouds on which students are to be added.
     * */
    public void studentsOnClouds(ArrayList<String> clouds){
        for(int i = 0; i < clouds.size(); i++){
            cloudGrids.get(i).getChildren().clear();
            String[] students = clouds.get(i).split(" ");

            if(students.length == 1)
                continue;

            for(int j = 1; j < students.length ; j++){
                cloudGrids.get(i).add(new StudentViewComponent(PawnDiscColor.valueOf(students[j]), "cloud"), j + 1, 3);
            }
        }
    }

    /**
     * Method refreshDeck refreshes the player's assistant card deck, so that they can only see the cards
     * that are still available.
     *
     * @param deck of type String - string to be split containing assistant cards owned by the player.
     * */
    public void refreshDeck(String deck){
        deckHolder.getChildren().clear();
        String[] cards = deck.split(" ");
        int col = 0;
        int row = -1;
        for(int i = 1; i < cards.length; i = i + 2){
            row ++;
            if(row > 4) col = 1;
            deckHolder.add(new AssistantCardViewComponent(Integer.valueOf(cards[i])), row%5 , col );
        }
        makeCardsClickable();
    }

    /**
     * Method refreshSchoolBoards calls methods to refresh either the main schoolboard
     * or the one(s) belonging to the opponent(s).
     *
     * @param schoolBoards - of type ArrayList - schoolboards.
     * */
    public void refreshSchoolBoards(ArrayList<String> schoolBoards){
        for(String sb : schoolBoards){
            String[] schoolboard = sb.split("\n");
            if(schoolboard[0].equals(GUI.getNickname()))
                refreshMainSB(schoolboard);
            else
                refreshOpponentSchoolboard(schoolboard);

        }
    }

    /**
     * Method refreshMainSB refreshes the main schoolboard, showing changes in students, towers and owned professors.
     *
     * @param mySchoolBoard of type String[] - main schoolboard.
     * */
    private void refreshMainSB(String[] mySchoolBoard) {

        int col = 0;
        int tot = 0;

        entranceGridPane.getChildren().clear();
        String val[] = mySchoolBoard[1].split(" ");
        for(int i = 0; i < Integer.valueOf(val[0]); i++) {
            entranceGridPane.add(new StudentViewComponent(PawnDiscColor.PINK, "entrance"), col, tot%5);
            tot++;
            if(tot > 4)
                col = 1;
        }
        for(int i = 0; i < Integer.valueOf(val[1]); i++){
            entranceGridPane.add(new StudentViewComponent(PawnDiscColor.YELLOW, "entrance"), col, tot%5);
            tot++;
            if(tot > 4)
                col = 1;
        }
        for(int i = 0; i < Integer.valueOf(val[2]); i++){
            entranceGridPane.add(new StudentViewComponent(PawnDiscColor.BLUE, "entrance"), col, tot%5);
            tot++;
            if(tot > 4)
                col = 1;
        }
        for(int i = 0; i < Integer.valueOf(val[3]); i++){
            entranceGridPane.add(new StudentViewComponent(PawnDiscColor.GREEN, "entrance"), col, tot%5);
            tot++;
            if(tot > 4)
                col = 1;
        }
        for(int i = 0; i < Integer.valueOf(val[4]); i++){
            entranceGridPane.add(new StudentViewComponent(PawnDiscColor.RED, "entrance"), col, tot%5);
            tot++;
            if(tot > 4)
                col = 1;
        }


        val = mySchoolBoard[2].split(" ");

        pinkStudentsDRGridPane.getChildren().clear();
        for(int i = 0; i < Integer.valueOf(val[0]); i++) {
            pinkStudentsDRGridPane.add(new StudentViewComponent(PawnDiscColor.PINK, "dr"), i, 0);
        }
        yellowStudentsDRGridPane.getChildren().clear();
        for(int i = 0; i < Integer.valueOf(val[1]); i++){
            yellowStudentsDRGridPane.add(new StudentViewComponent(PawnDiscColor.YELLOW, "dr"), i, 0);
        }
        blueStudentsDRGridPane.getChildren().clear();
        for(int i = 0; i < Integer.valueOf(val[2]); i++){
            blueStudentsDRGridPane.add(new StudentViewComponent(PawnDiscColor.BLUE, "dr"), i, 0);
        }
        greenStudentsDRGridPane.getChildren().clear();
        for(int i = 0; i < Integer.valueOf(val[3]); i++){
            greenStudentsDRGridPane.add(new StudentViewComponent(PawnDiscColor.GREEN, "dr"), i, 0);
        }
        redStudentsDRGridPane.getChildren().clear();
        for(int i = 0; i < Integer.valueOf(val[4]); i++){
            redStudentsDRGridPane.add(new StudentViewComponent(PawnDiscColor.RED, "dr"), i, 0);
        }

        val = mySchoolBoard[4].split(" ");
        col = 0;

        towersGridPane.getChildren().clear();

        for(int i = 0; i < Integer.valueOf(val[1]); i++){
            if(i > 3)
                col = 1;
            towersGridPane.add(new TowerViewComponent(TowerColor.valueOf(val[0])), col, i%4);
        }

        professorGridPane.getChildren().clear();

        if(mySchoolBoard[3].length() == 0)
            return;

        val = mySchoolBoard[3].split(" ");
        int row = 0;

        for(String prof : val){
            professorGridPane.add(new ProfessorViewComponent(PawnDiscColor.valueOf(prof)), 0, row);
            row++;
        }
    }

    /**
     * Method refreshOpponentSchoolboard refreshes the schoolboard belonging to the opponent,
     * showing changes in students and towers.
     *
     * @param opponentSchoolBoard of type String[] - opponent schoolboard.
     * */
    private void refreshOpponentSchoolboard(String[] opponentSchoolBoard){
        for(Node n : opponentSBHbox.getChildren()){
            if(n instanceof OpponentSchoolBoardViewComponent){
                OpponentSchoolBoardViewComponent opponent = (OpponentSchoolBoardViewComponent) n;
                if(opponent.getNickname().equals(opponentSchoolBoard[0])){
                    opponent.showPinkStudents(opponentSchoolBoard[1].split(" ")[0],
                            opponentSchoolBoard[2].split(" ")[0]);
                    opponent.showBlueStudents(opponentSchoolBoard[1].split(" ")[2],
                            opponentSchoolBoard[2].split(" ")[2]);
                    opponent.showGreenStudents(opponentSchoolBoard[1].split(" ")[3],
                            opponentSchoolBoard[2].split(" ")[3]);
                    opponent.showYellowStudents(opponentSchoolBoard[1].split(" ")[1],
                            opponentSchoolBoard[2].split(" ")[1]);
                    opponent.showRedStudents(opponentSchoolBoard[1].split(" ")[4],
                            opponentSchoolBoard[2].split(" ")[4]);
                    opponent.showTowerNumber(opponentSchoolBoard[4].split(" ")[1]);
                }
            }
        }
    }

    /**
     * Method showTurnChange shows the nickname of the player currently playing at each turn change.
     *
     * @param message of type TurnChangeMessage - turn change message.
     * */
    public void showTurnChange(TurnChangeMessage message){
        myNickLabel.setText("currently playing: " + message.getCurrentPlayer());
        myNickLabel.setTextFill(new Color(0.5, 1, 0.97, 1));
    }

    /**
     * Method showOpponentplayedCard shows, inside a label, the assistant card chosen by the opponent for that round.
     *
     * @param message of type PlayedCardMessage - message containing info about the assistant card played.
     * */
    public void showOpponentplayedCard(PlayedCardMessage message){
        for(Node n : playedCardsVBox.getChildren()){
            if(n instanceof PlayedCardLabel){
                PlayedCardLabel label = (PlayedCardLabel)n;
                if(label.getOpponentNickname().equals(message.getOwner())){
                    label.showPlayedCard(message.getCardID(), message.getPowerFactor());
                }
            }
        }
    }

    /**
     * Method setMyNickLabel sets a player's nickname on a label on their screen.
     *
     * @param nickname of type String - player's nickname.
     * */
    public void setMyNickLabel(String nickname){
        myNickLabel.setText(nickname + " 's screen");
    }

    /**
     * Method refreshCoins rewrites the number of coins owned by the player, updating it if any coins have been earned.
     * [Expert mode only]
     *
     * @param coinCounters of type ArrayList - coin counters.
     * */
    public void refreshCoins(ArrayList<String> coinCounters){
        for(String cc : coinCounters){
            String[] counter = cc.split(" ");
            if(counter[0].equals(GUI.getNickname())){
                coinLabel.setText("coins: " + counter[1]);
            }
        }
    }

    /**
     * Method drawExpertCards shows the character cards that were picked for this match, adding a description
     * of their effects on a label and placing students on cards that need to have students on.
     * [Expert mode only]
     *
     * @param cards of type ArrayList - character cards.
     * */
    public void drawExpertCards(ArrayList<String> cards){

        studentGrids = new ArrayList<>();

        param = new Parameter();

        expertCardsHBox.setPadding(new Insets(7));
        expertCardsHBox.setLayoutX(anchorPane.getWidth()/2 + 80 );
        expertCardsHBox.setLayoutY(anchorPane.getHeight()/2 + 15);
        expertCardsHBox.setSpacing(10);
        coinLabel.setTextFill(new Color(0.5, 1, 0.97, 1));
        expertCardTextArea.setLayoutX(anchorPane.getWidth()/2 + 60);
        expertCardTextArea.setLayoutY(anchorPane.getHeight()/2 + 150);

        for(String c : cards){
            StackPane cardPane = new StackPane();
            GridPane studentGrid = new GridPane();
            studentGrids.add(studentGrid);
            expertCardStackPanes.add(cardPane);
            String[] card = c.split("-");
            CharacterCardViewComponent character = new CharacterCardViewComponent(Integer.valueOf(card[0]));
            cardPane.setOnMouseEntered(mouseEvent -> expertCardTextArea.setText("[" + card[1]+"] " + card[2]));
            cardPane.setOnMouseExited(mouseEvent -> expertCardTextArea.setText(""));
            cardPane.setOnMouseClicked(mouseEvent -> {
                chosenCard = Integer.valueOf(card[0]);
                try {
                    handleCard();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
            expertCardsHBox.getChildren().add(cardPane);
            cardPane.getChildren().add(character);
            if(c.length() > 3){
                cardPane.getChildren().add(studentGrid);
                for(int i = 3; i < card.length - 1; i += 2){
                    for(int j = 0; j < Integer.valueOf(card[i+1]); j++) {
                        StudentViewComponent student = new StudentViewComponent(PawnDiscColor.valueOf(card[i]), card[0]);
                        studentGrid.add(student, 0, i - 3 + j);
                        student.toFront();
                    }
                }
            }
        }
    }

    /**
     * Getter method getParameter returns a parameter, which is used to handle the effects of character cards.
     * [Expert mode only]
     *
     * @return Parameter - parameter.
     * */
    public static Parameter getParameter() {
        return param;
    }

    /**
     * Method showError is used to show error messages coming from the server.
     *
     * @param message of type BaseServerMessage - server error message.
     * */
    public void showError(BaseServerMessage message){
        myNickLabel.setText(message.getMessage());
        myNickLabel.setTextFill(new Color(1, 0, 0.15, 1));
    }

    /**
     * Method makeCardsClickable is an utility method needed to detect which assistant card gets clicked.
     * */
    public void makeCardsClickable(){
        for(Node n : deckHolder.getChildren()){
            if(n instanceof AssistantCardViewComponent){
                AssistantCardViewComponent c = (AssistantCardViewComponent)n;
                c.setOnMouseClicked(mouseEvent -> {
                    setChanged();
                    notifyObservers(new AssistantCardMessage(c.getCardID()));
                });
            }
        }
    }

    /**
     * Method makeDRClickable allows to place a student in dining room after having clicked on it.
     * */
    private void makeDRClickable(){
        DRsurface.setOnMouseClicked(mouseEvent -> {
            if(studentSource != null) {
                if (studentSource.equals("entrance") && color != null) {
                    setChanged();
                    notifyObservers(new MoveStudentToDRMessage(color));
                    color = null;
                }
            }
        });
    }

    /**
     * Method refreshStudentsOnCard is used to refresh the students placed on  a character card when playing in expert mode.
     *
     * @param students of type ArrayList - students on card.
     * */
    public void refreshStudentsOnCard(ArrayList<String> students){
        for(Node n : expertCardsHBox.getChildren()){
            if(n instanceof StackPane){
                for(Node m : ((StackPane) n).getChildren()){
                    if(m instanceof GridPane)
                        ((GridPane) m).getChildren().clear();
                }
            }
        }
        for(int k = 0; k < students.size(); k++){
            String pawns[] = students.get(k).split("-");
            for(int i = 3; i < pawns.length - 1; i += 2){
                for(int j = 0; j < Integer.valueOf(pawns[i+1]); j++) {
                    StudentViewComponent student = new StudentViewComponent(PawnDiscColor.valueOf(pawns[i]), pawns[0]);
                    studentGrids.get(k).add(student, 0, i - 3 + j);
                    student.toFront();
                }
            }
        }
    }

    /**
     * Method refreshCardDescriptions is used to show the description of a character card and
     * its cost, which gets updated if the card has been used.
     * [Expert mode only]
     *
     * @param cards of type ArrayList - character cards.
     * */
    public void refreshCardDescriptions(ArrayList<String> cards){

        int i = 0;

        for(String c : cards){
            String[] card = c.split("-");
            StackPane cardPane = expertCardStackPanes.get(i);
            cardPane.setOnMouseEntered(mouseEvent -> expertCardTextArea.setText("[" + card[1]+"] " + card[2]));
            i++;
        }
    }

    /**
     * Method winningScreen removes from scene all the elements of the game and sets a label showing
     * the nickname of the winner of the match.
     *
     * @param winner of type String - winner's nickname.
     * */
    public void winningScreen(String winner){
        anchorPane.getChildren().clear();
        Label winnerLabel = new Label("This game is over!" +
                "\ncongratulations to " + winner + " who won the game!");
        winnerLabel.setFont(Font.font("Ink Free", 35));
        winnerLabel.setTextFill(new Color(0.5, 1, 0.97, 1));
        winnerLabel.setLayoutX(anchorPane.getHeight()/2);
        winnerLabel.setLayoutY(anchorPane.getWidth()/2-170);
        anchorPane.getChildren().add(winnerLabel);
    }

    /**
     * Getter method getColor returns the color of a student or professor.
     *
     * @return PawnDiscColor - color.
     * */
    public static PawnDiscColor getColor(){
        return color;
    }

    /**
     * Setter method setColor sets the color of a student or professor to the one specified as parameter.
     *
     * @param c of type PawnDiscColor - color to be set.
     * */
    public static void setColor(PawnDiscColor c){
        color = c;
    }

    /**
     * Setter method setStudentSource sets the source of a student pawn to the one specified as parameter.
     *
     * @param source of type String - source to be set.
     * */
    public static void setStudentSource(String source){
        studentSource = source;
    }

    /**
     * Getter method getIslands returns the ArrayList containing the islands.
     *
     * @return ArrayList - islands ArrayList.
     * */
    public static ArrayList<IslandViewComponent> getIslands(){
        return islandList;
    }

    /**
     * Method collectColor is used to show a new stage in which the player can select the color of a student,
     * in order to achieve an effect from a character card.
     * [Expert mode only]
     *
     * @throws IOException if an I/O exception occurs.
     * */
    public void collectColor() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/studentSelectionScene.fxml"));
        Parent root = loader.load();
        Scene studentSelectionScene = new Scene(root);
        cardStage.setScene(studentSelectionScene);
        try {
            cardStage.showAndWait();
        }catch(IllegalStateException e){}
    }

    /**
     * Method collectIslandId is used to show a new stage in which the player can select a specific island,
     * in order to achieve an effect from a character card.
     * [Expert mode only]
     *
     * @throws IOException if an I/O exception occurs.
     * */
    public void collectIslandId() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/islandSelectionScene.fxml"));
        Parent root = loader.load();
        Scene islandSelectionScene = new Scene(root);
        cardStage.setScene(islandSelectionScene);
        IslandSelectionController islandSelectionController = loader.getController();
        Platform.runLater(() -> islandSelectionController.drawIslands(islandList));
        try{
        cardStage.showAndWait();
        }catch (IllegalStateException e){}
    }

    /**
     * Method addNoEntryTile is used to place a no-entry tile on an island when a player decides to do it.
     * [Expert mode only]
     *
     * @param islands of type ArrayList - islands.
     * */
    public void addNoEntryTile(ArrayList<Integer> islands){
        for(Integer i : islands){
            ImageView noEntryTile = new ImageView(new Image("/deny_island_icon.png"));
            noEntryTile.setFitHeight(20);
            noEntryTile.setFitWidth(20);
            islandList.get(i).add(noEntryTile, 0, 5);
        }
    }

    /**
     * Method collectMoves is used to show a new stage in which the player can click a button that allows them
     * to select the number of additional steps that mother nature will take.
     * [Expert mode only]
     *
     * @throws IOException if an I/O exception occurs.
     * */
    public void collectMoves() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ExtraStepsSelectionController.fxml"));
        Parent root = loader.load();
        Scene collectMovesScene = new Scene(root);
        cardStage.setScene(collectMovesScene);
        try {
            cardStage.showAndWait();
        }catch(IllegalStateException e){}
    }

    /**
     * Method collectMoves is used to show a new stage in which the player can select the color of the professor(s)
     * they want to take control of for the turn, when using a specific character card.
     *
     * @throws IOException if an I/O exception occurs.
     * */
    public void collectProfessors() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chooseProfessorScene.fxml"));
        Parent root = loader.load();
        Scene chooseProfScene = new Scene(root);
        cardStage.setScene(chooseProfScene);
        try{
        cardStage.showAndWait();
        }catch (IllegalStateException e){}

    }

    /**
     * Method collectStudentList is used to show a new stage from which it's possible to collect student colors,
     * in order to achieve an effect from a character card.
     * [Expert mode only]
     *
     * @throws IOException if an I/O exception occurs.
     * */
    public void collectStudentList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/studentListSelectionScene.fxml"));
        Parent root = loader.load();
        Scene chooseStudentListScene = new Scene(root);
        cardStage.setScene(chooseStudentListScene);
        try {
            cardStage.showAndWait();
        }catch(IllegalStateException e){}
    }

    /**
     * Method handleCard is called when a character card is used, and based on its id different methods are called
     * in order to obtain the desired character card effect.
     *
     * @throws IOException if an I/O exception occurs.
     * */
    public void handleCard() throws IOException {

        CharacterCardMessage message = new CharacterCardMessage();
        message.setCardID(chosenCard);

        switch(chosenCard){
            case 1:
                collectColor();
                collectIslandId();
                break;
            case 2:
                collectProfessors();
                break;
            case 3:
            case 5:
                collectIslandId();
                break;
            case 4:
                collectMoves();
                break;
            case 6:
            case 8:
                // no parameters needed
                break;
            case 7:
            case 10:
                collectStudentList();
                collectStudentList();
                break;
            case 9:
            case 11:
            case 12:
                collectColor();
                break;
        }
        message.setParam(param);
        setChanged();
        notifyObservers(message);
        param = new Parameter();
        chosenCard = 0;
    }

    /**
     * Method closeCardStage closes the new stage that gets opened in order to take in any needed parameters
     * that were required to use a character card.
     * */
    public static void closeCardStage(){
        cardStage.close();
    }

    /**
     * Method clearPlayedCardLabels clears the labels that show the assistant cards played by every player in a round.
     * */
    public void clearPlayedCardLabels() {
        for(Node n : playedCardsVBox.getChildren()){
            if(n instanceof PlayedCardLabel){
                ((PlayedCardLabel) n).setText("");
            }
        }
    }

    /**
     * Method hideTextArea is used when playing in simple mode in order to hide the text area that in expert mode
     * is used to show the descriptions of character cards.
     * */
    public void hideTextArea() {
        expertCardTextArea.setVisible(false);
    }
}
