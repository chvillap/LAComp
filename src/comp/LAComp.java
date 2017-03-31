package comp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import comp.lex.LexicalAnalyzer;
import comp.lex.LexicalException;
import comp.lex.Token;

/**
 * Main class of the LA Compiler.
 */
public class LAComp
{
    /**
     * Reads the input code from a file.
     *
     * @param path Path to the input file.
     * @return Input code as a string.
     */
    public String readInputFile(String path)
    {
        // Read the file line by line and save the input code into a string.
        // If any exception occurs, print an error message and exit.
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = null;
            while ((line = reader.readLine()) != null)
                builder.append(line + "\n");

        } catch (IOException e) {
            System.err.println("Error: Could not read file '" + path + "'");
            System.exit(1);
        }
        return builder.toString();
    }

    /**
     * Writes the output code to a file.
     *
     * @param path Path to the output file.
     */
    public void writeOutputFile(String path, String code)
    {
        // Write the whole contents in the file at once.
        // If any exception occurs, print an error message and exit.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(code);

        } catch (IOException e) {
            System.err.println("Error: Could not write file '" + path + "'");
            System.exit(1);
        }
    }

    /**
     * Test function that runs a lexical analysis on some input code and gets
     * the output messages (not the code!) in a string.
     *
     * @param inputCode Input code as a string.
     * @return String of output messages.
     */
    public String runLexicalAnalysis(String inputCode)
    {
        LexicalAnalyzer lexical = new LexicalAnalyzer(inputCode);
        StringBuilder outputBuilder = new StringBuilder();

        while (true) {
            try {
                Token token = lexical.nextToken();
                if (token == null)
                    break;
                // System.out.println(token.toString());
                outputBuilder.append(token.toString());

            } catch (LexicalException e) {
                // System.err.println(e.getMessage());
                outputBuilder.append(e.getMessage());
            }
        }
        return outputBuilder.toString();
    }

    /**
     * Main method. The execution starts from here.
     */
    public static void main(String[] args)
    {
        // Check the command line arguments.
        if (args.length < 2) {
            System.err.println("Error: Not enough arguments");
            System.err.println("Usage: <command> <inputFile> <outputFile>");
            System.exit(1);
        }

        // Instantiate the compiler and get the input code.
        LAComp compiler = new LAComp();
        String inputCode = compiler.readInputFile(args[0]);

        // ...

        // Write all the output code at once in a file.
        // If any exception occurs, print an error message and exit.
        String outputCode = "/* (nothing for now) */";
        compiler.writeOutputFile(args[1], outputCode);

        System.out.println("Compilation finished");
    }
}
