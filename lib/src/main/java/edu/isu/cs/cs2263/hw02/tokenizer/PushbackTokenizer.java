/**
 * A tokenizer interface with the ability to "back up", to be able to re-read the previously read token.
 * By Brandon Watkins
 */
package edu.isu.cs.cs2263.hw02.tokenizer;

public interface PushbackTokenizer {
    /**
     * Retrieves the next token.
     * @return (String) the next token.
     */
    String nextToken();

    /**
     * Determines whether there are more tokens.
     * @return (boolean) true if there are more tokens, else false.
     */
    boolean hasMoreTokens();

    /**
     * The token last read is pushed back, so it can be read again using nextToken().
     */
    void pushback();

}
