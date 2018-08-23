package tictactoe.exceptions;

/**
 * Exception when the Game is running when an action is performed that needs the Game to be running
 */
public class GameRunningException extends Exception {
    public GameRunningException(){
        super("the game has not ended yet");
    }
}
