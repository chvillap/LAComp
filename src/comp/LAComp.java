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
     * Reads some file and returns its contents as a string.
     *
     * @param path Path to the input file.
     * @return File contents as a string.
     */
    public String readFile(String path)
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
     * Writes some string into a file.
     *
     * @param path Path to the output file.
     * @param contents String to be written.
     */
    public void writeFile(String path, String contents)
    {
        // Write all the contents into the file at once.
        // If any exception occurs, print an error message and exit.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(contents);

        } catch (IOException e) {
            System.err.println("Error: Could not write file '" + path + "'");
            System.exit(1);
        }
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
        String inputCode = compiler.readFile(args[0]);

        // ...

        // Write all the output code at once in a file.
        // If any exception occurs, print an error message and exit.
        String outputCode = "/* (nothing for now) */";
        compiler.writeFile(args[1], outputCode);

        System.out.println("Compilation finished");
    }
}
