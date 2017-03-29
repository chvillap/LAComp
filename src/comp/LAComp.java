package comp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import comp.lex.LexicalAnalyzer;
import comp.lex.LexicalException;
import comp.lex.Token;

/**
 * Main class of the LA Compiler.
 */
public class LAComp {

    /**
     * Main method. The execution starts from here.
     */
    public static void main(String[] args) {

        // Check the command line arguments.
        if (args.length < 2) {
            System.err.println("Error: Not enough arguments");
            System.err.println("Usage: <command> <inputFile> <outputFile>");
            System.exit(1);
        }

        String inputCode = readInputFile(args[0]);

        // Main loop.
        LexicalAnalyzer lexical = new LexicalAnalyzer(inputCode);
        try {
            while (true) {
                Token token = lexical.nextToken();
                if (token == null)
                    break;
                System.out.println(token);
            }
        } catch (LexicalException e) {
            System.err.println(e.getMessage());
        }

        String outputCode = "/* nothing */";
        writeOutputFile(args[1], outputCode);

        System.out.println("Compilation finished");
    }

    /**
     * Reads the input code from a file.
     *
     * @param path Path to the input file.
     * @return Input code as a string.
     */
    public static String readInputFile(String path) {

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
     * @param code Contents of the output code.
     */
    public static void writeOutputFile(String path, String code) {

        // Write the whole contents in the file at once.
        // If any exception occurs, print an error message and exit.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(code);

        } catch (IOException e) {
            System.err.println("Error: Could not write file '" + path + "'");
            System.exit(1);
        }
    }
}
