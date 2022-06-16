package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.ActionMessages.AssistantCardMessage;
import it.polimi.ingsw.Client.ActionMessages.MoveStudentToDRMessage;
import it.polimi.ingsw.Client.ActionMessages.MoveStudentToIslandMessage;
import it.polimi.ingsw.Client.ActionMessages.PickCloudMessage;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Utils.Enums.TowerColor;
import it.polimi.ingsw.Utils.NetMessages.BaseServerMessage;
import it.polimi.ingsw.Utils.NetMessages.PlayedCardMessage;
import it.polimi.ingsw.Utils.NetMessages.TurnChangeMessage;
import it.polimi.ingsw.View.GUI.Components.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

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


    ArrayList<IslandViewComponent> islandList = new ArrayList<>();
    ArrayList<ImageView> islandsImgs = new ArrayList<>();
    ArrayList<GridPane> cloudGrids = new ArrayList<>();
    HashMap<String, TowerColor> towerColors = new HashMap<>();

    private static PawnDiscColor color;
    private static String studentSource;

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

    public void refreshIslands(ArrayList<String> islands, int mnPosition){
        for(int i = 0; i < islands.size(); ++i){
            islandList.get(i).getChildren().clear();
            addStudents(islands.get(i), islandList.get(i));
            addTowers(islandList.get(i), islands.get(i));
        }

        addMotherNature(mnPosition);
    }

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

    public void drawDeck(ArrayList<String> decks){

        deckHolder.setLayoutX(13);
        deckHolder.setLayoutY(100);

        deckHolder.setHgap(15);
        deckHolder.setVgap(15);

        for(String d : decks){

            String[] deck = d.split(" ");
            playedCardsVBox.getChildren().add(new PlayedCardLabel(deck[0]));
            if(deck[0].equals(GUI.getNickname())) {
                refreshDeck(d);
                return;
            }

        }
    }

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

    private void addMotherNature(int i){
        islandList.get(i).add(new MNViewComponent(), 0, 0);
    }

    private void addTowers(IslandViewComponent island, String s){
        String[] data = s.split(" ");

        if(data.length < 7)
            return;

       TowerColor color = towerColors.get(data[6]);

        for(int i = 0; i < Integer.valueOf(data[7]); i++){
            island.add(new TowerViewComponent(color), i, 0);
        }
    }

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

    public void refreshSchoolBoards(ArrayList<String> schoolBoards){
        for(String sb : schoolBoards){
            String[] schoolboard = sb.split("\n");
            if(schoolboard[0].equals(GUI.getNickname()))
                refreshMainSB(schoolboard);
            else
                refreshOpponentSchoolboard(schoolboard);

        }
    }

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

    public void showTurnChange(TurnChangeMessage message){
        myNickLabel.setText("currently playing: " + message.getCurrentPlayer());
        myNickLabel.setTextFill(new Color(0.5, 1, 0.97, 1));
    }

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

    public void setMyNickLabel(String nickname){
        myNickLabel.setText(nickname + " 's screen");
    }

    public void refreshCoins(ArrayList<String> coinCounters){
        for(String cc : coinCounters){
            String[] counter = cc.split(" ");
            if(counter[0].equals(GUI.getNickname())){
                coinLabel.setText("You have " + counter[1] + " coins");
            }
        }
    }

    public void drawExpertCards(ArrayList<String> cards){

        expertCardsHBox.setPadding(new Insets(7));
        expertCardsHBox.setLayoutX(anchorPane.getWidth()/2 + 80 );
        expertCardsHBox.setLayoutY(anchorPane.getHeight()/2 + 15);
        expertCardsHBox.setSpacing(10);

        for(String c : cards){
            String[] card = c.split("-");
            expertCardsHBox.getChildren().add(new CharacterCardViewComponent(Integer.valueOf(card[0])));
        }
    }

    public void showError(BaseServerMessage message){
        myNickLabel.setText(message.getMessage());
        myNickLabel.setTextFill(new Color(1, 0, 0.15, 1));
    }

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

    private void makeDRClickable(){
        DRsurface.setOnMouseClicked(mouseEvent -> {
            if(studentSource.equals("entrance") && color != null){
                setChanged();
                notifyObservers(new MoveStudentToDRMessage(color));
                color = null;
            }
        });
    }

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

    public static String getStudentSource(){
        return studentSource;
    }

    public static PawnDiscColor getColor(){
        return color;
    }

    public static void setColor(PawnDiscColor c){
        color = c;
    }

    public static void setStudentSource(String source){
        studentSource = source;
    }

}
