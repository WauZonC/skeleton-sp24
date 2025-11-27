package main;

import wordnet.WordNet;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final WordNet wordNet;
    public HyponymsHandler(WordNet wn) {
        wordNet = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();

        //start
        //end
        //k

        return wordNet.query(words);
    }
}
