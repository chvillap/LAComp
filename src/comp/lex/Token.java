package comp.lex;

/**
 * A single lexic token, described by its lexeme and type.
 */
public class Token
{
    private TokenType type;
    private String lexeme;

    /**
     * Constructor. Assigns the token's type and lexeme.
     *
     * @param type Value of the token type.
     * @param lexeme Lexeme string.
     */
    public Token(TokenType type, String lexeme)
    {
        this.type = type;
        this.lexeme = lexeme;
    }

    /**
     * Getter for type.
     */
    public TokenType getType()
    {
        return type;
    }

    /**
     * Getter for lexeme.
     */
    public String getLexeme()
    {
        return lexeme;
    }

    /**
     * Converts the token to a string representation.
     *
     * @return String in the format: "lexeme - category".
     */
    public String toString()
    {
        switch (type) {
            case IDENTIFIER:
                return lexeme + " - identificador";
            case STRING_LITERAL:
                return lexeme + " - cadeia_literal";
            case INTEGER_NUMBER:
                return lexeme + " - numero_inteiro";
            case REAL_NUMBER:
                return lexeme + " - numero_real";
            default: // keyword or symbol
                return lexeme + " - " + lexeme;
        }
    }
}
