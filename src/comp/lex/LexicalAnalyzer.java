package comp.lex;

import java.util.Scanner;

/**
 * The lexical analyzer. It scans the input code and segment it into a
 * sequence of categorized tokens.
 */
public class LexicalAnalyzer {

    private char[] buffer;
    private int bufferPtr;
    private int lineNumber;
    private TokenTable tokenTable;

    /**
     * Constructor. Initializes members and fills the input buffer.
     *
     * @param code The whole input code as a string.
     */
    public LexicalAnalyzer(String code) {
        buffer = code.toCharArray();
        bufferPtr = (buffer.length > 0) ? 0 : -1;
        lineNumber = 1;
        tokenTable = new TokenTable();
    }

    /**
     * Move the buffer pointer a certain number of positions ahead/back.
     *
     * @param count Number of positions (negative to move backward).
     */
    private void moveBufferPtr(int count) {
        int target = bufferPtr + count;

        // Move the pointer keeping track of the line number.
        while (bufferPtr < target) {
            if (currentChar() == '\n')
                ++lineNumber;
            ++bufferPtr;
        }
        while (bufferPtr > target) {
            if (currentChar() == '\n')
                --lineNumber;
            --bufferPtr;
        }
    }

    /**
     * Get the current character from buffer.
     */
    private char currentChar() {
        // Return the null character if the pointer is at an invalid position.
        if (bufferPtr < 0 || bufferPtr >= buffer.length)
            return '\0';
        return buffer[bufferPtr];
    }

    /**
     * Gets the next token from the input buffer (if any).
     *
     * @return A token of some kind, or null if something went wrong.
     * @throws LexicalException
     */
    public Token nextToken() throws LexicalException {
        Token token = null;
        char character = currentChar();

        // Ignore all whitespaces and comments.
        while (character == ' ' || character == '\n' || character == '\t' ||
               character == '{' || character == '\r' || character == '\f') {
            if (character == '{')
                tokenizeComment();

            moveBufferPtr(1);
            character = currentChar();
        }

        // Some input files may have nothing but comments and/or whitespaces
        // (or at least they end with that). So we need to check whether we
        // are already done with this input or not. The null character signals
        // this.
        if (character == '\0')
            return token;

        // Take a peek at the next character to decide which tokenizing
        // function should be used.
        if (Character.isDigit(character))
            token = tokenizeNumber();
        else if (character == '"')
            token = tokenizeString();
        else if (character == '_' || Character.isLetter(character))
            token = tokenizeIdentifierOrKeyword();
        else
            token = tokenizeSymbol();

        // If no function can tokenize what comes next, then we have a
        // lexical error.
        if (token == null) {
            String message = "Linha " + lineNumber + ": " + character +
                " - simbolo nao identificado";
            throw new LexicalException(message);
        }

        return token;
    }

    /**
     * Detects the next comment token (and ignore them).
     *
     * COMENTARIO
     *     : '{' ~( '\n' | '\r' | EOF )* '}'
     *     ;
     *
     * @throws LexicalException
     */
    public void tokenizeComment() throws LexicalException {
        char character = currentChar();

        // A comment can end with closing brackets, or a new line, or EOF.
        // We keep reading the comment's content until it reaches its end.
        while (character != '}' && character != '\0' && character != '\n') {
            moveBufferPtr(1);
            character = currentChar();
        }

        // Leaving a comment unclosed causes a lexical error.
        if (character == '\0' || character == '\n') {
            String message = "Linha " + lineNumber +
                ": comentario nao fechado";
            throw new LexicalException(message);
        }
    }

    /**
     * Detects the next identifier token.
     *
     * IDENTIFICADOR
     *     : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
     *     ;
     *
     * @return An identifier token, or null if something went wrong.
     * @throws LexicalException
     */
    public Token tokenizeIdentifierOrKeyword() throws LexicalException {
        final int STATE_END = 0;
        final int STATE_FIRST = 1;
        final int STATE_REST = 2;

        StringBuilder builder = new StringBuilder();
        Token token = null;
        int state = STATE_FIRST;

        do {
            char character = currentChar();

            switch (state) {
                case STATE_FIRST:
                    // We know that the current character is valid (letter or
                    // underscore), because this function would've never have
                    // been called otherwise. So we just add the character to
                    // the builder and move on to the next state.
                    builder.append(character);
                    moveBufferPtr(1);
                    state = STATE_REST;
                    break;

                case STATE_REST:
                    // The remaining characters can be letters, underscores,
                    // or numbers. We keep adding them to the builder until
                    // something different thant that appears.
                    if (character == '_' || Character.isLetter(character) ||
                        Character.isDigit(character)) {
                        builder.append(character);
                        moveBufferPtr(1);
                    } else {
                        // The lexeme of the identifier has ended. So now we
                        // create the token and move on to the end state.
                        // PS: maybe a keyword was found instead of an
                        // identifier. This possibility must be checked.
                        String lexeme = builder.toString();
                        TokenType type = tokenTable.search(lexeme);
                        if (type == null)
                            token = new Token(TokenType.IDENTIFIER, lexeme);
                        else
                            token = new Token(type, lexeme);
                        state = STATE_END;
                    }
                    break;
            }
        } while (state != STATE_END);

        return token;
    }

    /**
     * Detects the next numerical token (integer or real).
     *
     * NUMERO_INTEIRO
     *     : '0'..'9'+
     *     ;
     * NUMERO_REAL
     *     : ('0'..'9')+ '.' ('0'..'9')+
     *     ;
     *
     * @return A numerical token, or null if something went wrong.
     * @throws LexicalException
     */
    public Token tokenizeNumber() throws LexicalException {
        final int STATE_END = 0;
        final int STATE_INTEGER = 1;
        final int STATE_POINT = 2;
        final int STATE_DECIMAL = 3;

        StringBuilder builder = new StringBuilder();
        Token token = null;
        int state = STATE_INTEGER;

        do {
            char character = currentChar();

            switch (state) {
                case STATE_INTEGER:
                    // We are at the integer part of a number. Keep reading
                    // digits until anything non-numerical appears.
                    if (Character.isDigit(character)) {
                        builder.append(character);
                        moveBufferPtr(1);
                    } else if (character == '.') {
                        // A number can continue or not after a point, so there
                        // is more to check. Move on to the next state.
                        builder.append(character);
                        moveBufferPtr(1);
                        state = STATE_POINT;
                    } else {
                        // Not a digit, nor a point. The number has ended and
                        // it is an integer. So now we create its token and
                        // move on to the end state.
                        String lexeme = builder.toString();
                        token = new Token(TokenType.INTEGER_NUMBER, lexeme);
                        state = STATE_END;
                    }
                    break;

                case STATE_POINT:
                    // Finding another digit here means that we have a real
                    // (float) number. So we move on to the next state.
                    if (Character.isDigit(character)) {
                        builder.append(character);
                        moveBufferPtr(1);
                        state = STATE_DECIMAL;
                    } else {
                        // Finding anything different than a digit here means
                        // that the point is not part of the number and so the
                        // number is an integer. This may seem a little odd,
                        // since we could interpreted "x." as "x.0", but...
                        // that is the language specification.
                        // Thus, we have to delete the builder's last character
                        // and rewind the buffer pointer before moving to the
                        // end state.
                        builder.deleteCharAt(builder.length() - 1);
                        moveBufferPtr(-1);
                        String lexeme = builder.toString();
                        token = new Token(TokenType.INTEGER_NUMBER, lexeme);
                        state = STATE_END;
                    }
                    break;

                case STATE_DECIMAL:
                    // Decimal part of the number. Keep appending digits until
                    // anything different appears, indicating that the number
                    // has reached its end.
                    if (Character.isDigit(character)) {
                        builder.append(character);
                        moveBufferPtr(1);
                    } else {
                        String lexeme = builder.toString();
                        token = new Token(TokenType.REAL_NUMBER, lexeme);
                        state = STATE_END;
                    }
                    break;
            }
        } while (state != STATE_END);

        return token;
    }

    /**
     * Detects the next string token.
     *
     * CADEIA_LITERAL
     *     : '"' ~( '\n' )* '"'
     *     ;
     *
     * @return A string token, or null if something went wrong.
     * @throws LexicalException
     */
    public Token tokenizeString() throws LexicalException {
        final int STATE_END = 0;
        final int STATE_BEGIN = 1;
        final int STATE_CONTENT = 2;

        StringBuilder builder = new StringBuilder();
        Token token = null;
        int state = STATE_BEGIN;

        do {
            char character = currentChar();

            switch (state) {
                case STATE_BEGIN:
                    // We already know that the current character is the double
                    // quote that starts the string. So just add this character
                    // to the builder and move on to the next state.
                    builder.append(character);
                    moveBufferPtr(1);
                    state = STATE_CONTENT;
                    break;

                case STATE_CONTENT:
                    if (character == '"') {
                        // Finding another double quote means that the string
                        // has ended. So we just add this character to the
                        // builder and move on to the end state.
                        builder.append(character);
                        String lexeme = builder.toString();
                        token = new Token(TokenType.STRING_LITERAL, lexeme);
                        moveBufferPtr(1);
                        state = STATE_END;
                    } else if (character == '\0' || character == '\n') {
                        // String literals can't span multiple lines. So if a
                        // line break or EOF appears here, we have to rewind
                        // the buffer pointer to the first quote's position and
                        // move on to the end state. This way, the function is
                        // going to return null and the quote character will be
                        // treated as an unexpected symbol, raising a lexical
                        // error.
                        moveBufferPtr(-builder.length());
                        state = STATE_END;
                    } else {
                        // Just keep adding the string characters to the builder.
                        builder.append(character);
                        moveBufferPtr(1);
                    }
                    break;
            }
        } while (state != STATE_END);

        return token;
    }

    /**
     * Detects the next identifier token.
     *
     * IDENTIFICADOR
     *     : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
     *     ;
     *
     * @return An identifier token, or null if something went wrong.
     * @throws LexicalException
     */
    public Token tokenizeSymbol() throws LexicalException {
        final int STATE_END = 0;
        final int STATE_FIRST = 1;
        final int STATE_SECOND = 2;

        StringBuilder builder = new StringBuilder();
        Token token = null;
        int state = STATE_FIRST;

        do {
            char character = currentChar();

            switch (state) {
                case STATE_FIRST:
                    // After reading the first character of a keyword or symbol
                    // we can either move ahead to the next state, to read
                    // another symbol character, or check whether the current
                    // lexeme already represents a valid keyword/symbol and
                    // move straight to the end state if it does.
                    builder.append(character);
                    if (character == '<' || character == '>' ||
                        character == '.') {
                        moveBufferPtr(1);
                        state = STATE_SECOND;
                    } else {
                        String lexeme = builder.toString();
                        TokenType type = tokenTable.search(lexeme);
                        if (type != null)
                            token = new Token(type, lexeme);
                        moveBufferPtr(1);
                        state = STATE_END;
                        // If the lexeme doesn't represent any valid keyword
                        // or symbol, a lexical error will be raised (as
                        // assured by the null token that will be returned).
                    }
                    break;

                case STATE_SECOND:
                    // Here we can get the second character of a symbol, for
                    // those ones who have two characters. No matter what
                    // happens here, we go to the end state right after.
                    if (builder.charAt(0) == '<' && character == '-' ||
                        builder.charAt(0) == '<' && character == '>' ||
                        builder.charAt(0) == '<' && character == '=' ||
                        builder.charAt(0) == '>' && character == '=' ||
                        builder.charAt(0) == '.' && character == '.')
                        // Valid symbol with two characters.
                        builder.append(character);
                    else
                        // Actually it was a single-character key symbol, so
                        // rewind the buffer pointer and consider only the
                        // characters that came before it.
                        moveBufferPtr(-1);

                    // There is no need to check the token type here because at
                    // this point it is always a valid type.
                    String lexeme = builder.toString();
                    TokenType type = tokenTable.search(lexeme);
                    token = new Token(type, lexeme);
                    state = STATE_END;
                    break;
            }
        } while (state != STATE_END);

        return token;
    }
}
