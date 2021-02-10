/**
 * A PushbackTokenizer adapter utilizing StringTokenizer as an adaptee
 * By Brandon Watkins
 */
package edu.isu.cs.cs2263.hw02.tokenizer.implementation;

import edu.isu.cs.cs2263.hw02.tokenizer.PushbackTokenizer;

import java.util.StringTokenizer;

public class PushbackTokenizerImpl implements PushbackTokenizer {
    private StringTokenizer tokenizer = null;
    private String stringToTokenize = null;
    private String delimiter = " \t\n\r\f";
    private boolean includeDelimiterTokens = false;

    public PushbackTokenizerImpl(String string) {
        tokenizer = new StringTokenizer(string);
        stringToTokenize = string;
    }
    public PushbackTokenizerImpl(String string, String delimiter) {
        tokenizer = new StringTokenizer(string, delimiter);
        stringToTokenize = string;
        this.delimiter = delimiter;
    }
    public PushbackTokenizerImpl(String string, String delimiter, boolean includeDelimiterTokens) {
        tokenizer = new StringTokenizer(string, delimiter, includeDelimiterTokens);
        stringToTokenize = string;
        this.delimiter = delimiter;
        this.includeDelimiterTokens = includeDelimiterTokens;
    }

    @Override
    public String nextToken() {
        if (tokenizer != null && tokenizer.hasMoreTokens()) return tokenizer.nextToken();
        else return "";
    }

    @Override
    public boolean hasMoreTokens() {
        if (tokenizer != null) return tokenizer.hasMoreTokens();
        else return false;
    }

    /**
     * I should probably just utilize a List<String>, to make back stepping easier, but as this is meant to use adaptee
     * StringTokenizer (not a List), I'm just going to recreate the tokenizer every time pushback() is called, which
     * I realize is incredibly inefficient.
     *
     * if already at beginning of tokenizer, this will do nothing (create a new tokenizer at the initial pointer)
     */
    @Override
    public void pushback() {
        if (tokenizer != null) {
            // add 1 to count: when looping through the new tokenizer, we want there to be 1 more token remaining than there is currently.
            int tokensRemaining = tokenizer.countTokens() + 1;
            tokenizer = new StringTokenizer(stringToTokenize, delimiter, includeDelimiterTokens);
            int totalNumberOfTokens = tokenizer.countTokens();
            for (int i = totalNumberOfTokens; i > tokensRemaining; i--) {
                tokenizer.nextToken();
            }
        }
    }
}
