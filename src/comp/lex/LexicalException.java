package comp.lex;

/**
 * Represents an exception during the lexical analysis.
 */
public class LexicalException extends RuntimeException {

    /**
     * Constructor. It simply passes up the exception message.
     */
    public LexicalException(String message) {
        super(message);
    }
}
