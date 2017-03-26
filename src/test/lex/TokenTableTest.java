package test.lex;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.assertEquals;

import comp.lex.TokenTable;
import comp.lex.TokenType;

/**
 * Unit tests for the comp.lex.TokenTable class.
 */
public class TokenTableTest {

    @BeforeClass
    public static void init() {
        System.out.println("================================================");
        System.out.println("TEST CLASS: comp.lex.TokenTableTest");
        System.out.println("================================================");
    }

    @Test
    public void testSearch() {

        TokenTable table = new TokenTable();

        String[] lexemes = {
            "algoritmo",
            "fim_algoritmo",
            "declare",
            "literal",
            "inteiro",
            "real",
            "logico",
            "leia",
            "escreva",
            "se",
            "entao",
            "senao",
            "fim_se",
            "caso",
            "seja",
            "fim_caso",
            "tipo",
            "para",
            "ate",
            "faca",
            "fim_para",
            "enquanto",
            "fim_enquanto",
            "procedimento",
            "fim_procedimento",
            "funcao",
            "fim_funcao",
            "ou",
            "e",
            "nao",
            "registro",
            "fim_registro",
            "var",
            "constante",
            "verdadeiro",
            "falso",
            "retorne",
            ":",
            ",",
            "^",
            "(",
            ")",
            "-",
            "%",
            "*",
            "/",
            "+",
            "&",
            "=",
            "<",
            ">",
            ".",
            "[",
            "]",
            "<-",
            "<>",
            "<=",
            ">=",
            ".."
        };

        TokenType[] types = {
            TokenType.KEYWORD_ALGORITMO,
            TokenType.KEYWORD_FIMALGORITMO,
            TokenType.KEYWORD_DECLARE,
            TokenType.KEYWORD_LITERAL,
            TokenType.KEYWORD_INTEIRO,
            TokenType.KEYWORD_REAL,
            TokenType.KEYWORD_LOGICO,
            TokenType.KEYWORD_LEIA,
            TokenType.KEYWORD_ESCREVA,
            TokenType.KEYWORD_SE,
            TokenType.KEYWORD_ENTAO,
            TokenType.KEYWORD_SENAO,
            TokenType.KEYWORD_FIMSE,
            TokenType.KEYWORD_CASO,
            TokenType.KEYWORD_SEJA,
            TokenType.KEYWORD_FIMCASO,
            TokenType.KEYWORD_TIPO,
            TokenType.KEYWORD_PARA,
            TokenType.KEYWORD_ATE,
            TokenType.KEYWORD_FACA,
            TokenType.KEYWORD_FIMPARA,
            TokenType.KEYWORD_ENQUANTO,
            TokenType.KEYWORD_FIMENQUANTO,
            TokenType.KEYWORD_PROCEDIMENTO,
            TokenType.KEYWORD_FIMPROCEDIMENTO,
            TokenType.KEYWORD_FUNCAO,
            TokenType.KEYWORD_FIMFUNCAO,
            TokenType.KEYWORD_OU,
            TokenType.KEYWORD_E,
            TokenType.KEYWORD_NAO,
            TokenType.KEYWORD_REGISTRO,
            TokenType.KEYWORD_FIMREGISTRO,
            TokenType.KEYWORD_VAR,
            TokenType.KEYWORD_CONSTANTE,
            TokenType.KEYWORD_VERDADEIRO,
            TokenType.KEYWORD_FALSO,
            TokenType.KEYWORD_RETORNE,
            TokenType.SYMBOL_COLON,
            TokenType.SYMBOL_COMMA,
            TokenType.SYMBOL_CARET,
            TokenType.SYMBOL_LEFT_PARENTHESIS,
            TokenType.SYMBOL_RIGHT_PARENTHESIS,
            TokenType.SYMBOL_SUBTRACTION,
            TokenType.SYMBOL_MODULUS,
            TokenType.SYMBOL_MULTIPLICATION,
            TokenType.SYMBOL_DIVISION,
            TokenType.SYMBOL_ADDITION,
            TokenType.SYMBOL_ADDRESS,
            TokenType.SYMBOL_EQUAL,
            TokenType.SYMBOL_LOWER_THAN,
            TokenType.SYMBOL_GREATER_THAN,
            TokenType.SYMBOL_POINT,
            TokenType.SYMBOL_LEFT_BRACKET,
            TokenType.SYMBOL_RIGHT_BRACKET,
            TokenType.SYMBOL_ASSIGNMENT,
            TokenType.SYMBOL_UNEQUAL,
            TokenType.SYMBOL_LOWER_THAN_OR_EQUAL,
            TokenType.SYMBOL_GREATER_THAN_OR_EQUAL,
            TokenType.SYMBOL_CONCATENATION
        };

        for (int i = 0; i < lexemes.length; ++i) {
            assertEquals(types[i], table.search(lexemes[i]));
        }
    }

    public static void main(String[] args) {
        JUnitCore.main("test.lex.TokenTableTest");
    }
}
