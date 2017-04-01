package test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import comp.LAComp;

/**
 * Unit tests for the comp.LAComp class.
 */
public class LACompTest
{
    @BeforeClass
    public static void init()
    {
        System.out.println("================================================");
        System.out.println("TEST CLASS: comp.LACompTest");
        System.out.println("================================================");
    }

    @Test
    public void testReadFile()
    {
        LAComp compiler = new LAComp();
        String path = "testcases/comp/testReadFile.txt";
        assertEquals("Line 1\nLine 2\n", compiler.readFile(path));
    }

    @Test
    public void testWriteFile()
    {
        LAComp compiler = new LAComp();
        String path = "testcases/comp/testWriteFile.txt";
        compiler.writeFile(path, "Line 1\nLine 2\n");
        assertEquals("Line 1\nLine 2\n", compiler.readFile(path));
    }

    @Test
    public void testCompilationSuccess()
    {

    }

    @Test
    public void testCompilationFail()
    {

    }

    public static void main(String[] args)
    {
        JUnitCore.main("test.LACompTest");
    }
}
