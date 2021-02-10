/**
 * A class acting as a "client" using my PushbackTokenizerImpl adapter.
 * Ideally I'd put this stuff in the test directory, but the assignment specified putting some experiments here.
 * By Brandon Watkins
 */
package edu.isu.cs.cs2263.hw02;

import edu.isu.cs.cs2263.hw02.tokenizer.PushbackTokenizer;
import edu.isu.cs.cs2263.hw02.tokenizer.implementation.PushbackTokenizerImpl;

public class Driver {

    private static final String message1 = "Some random string to be tokenized.";
    private static final String message2 = "1 2 3 4 5 6 7 8 9 10";
    private static PushbackTokenizer tokenizer;

    public static void main(String[] args) {
        test();
    }

    /**
     * Coordinating my various PushbackTokenizer tests.
     */
    private static void test() {
        System.out.println("Tokenizing: \"" + message1 + "\"");
        tokenizer = new PushbackTokenizerImpl(message1);

        testSimpleWalkThrough();
        testSimplePushback();

        System.out.println("\r\nTokenizing: \"" + message2 + "\"");
        tokenizer = new PushbackTokenizerImpl(message2);

        testNextTokenVsPushbackNextToken();
        testNextTokenOutOfBounds();

        System.out.println("\r\nTokenizing: \"" + message1 + "\"");
        tokenizer = new PushbackTokenizerImpl(message1);

        testPushbackOutOfBounds();
    }

    /**
     * Performs and informs user of pushback().
     */
    private static void dec() {
        System.out.println("Pushing token back onto the tokenizer.");
        tokenizer.pushback();
    }

    /**
     * Performs and informs user of nextToken().
     */
    private static void inc() {
        System.out.printf("Reading next Token: \"%s\"\r\n", tokenizer.nextToken());
    }

    /**
     * Testing the ability to walk through the Tokens.
     */
    private static void testSimpleWalkThrough() {
        System.out.println("Walking through all tokens...");
        while(tokenizer.hasMoreTokens()) inc();
        System.out.println();
    }

    /**
     * Testing pushback() basic functionality.
     */
    private static void testSimplePushback() {
        System.out.println("Testing pushback():");
        dec();
        dec();
        inc();
    }

    /**
     * Testing whether nextToken() is equal to nextToken() -> pushback() -> nextToken().
     */
    private static void testNextTokenVsPushbackNextToken() {
        for (int i = 0; i < 10; i++) {
            String next = tokenizer.nextToken();
            tokenizer.pushback();
            String prevNext = tokenizer.nextToken();
            boolean equal = next.equals(prevNext);
            System.out.printf("Testing: nextToken == nextToken() -> pushback() -> nextToken():  %s:  %s %s %s\r\n", Boolean.toString(equal), next, equal == true ? "=" : "!=", prevNext);
        }
    }

    /**
     * Testing the adapter's ability to handle nextToken() requests when there are no more tokens.
     */
    private static void testNextTokenOutOfBounds() {
        System.out.println("\r\nTesting: nextToken() won't break the program if no more tokens available, and maintains \"pointer\"");
        System.out.println("(ie. attempting to retrieve 3 more elements than are available, a single pushback will point to the last token:)");
        inc(); // pointing after 10
        inc(); // pointing after 10
        inc(); // pointing after 10
        dec(); // pointing at 10
        inc(); // reads 10, then pointing after 10
    }

    /**
     * Testing the adapter's ability to handle pushback() requests when the pointer is already at "0".
     */
    private static void testPushbackOutOfBounds() {
        System.out.println("Testing: pushback() while at initial index results in no changes (or errors):");
        dec(); // pointing at first token
        dec(); // pointing at first token
        inc(); // reads first token, then pointing at 2nd token
        inc(); // reads 2nd token, then pointing at 3rd token
        dec(); // pointing at 2nd token
        dec(); // pointing at first token
        dec(); // pointing at first token
        inc(); // reads first token, then pointing at 2nd token
    }

}
