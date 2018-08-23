package tictactoe.exceptions;

/**
 * Exception-class when a field is already bound
 */
public class AlreadyBoundException extends Exception {
    public AlreadyBoundException(int... values){
        this(loadMessage(values));
    }
    private AlreadyBoundException(String reason){
        super(reason);
    }

    /**
     * gets the Exception-Message
     * @param values the coordinates of the field
     * @return the Message
     */
    private static String loadMessage(int... values){
        StringBuilder reasonBuilder=new StringBuilder("Field is already bound: ");
        boolean hasElementsBefore=false;
        for (int coord:values) {
            if (hasElementsBefore){
                reasonBuilder.append(" ");
            }else {
                hasElementsBefore=true;
            }
            reasonBuilder.append(coord);

        }
        return reasonBuilder.toString();
    }
}
