package tictactoe.core;

import tictactoe.Main;
import tictactoe.exceptions.AlreadyBoundException;
import tictactoe.exceptions.GameNotRunningException;
import tictactoe.exceptions.GameRunningException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

/**
 * class for the GUI of the Game
 */
public class GUI extends Application {
    @NotNull
    private final Game theGame;
    private boolean player;
    private Rectangle[][] fieldRects;
    private ImagePattern player1ImgPattern;
    private ImagePattern player2ImgPattern;
    /**
     * no-args-constructor for {@link Application}
     */
    public GUI(){
        theGame=Game.getGame();
        player=false;
        player1ImgPattern=getPattern("resc/pl1img.png");
        player2ImgPattern=getPattern("resc/pl2img.png");

    }
    private static ImagePattern getPattern(String path){
        URL resource = Main.class.getResource(path);
        Image player1Img=new Image(resource.toString());
        return new ImagePattern(player1Img);
    }
    @Override
    public void start(@NotNull Stage primaryStage){
        Rectangle2D visBounds=getScreenBounds();
        Boolean[][] fields=theGame.getFields();

        HBox[] fieldBoxes=new HBox[fields.length];
        fieldRects=new Rectangle[fields.length][fields[0].length];


        double smallest=Math.min(visBounds.getHeight(),visBounds.getWidth());
        double sqSize=smallest/3;

        for (int x = 0; x < fields.length; x++) {
            for (int y = 0; y < fields[x].length; y++) {
                fieldRects[x][y]=new Rectangle(sqSize,sqSize);
                fieldRects[x][y].setFill(Color.WHITE);
                fieldRects[x][y].setStroke(Color.BLACK);
                int xCopy=x;
                int yCopy=y;
                fieldRects[x][y].setOnMouseClicked(e->onFieldClick(xCopy,yCopy));

            }
            fieldBoxes[x]=new HBox(fieldRects[x]);
        }
        VBox wrapper=new VBox(fieldBoxes);
        Scene scene=new Scene(wrapper);
        scene.setOnKeyPressed(this::onKeyPressed);


        primaryStage.setScene(scene);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }

    /**
     * executed when a Key is pressed<br>
     * R    ... resets the Game
     * ESC  ... stops the Application
     * @param event the {@link KeyEvent of the Key pressed}
     */
    private void onKeyPressed(KeyEvent event){
        switch (event.getCode()){
            case R:
                theGame.reset();
                reloadAll();
                break;
            case ESCAPE:
                Platform.exit();
                break;
        }
    }

    /**
     * executed when it is clicked on a field
     * @param x the x-coordinate of the field
     * @param y the y-coordinate of the field
     */
    private void onFieldClick(int x,int y){
        try {
            theGame.setField(x,y,player);
            player=!player;
            reloadField(x,y);
            if (!theGame.isRunning()){
                Boolean winner=theGame.getWinner();

                if (winner==null){
                    Alert alert=new Alert(Alert.AlertType.INFORMATION,"The game ends without a winner!");
                    alert.showAndWait();
                    theGame.reset();
                    reloadAll();
                }
                else {
                    String winnerString;
                    if (!winner){
                        winnerString="Player 1";
                    }else {
                        winnerString="Player 2";
                    }

                    Alert alert=new Alert(Alert.AlertType.INFORMATION,winnerString+" won!");
                    alert.showAndWait();
                    theGame.reset();
                    reloadAll();
                }
            }
        }catch (AlreadyBoundException e){
            Alert alert=new Alert(Alert.AlertType.ERROR,"This field is already in use");
            alert.show();
        }catch (GameNotRunningException e){
            Alert alert=new Alert(Alert.AlertType.ERROR,"This game is not running - press 'r' to restart the Game!");
            alert.show();
        } catch (GameRunningException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR,"Unknown Error");
            alert.show();
        }
    }

    /**
     * reloads the GUI representation of a field
     * @param x the x-coordinate of the field
     * @param y the y-coordinate of the field
     */
    private void reloadField(int x,int y){
        Paint paint;
        Boolean field=theGame.getField(x,y);
        if (field==null){
            paint=Color.WHITE;
        }
        else if (field){
            //paint=Color.RED;
            paint=player2ImgPattern;
        }
        else {
            //paint=Color.BLUE;
            paint=player1ImgPattern;
        }
        fieldRects[x][y].setFill(paint);
    }

    /**
     * reloads the GUI representation of all fields
     */
    private void reloadAll(){
        for (int x = 0; x < fieldRects.length; x++) {
            for (int y = 0; y < fieldRects[x].length; y++) {
                reloadField(x,y);
            }
        }
    }

    /**
     * gets the visual Bounds of the main Screen
     * @return the visual bounds of the main Screen represented by a {@link Rectangle2D}
     */
    private Rectangle2D getScreenBounds(){
        return Screen.getPrimary().getVisualBounds();
    }
}
