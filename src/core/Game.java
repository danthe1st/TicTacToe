package core;


import exceptions.AlreadyBoundException;
import exceptions.FieldOutOfBoundsException;
import exceptions.GameNotRunningException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Core class for the Game
 */
public class Game {
    private Boolean[][] fields;
    @Nullable
    private static Game theGame =null;
    private boolean running=true;
    /**
        only via {@link Game#getGame()}
     */
    private Game(){
        reset();
    }

    /**
     * gets the instance of the Game(Singleton)
     * @return the (only) instance of the game
     */
    @NotNull
    public static Game getGame(){
        if (theGame==null){
            theGame=new Game();
        }
        return theGame;
    }

    /**
     * bind a field to a Player
     * @param x the x-coordinate of the field
     * @param y the y-coordinate of the field
     * @param player the Player to be set(Player 1 is false, Player 2 is true)
     * @throws AlreadyBoundException if the field is already bound
     * @throws GameNotRunningException if the Game is not running currently
     * @throws FieldOutOfBoundsException if the Field does not exist
     */
    public void setField(int x,int y,boolean player)throws AlreadyBoundException, GameNotRunningException,FieldOutOfBoundsException {
        if (!running){
            throw new GameNotRunningException();
        }
        if (x>=fields.length||x<0){
            throw new FieldOutOfBoundsException("x",x);
        }
        if (y>=fields[0].length||y<0){
            throw new FieldOutOfBoundsException("y",y);
        }
        if (fields[x][y]!=null){
            throw new AlreadyBoundException(x,y);

        }
        fields[x][y]=player;
    }

    /**
     * resets the Game and starts it
     */
    public void reset(){
        fields=new Boolean[3][3];
        running=true;
    }

    /**
     * gets the TicTacToe-Fields represented as a {@link Boolean}[][]<br>
     * null ... not bound yet<br>
     * false ... Player 1<br>
     * true ... Player 2<br>
     * @return the Fields
     */
    public Boolean[][] getFields() {
        return fields.clone();
    }

    /**
     * gets one field using coordinates<br>
     * null ... not bound yet<br>
     * false ... Player 1<br>
     * true ... Player 2<br>
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @throws ArrayIndexOutOfBoundsException if the field does not exist
     * @return the Field represented by a {@link Boolean}
     */
    public Boolean getField(int x,int y) throws ArrayIndexOutOfBoundsException{
        return fields[x][y];
    }

    /**
     * gets the current winner
     * null ... not bound yet<br>
     * false ... Player 1<br>
     * true ... Player 2<br>
     * @return the winner represented by a {@link Boolean} or null if the game is not over yet
     */
    @Nullable
    public Boolean getWinner(){
        bigLoop:for (Boolean[] field : fields) {
            Boolean winner = field[0];
            if (winner == null) {
                continue;
            }
            for (int y = 1; y < field.length; y++) {
                if (field[y] == null || winner.booleanValue() != field[y].booleanValue()) {
                    continue bigLoop;
                }
            }
            running = false;
            return winner;
        }
        bigLoop:for (int x = 0; x < fields.length; x++) {
            Boolean winner=fields[0][x];
            if (winner==null){
                continue;
            }
            for (int y = 1; y < fields[x].length; y++) {

                if (fields[y][x]==null||winner.booleanValue()!=fields[y][x].booleanValue()){
                    continue bigLoop;
                }
            }
            running=false;
            return winner;
        }
        Boolean winner=fields[0][0];
        if (winner!=null){
            for (int xy = 0; xy < fields.length; xy++) {
                if (fields[xy][xy] == null || winner.booleanValue() != fields[xy][xy].booleanValue()) {
                    winner = null;
                    break;
                }
            }
            if (winner!=null){
                running=false;
                return winner;
            }
        }
        winner=fields[fields.length-1][0];
        if (winner==null){
            return null;
        }

        for (int xy = 0; xy < fields.length; xy++) {
            if (fields[fields.length - xy - 1][xy] == null || winner.booleanValue() != fields[fields.length - xy - 1][xy].booleanValue()) {
                winner = null;
                break;
            }
        }
        if (winner!=null){
            running=false;
        }
        return winner;
    }
}
