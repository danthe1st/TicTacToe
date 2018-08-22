package exceptions;

/**
 * Exception when a field does not exist
 */
public class FieldOutOfBoundsException extends RuntimeException {
    private FieldOutOfBoundsException(String reason){
        super(reason);
    }
    public FieldOutOfBoundsException(String nameOfOutOfBounds,int value){
        this(nameOfOutOfBounds+" is out of bounds: "+value);
    }
}
