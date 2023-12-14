package packman;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;

public class GameController {
    @FXML private Image s = new Image(("/packman/down_eating_pacman.png"));
    @FXML private Image w = new Image(("/packman/top_eating_pacman.png"));
    @FXML private Image a = new Image("/packman/left_eating_pacman.png");
    @FXML private Image d = new Image("/packman/right_eating_pacman.png");
    @FXML private Image monster = new Image("/packman/monster.png");
    @FXML private Image cherry = new Image("/packman/cherry.png");
    @FXML private ImageView packamnImageView = new ImageView(w);
    @FXML private ImageView monsterImageView = new ImageView(monster);

    @FXML private AnchorPane anchorPaneFXML; // referer til Pane som spillebrette skal ligge i
        @FXML private Pane paneGameoverFXML;
        @FXML private GridPane gridPaneFXML;
            @FXML private Pane paneGameFXML;
            @FXML private GridPane infoGridPaneFXML;
                 @FXML private TextField yourNameTextFieldFXML;
                // @FXML private Button saveButton;
                // @FXML private Button showPlayersButton;
        

    private Game game;
    private List<Monster> monsters;
    private FileSaver fileSaver;


    @FXML
    public void initialize() throws IOException{
        game = new Game(15, 15);

        fileSaver = new FileSaver();
        fileSaver.setGameController(this);
        fileSaver.setGame(game);
        System.out.println(fileSaver.readAllText() + "reading from file");
        Monster monster1 = new Monster(1, 1, this.game);
        Monster monster2 = new Monster(5, 5, this.game);
        Monster monster3 = new Monster(10, 10, this.game);
        Monster monster4 = new Monster(12, 12, this.game);
        monster1.setPreviousType(2);
        monster2.setPreviousType(2);
        monster3.setPreviousType(2);
        monster4.setPreviousType(2);
        monsters = new ArrayList<>();
        monsters.add(monster1);
        monsters.add(monster2);
        monsters.add(monster3);
        monsters.add(monster4);

        createLogicalBoard();
        createFXMLBoard();
        colorFXMLBoard();
    }

    @FXML
    public void saveButton() throws IOException {
        System.out.println("saveButton kjøres");
        fileSaver.saveText(yourNameTextField());
        fileSaver.closeWriter();
        fileSaver.closeReader();
    }

    @FXML
    public void showPlayersButton() throws IOException{
        System.out.println("show players button kjøres");
        showNames();
    }

    @FXML
    public void showNames() throws IOException {
        ListView<String> listView = new ListView<>();
        listView.setOrientation(Orientation.VERTICAL);

        List<String> linesList = new ArrayList<>();
        String text = fileSaver.readAllText();
        String[] lines = text.split("@");
        for (int i=0; i<lines.length; i++) {
            linesList.add(lines[i]);
        }
        linesList.sort(new ComparatorInterface());
        listView.getItems().addAll(linesList);

        listView.setStyle("-fx-font-size: 14px");
        listView.setTranslateX(game.getWidth()*40/2 +300); // svarer ikke på translateX
        listView.setTranslateY(250);
        paneGameoverFXML.setVisible(true);
        paneGameoverFXML.getChildren().add(listView);
    }

    @FXML
    public String yourNameTextField() {
        return yourNameTextFieldFXML.getText();
    }

    @FXML
    public void showGameoverScreen() {

        infoGridPaneFXML.setVisible(true);

        if (game.hasWon()) {
            Text wonText = new Text();
            wonText.setText("Gratulerer, du vant!");
            wonText.setFill(Color.GREEN);
            wonText.setStyle("-fx-font-size: 100px");
            wonText.setTranslateX(game.getWidth()*40/2 +100);
            wonText.setTranslateY(200);
            paneGameoverFXML.setVisible(true);
            paneGameoverFXML.getChildren().add(wonText);
        }
        else {
            Text lostText1 = new Text();
            lostText1.setText("U suck");
            lostText1.setFill(Color.RED);
            lostText1.setStyle("-fx-font-size: 100px");
            lostText1.setTranslateX(game.getWidth()*40 + 100);
            lostText1.setTranslateY(200);
            paneGameoverFXML.setVisible(true);
            paneGameoverFXML.getChildren().add(lostText1);

            Text lostText2 = new Text();
            lostText2.setText("my balls");
            lostText2.setFill(Color.RED);
            lostText2.setStyle("-fx-font-sixe: 250px"); // svarer ikke på skriftstørelse
            lostText2.setTranslateX(game.getWidth()*40+100);
            lostText2.setTranslateY(250);
            paneGameoverFXML.setVisible(true);
            paneGameoverFXML.getChildren().add(lostText2);
        }
    }






    public void createLogicalBoard() {
        for (int i=0; i<game.getHeight(); i++) {
            game.getBoard()[0][i].setType(4);
            game.getBoard()[14][i].setType(6);
            game.getBoard()[i][0].setType(3);
            game.getBoard()[i][14].setType(5);
        }
        // setter opp alle vegger
        for (int i=0; i<4; i++) {
            game.getBoard()[3][i+2].setType(0);
            game.getBoard()[10][i+1].setType(0);
            game.getBoard()[12][i+8].setType(0);
        }
        for (int i=0; i<2; i++) {
            game.getBoard()[i+8][8].setType(0);
            game.getBoard()[i+1][12].setType(0);
        }
        for (int i=0; i<4; i++) {
            game.getBoard()[i+5][12].setType(0);
        }
        for (int i=0; i<5; i++) {
            game.getBoard()[i+4][4].setType(0);
            game.getBoard()[i+4][7].setType(0);
        }

        // setter opp packman
        game.getBoard()[7][7].setType(7); // 7 = packman
        // setter opp ghosts
        game.getBoard()[1][1].setType(8); // 8 = ghost
        game.getBoard()[5][5].setType(8);
        game.getBoard()[10][10].setType(8);
        game.getBoard()[12][12].setType(8);
    }

    public void createFXMLBoard() {
        paneGameFXML.getChildren().clear(); // sletter det gamle spillebrettet

        for (int i=0; i<game.getHeight(); i++) {
            for (int j=0; j<game.getWidth(); j++) {
                Pane tileFXML = new Pane();
                tileFXML.setPrefHeight(40);
                tileFXML.setPrefWidth(40);
                tileFXML.setLayoutX(j*40);
                tileFXML.setLayoutY(i*40);
                
                paneGameFXML.getChildren().add(tileFXML);
            }
        }
    }

    public void colorFXMLBoard() {
        for (int i=0; i<game.getHeight(); i++) {
            for (int j=0; j<game.getWidth(); j++) {
                int index = i*game.getHeight()+j;
                switch (game.getTile(i, j).getType()) {
                    case (0):
                        paneGameFXML.getChildren().get(index).setStyle("-fx-background-color: " + "#db4d4d"); // rød
                        break;
                    case (1):
                        paneGameFXML.getChildren().get(index).setStyle("-fx-background-color: " + "#e3e2c1"); // beige
                        break;
                    case(2):
                        paneGameFXML.getChildren().get(index).setStyle("-fx-background-color: " + "#f3cf3f"); // gull
                        break;
                    case(3):
                        paneGameFXML.getChildren().get(index).setStyle("-fx-background-color: " + "#e3e2c1"); // beige
                        break;
                    case(4):
                        paneGameFXML.getChildren().get(index).setStyle("-fx-background-color: " + "#e3e2c1"); // beige
                        break;
                    case(5):
                        paneGameFXML.getChildren().get(index).setStyle("-fx-background-color: " + "#e3e2c1"); // beige
                        break;
                    case(6):
                        paneGameFXML.getChildren().get(index).setStyle("-fx-background-color: " + "#e3e2c1"); // beige
                        break;
                    case(7):
                        Pane oldPackmanPane = (Pane) paneGameFXML.getChildren().get(index);
                        oldPackmanPane.getChildren().clear();

                        packamnImageView.setFitWidth(40);
                        packamnImageView.setFitHeight(40);
                        oldPackmanPane.getChildren().add(packamnImageView);
                        break;
                    case(8):
                        Pane oldPane = (Pane) paneGameFXML.getChildren().get(index);
                        oldPane.getChildren().clear();

                        monsterImageView = new ImageView(monster);
                        monsterImageView.setFitWidth(40);
                        monsterImageView.setFitHeight(40);
                        oldPane.getChildren().add(monsterImageView);
                        break;
                }
            }
        }
    }

    public void setPackmanImageView(Image i) {
        this.packamnImageView = new ImageView(i);
    }
    @FXML
    public void handle(KeyEvent e) {
        KeyCode press = e.getCode();

        switch (press) {
            case W: 
                game.movePackman(-1, 0);
                setPackmanImageView(w);
                updateGame();
                renderGame();
                break;
            case S:
                game.movePackman(1, 0);
                setPackmanImageView(s);
                updateGame();
                renderGame();
                break;
            case A:
                game.movePackman(0, -1);
                setPackmanImageView(a);
                updateGame();
                renderGame();
                break;
            case D:
                game.movePackman(0, 1);
                setPackmanImageView(d);
                updateGame();
                renderGame();
                break;
            case ESCAPE:
                game.setGameOver(true);
                break;
        }
    }

    public void updateGame() {
        for (Monster monster : monsters) {
             monster.keepMoving();
             monster.isDead();
             game.hasWon();
        }
    }

    public void renderGame() {
        createFXMLBoard();
        colorFXMLBoard();
    }

    public Game getGame() {
        return this.game;
    }
    public void setGame(Game game) {
        this.game=game;
    }
}


