package comp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Main class of the LA Compiler. The execution starts from here.
 */
public class LAComp {

    public static void main(String[] args) {

        // Check the input arguments.
        if (args.length < 2) {
            System.err.println("Error: too few arguments");
            System.err.println("Usage: <command> <inputFile> <outputFile>");
            System.exit(1);
        }

        // Get the standard I/O streams for backup.
        InputStream stdin = System.in;
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;

        // Create new file I/O streams.
        InputStream fin = null;
        PrintStream fout = null;
        try {
            fin = new FileInputStream(args[0]);
            fout = new PrintStream(new FileOutputStream(args[1]));
        } catch (FileNotFoundException e) {
            System.err.println("Error: file '" + args[0] + "' not found");
        }

        // Replace the standard I/O streams by the file streams.
        System.setIn(fin);
        System.setOut(fout);

        // Main loop.
        // ...

        // Recover the standard I/O streams.
        System.setIn(stdin);
        System.setOut(stdout);
        System.setErr(stderr);

        // Close the file streams.
        try {
            fin.close();
            fout.close();
        } catch (IOException e) {
            System.err.println("Error: failed at closing streams");
            System.err.println(e.getMessage());
        }

        System.out.println("Compilation finished");
    }
}
