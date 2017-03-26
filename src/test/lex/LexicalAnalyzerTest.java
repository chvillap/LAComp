package test.lex;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.assertEquals;

import comp.lex.LexicalAnalyzer;
import comp.lex.LexicalException;

/**
 * Unit tests for the comp.lex.LexicalAnalyzer class.
 */
public class LexicalAnalyzerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void init() {
        System.out.println("================================================");
        System.out.println("TEST CLASS: comp.lex.LexicalAnalyzerTest");
        System.out.println("================================================");
    }

    @Test
    public void testTokenizeComment() {
        String code1 = "";
        String code2 = "comment";
        String code3 = "comment}";
        String code4 = "{comment}";

        LexicalAnalyzer lex1 = new LexicalAnalyzer(code1);
        thrown.expect(LexicalException.class);
        lex1.tokenizeComment();

        LexicalAnalyzer lex2 = new LexicalAnalyzer(code2);
        thrown.expect(LexicalException.class);
        lex2.tokenizeComment();

        LexicalAnalyzer lex3 = new LexicalAnalyzer(code3);
        lex3.tokenizeComment();

        LexicalAnalyzer lex4 = new LexicalAnalyzer(code4);
        lex4.tokenizeComment();
    }

    @Test
    public void testTokenizeIdentifier() {

    }

    @Test
    public void testTokenizeNumber() {

    }

    @Test
    public void testTokenizeString() {

    }

    @Test
    public void testTokenizeKeywordOrSymbol() {

    }

    @Test
    public void testSuccess() {

    }

    @Test
    public void testUnclosedCommentError() {

    }

    @Test
    public void testUnclosedStringLiteralError() {

    }

    @Test
    public void testUnidentifiedSymbolError() {

    }

    public static void main(String[] args) {
        JUnitCore.main("test.lex.LexicalAnalyzerTest");
    }
}
