package test.lex;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import comp.LAComp;
import comp.lex.LexicalAnalyzer;
import comp.lex.LexicalException;
import comp.lex.Token;
import comp.lex.TokenType;

/**
 * Unit tests for the comp.lex.LexicalAnalyzer class.
 */
public class LexicalAnalyzerTest
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void init()
    {
        System.out.println("================================================");
        System.out.println("TEST CLASS: comp.lex.LexicalAnalyzerTest");
        System.out.println("================================================");
    }

    @Test
    public void testTokenizeComment()
    {
        // Unenclosed comment.
        String code1 = "";
        LexicalAnalyzer lex1 = new LexicalAnalyzer(code1);
        thrown.expect(LexicalException.class);
        lex1.tokenizeComment();

        // Unenclosed comment.
        String code2 = "comment";
        LexicalAnalyzer lex2 = new LexicalAnalyzer(code2);
        thrown.expect(LexicalException.class);
        lex2.tokenizeComment();

        // Valid comment.
        String code3 = "comment}";
        LexicalAnalyzer lex3 = new LexicalAnalyzer(code3);
        lex3.tokenizeComment();

        // Valid comment.
        String code4 = "{comment}";
        LexicalAnalyzer lex4 = new LexicalAnalyzer(code4);
        lex4.tokenizeComment();
    }

    @Test
    public void testTokenizeIdentifierOrKeyword()
    {
        TokenType[] tokenTypes = {
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
            TokenType.KEYWORD_RETORNE
        };

        // Valid keywords.
        String[] validKeywords = {
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
            "retorne"
        };
        for (int i = 0; i < validKeywords.length; ++i) {
            String code = validKeywords[i];
            LexicalAnalyzer lex = new LexicalAnalyzer(code);
            Token token = lex.tokenizeIdentifierOrKeyword();
            assertEquals(tokenTypes[i], token.getType());
            assertEquals(validKeywords[i], token.getLexeme());
        }

        // Valid identifier.
        String code1 = "identifier";
        LexicalAnalyzer lex1 = new LexicalAnalyzer(code1);
        Token token1 = lex1.tokenizeIdentifierOrKeyword();
        assertEquals(TokenType.IDENTIFIER, token1.getType());
        assertEquals("identifier", token1.getLexeme());

        // Valid identifier.
        String code2 = "i_d_e_n_t_i_f_i_e_r_";
        LexicalAnalyzer lex2 = new LexicalAnalyzer(code2);
        Token token2 = lex2.tokenizeIdentifierOrKeyword();
        assertEquals(TokenType.IDENTIFIER, token2.getType());
        assertEquals("i_d_e_n_t_i_f_i_e_r_", token2.getLexeme());

        // Valid identifier.
        String code3 = "id3n71f13r";
        LexicalAnalyzer lex3 = new LexicalAnalyzer(code3);
        Token token3 = lex3.tokenizeIdentifierOrKeyword();
        assertEquals(TokenType.IDENTIFIER, token3.getType());
        assertEquals("id3n71f13r", token3.getLexeme());

        // Valid identifier only in the beginning of the input.
        String code4 = "id.ent-if#ier$";
        LexicalAnalyzer lex4 = new LexicalAnalyzer(code4);
        Token token4 = lex4.tokenizeIdentifierOrKeyword();
        assertEquals(TokenType.IDENTIFIER, token4.getType());
        assertEquals("id", token4.getLexeme());

        // Valid identifier (NOT a keyword!).
        String code5 = "algoritmo_1";
        LexicalAnalyzer lex5 = new LexicalAnalyzer(code5);
        Token token5 = lex5.tokenizeIdentifierOrKeyword();
        assertEquals(TokenType.IDENTIFIER, token5.getType());
        assertEquals("algoritmo_1", token5.getLexeme());
    }

    @Test
    public void testTokenizeNumber()
    {
        // Valid integer number.
        String code1 = "123";
        LexicalAnalyzer lex1 = new LexicalAnalyzer(code1);
        Token token1 = lex1.tokenizeNumber();
        assertEquals(TokenType.INTEGER_NUMBER, token1.getType());
        assertEquals("123", token1.getLexeme());

        // Valid real number.
        String code2 = "123.456";
        LexicalAnalyzer lex2 = new LexicalAnalyzer(code2);
        Token token2 = lex2.tokenizeNumber();
        assertEquals(TokenType.REAL_NUMBER, token2.getType());
        assertEquals("123.456", token2.getLexeme());

        // Valid integer number only in the beginning of the input.
        String code3 = "123.";
        LexicalAnalyzer lex3 = new LexicalAnalyzer(code3);
        Token token3 = lex3.tokenizeNumber();
        assertEquals(TokenType.INTEGER_NUMBER, token3.getType());
        assertEquals("123", token3.getLexeme());

        // Valid real number only in the beginning of the input.
        String code4 = "123.456.789";
        LexicalAnalyzer lex4 = new LexicalAnalyzer(code4);
        Token token4 = lex4.tokenizeNumber();
        assertEquals(TokenType.REAL_NUMBER, token4.getType());
        assertEquals("123.456", token4.getLexeme());
    }

    @Test
    public void testTokenizeString()
    {
        // Valid string.
        String code1 = "\"\"";
        LexicalAnalyzer lex1 = new LexicalAnalyzer(code1);
        Token token1 = lex1.tokenizeString();
        assertEquals(TokenType.STRING_LITERAL, token1.getType());
        assertEquals("\"\"", token1.getLexeme());

        // Valid string.
        String code2 = "\"abc123!@#$&*+-/%\"";
        LexicalAnalyzer lex2 = new LexicalAnalyzer(code2);
        Token token2 = lex2.tokenizeString();
        assertEquals(TokenType.STRING_LITERAL, token2.getType());
        assertEquals("\"abc123!@#$&*+-/%\"", token2.getLexeme());

        // Valid string only in the beginning of the input.
        String code3 = "\"abc\"something";
        LexicalAnalyzer lex3 = new LexicalAnalyzer(code3);
        Token token3 = lex3.tokenizeString();
        assertEquals(TokenType.STRING_LITERAL, token3.getType());
        assertEquals("\"abc\"", token3.getLexeme());

        // Unenclosed string.
        String code4 = "\"abc";
        LexicalAnalyzer lex4 = new LexicalAnalyzer(code4);
        Token token4 = lex4.tokenizeString();
        assertNull(token4);

        // Forbidden line break in string.
        String code5 = "\"abc\n123\"";
        LexicalAnalyzer lex5 = new LexicalAnalyzer(code5);
        Token token5 = lex5.tokenizeString();
        assertNull(token5);
    }

    @Test
    public void testTokenizeSymbol()
    {
        TokenType[] tokenTypes = {
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

        // Valid symbols.
        String[] validSymbols = {
            ":", ",", "^", "(", ")", "-", "%", "*", "/", "+", "&", "=",
            "<", ">", ".", "[", "]", "<-", "<>", "<=", ">=", ".."
        };
        for (int i = 0; i < validSymbols.length; ++i) {
            String code = validSymbols[i];
            LexicalAnalyzer lex = new LexicalAnalyzer(code);
            Token token = lex.tokenizeSymbol();
            assertEquals(tokenTypes[i], token.getType());
            assertEquals(validSymbols[i], token.getLexeme());
        }

        // Invalid symbols.
        String[] invalidSymbols = {
            "", "abc", "123", "~", "#", "$", "_", "{}"
        };
        for (int i = 0; i < invalidSymbols.length; ++i) {
            String code = invalidSymbols[i];
            LexicalAnalyzer lex = new LexicalAnalyzer(code);
            Token token = lex.tokenizeSymbol();
            assertNull(token);
        }

        // Valid symbol only in the beginning of the input.
        String code1 = "<something";
        LexicalAnalyzer lex1 = new LexicalAnalyzer(code1);
        Token token1 = lex1.tokenizeSymbol();
        assertEquals(TokenType.SYMBOL_LOWER_THAN, token1.getType());
        assertEquals("<", token1.getLexeme());

        // Valid symbol only in the beginning of the input.
        String code2 = ">something";
        LexicalAnalyzer lex2 = new LexicalAnalyzer(code2);
        Token token2 = lex2.tokenizeSymbol();
        assertEquals(TokenType.SYMBOL_GREATER_THAN, token2.getType());
        assertEquals(">", token2.getLexeme());

        // Valid keyword only in the beginning of the input.
        String code3 = ".something";
        LexicalAnalyzer lex3 = new LexicalAnalyzer(code3);
        Token token3 = lex3.tokenizeSymbol();
        assertEquals(TokenType.SYMBOL_POINT, token3.getType());
        assertEquals(".", token3.getLexeme());
    }

    @Test
    public void testRunLexicalAnalysis()
    {
        LAComp compiler = new LAComp();

        // Get all files in the input directory.
        File inputFolder = new File("testcases/comp/lex/input");
        File[] inputFiles = inputFolder.listFiles();

        // Get all files in the output directory.
        File outputFolder = new File("testcases/comp/lex/output");
        File[] outputFiles = outputFolder.listFiles();

        for (int i = 0; i < inputFiles.length; ++i) {
            // Get the input code and its expected outputs.
            String inputCode = compiler.readFile(inputFiles[i].getPath());
            String outputCode = compiler.readFile(outputFiles[i].getPath());

            // Do the lexical analysis getting all messages.
            // In the end, compare them to the expected outputs.
            LexicalAnalyzer lexical = new LexicalAnalyzer(inputCode);
            StringBuilder outputBuilder = new StringBuilder();
            while (true) {
                try {
                    Token token = lexical.nextToken();
                    if (token == null)
                        break;
                    outputBuilder.append(token.toString() + "\n");

                } catch (LexicalException e) {
                    outputBuilder.append(e.getMessage() + "\n");
                }
            }
            assertEquals(outputCode, outputBuilder.toString());
        }
    }

    public static void main(String[] args)
    {
        JUnitCore.main("test.lex.LexicalAnalyzerTest");
    }
}
