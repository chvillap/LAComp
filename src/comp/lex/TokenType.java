package comp.lex;

/**
 * Enumeration of all supported token types: identifiers, string literals,
 * integer numbers, real numbers, and several keywords and symbols.
 */
public enum TokenType {

    IDENTIFIER,
    STRING_LITERAL,
    INTEGER_NUMBER,
    REAL_NUMBER,
    KEYWORD_ALGORITMO,             // algorithm
    KEYWORD_FIMALGORITMO,          // end-algorithm
    KEYWORD_DECLARE,               // declare
    KEYWORD_LITERAL,               // literal (string)
    KEYWORD_INTEIRO,               // integer
    KEYWORD_REAL,                  // real (float)
    KEYWORD_LOGICO,                // logical (boolean)
    KEYWORD_LEIA,                  // read
    KEYWORD_ESCREVA,               // write (print)
    KEYWORD_SE,                    // if
    KEYWORD_ENTAO,                 // then
    KEYWORD_SENAO,                 // else
    KEYWORD_FIMSE,                 // end-if
    KEYWORD_CASO,                  // case (switch)
    KEYWORD_SEJA,                  // is (case)
    KEYWORD_FIMCASO,               // end-case
    KEYWORD_TIPO,                  // type (typedef)
    KEYWORD_PARA,                  // for
    KEYWORD_ATE,                   // until
    KEYWORD_FACA,                  // do
    KEYWORD_FIMPARA,               // end-for
    KEYWORD_ENQUANTO,              // while
    KEYWORD_FIMENQUANTO,           // end-while
    KEYWORD_PROCEDIMENTO,          // procedure
    KEYWORD_FIMPROCEDIMENTO,       // end-procedure
    KEYWORD_FUNCAO,                // function
    KEYWORD_FIMFUNCAO,             // end-function
    KEYWORD_OU,                    // logical OR
    KEYWORD_E,                     // logical AND
    KEYWORD_NAO,                   // logical NOT
    KEYWORD_REGISTRO,              // register (struct)
    KEYWORD_FIMREGISTRO,           // end-register
    KEYWORD_VAR,                   // var (reference)
    KEYWORD_CONSTANTE,             // constant
    KEYWORD_VERDADEIRO,            // true
    KEYWORD_FALSO,                 // false
    KEYWORD_RETORNE,               // return
    SYMBOL_COLON,                  // :
    SYMBOL_COMMA,                  // ,
    SYMBOL_CARET,                  // ^
    SYMBOL_LEFT_PARENTHESIS,       // (
    SYMBOL_RIGHT_PARENTHESIS,      // )
    SYMBOL_SUBTRACTION,            // -
    SYMBOL_MODULUS,                // %
    SYMBOL_MULTIPLICATION,         // *
    SYMBOL_DIVISION,               // /
    SYMBOL_ADDITION,               // +
    SYMBOL_ADDRESS,                // &
    SYMBOL_EQUAL,                  // =
    SYMBOL_LOWER_THAN,             // <
    SYMBOL_GREATER_THAN,           // >
    SYMBOL_POINT,                  // .
    SYMBOL_LEFT_BRACKET,           // [
    SYMBOL_RIGHT_BRACKET,          // ]
    SYMBOL_ASSIGNMENT,             // <-
    SYMBOL_UNEQUAL,                // <>
    SYMBOL_LOWER_THAN_OR_EQUAL,    // <=
    SYMBOL_GREATER_THAN_OR_EQUAL,  // >=
    SYMBOL_CONCATENATION           // ..
}
