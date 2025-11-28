package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import wordnet.WordNet;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        return new HyponymsHandler(new WordNet(synsetFile, hyponymFile), new NGramMap(wordFile, countFile));
    }
}
