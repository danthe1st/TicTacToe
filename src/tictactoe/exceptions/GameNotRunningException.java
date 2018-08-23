package tictactoe.exceptions;

/**
 * Exception when the Game is not running
 */
public class GameNotRunningException extends Exception {
    public GameNotRunningException(){
        super("the game has ended previously");
    }
}
