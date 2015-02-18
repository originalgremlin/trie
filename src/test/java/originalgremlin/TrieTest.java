package originalgremlin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TrieTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TrieTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TrieTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
