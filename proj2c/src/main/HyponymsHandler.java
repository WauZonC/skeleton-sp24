package main;

import browser.NgordnetQueryType;
import ngrams.NGramMap;
import wordnet.WordNet;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final WordNet wordNet;
    private final NGramMap nGramMap;
    public HyponymsHandler(WordNet wn, NGramMap ngm) {
        wordNet = wn;
        nGramMap = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        NgordnetQueryType queryType = q.ngordnetQueryType();
        if (queryType == NgordnetQueryType.HYPONYMS) {
            return handleHyponyms(words, startYear, endYear, k);
        } else {
            return handleAncestors(words, startYear, endYear, k);
        }
    }

    /**
     * A Basic method specified for handling Hyponyms
     * @return A String of adequate words in alphabetic order
     */
    private String handleHyponyms(List<String> words, int startYear, int endYear, int k) {
        if (k == 0) {
            return wordNet.query(words);
        }

        TreeSet<String> querySet =  wordNet.queryForSet(words);
        PriorityQueue<Pair> pq = new PriorityQueue<>(new PairComparator());
        for (String word : querySet) {
            Double weight = nGramMap.weight(word, startYear, endYear);
            if (weight == null || weight.equals(0.0)) {
                continue;
            }
            pq.add(new Pair(weight, word));
        }
        return takeNThingsOutFromPQ(k, pq);
    }

    private String handleAncestors(List<String> words, int startYear, int endYear, int k) {

        if (k == 0) {
            return wordNet.ancestor(words);
        }
        TreeSet<String> querySet =  wordNet.ancestorForSet(words);
        PriorityQueue<Pair> pq = new PriorityQueue<>(new PairComparator());
        for (String word : querySet) {
            Double weight = nGramMap.weight(word, startYear, endYear);
            if (weight == null || weight.equals(0.0)) {
                continue;
            }
            pq.add(new Pair(weight, word));
        }
        return takeNThingsOutFromPQ(k, pq);
    }
    private String takeNThingsOutFromPQ(int k, PriorityQueue<Pair> pq) {
        TreeSet<String> returnSet = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            if (pq.isEmpty()) {
                break;
            }
            returnSet.add(pq.poll().string);
        }
        return returnSet.toString();
    }
    private class Pair {
        public final double weight;
        public final String string;
        public Pair(double w, String s) {
            weight = w;
            string = s;
        }
    }
    private class PairComparator implements Comparator<Pair> {
        public PairComparator() {
            super();
        }
        @Override
        public int compare(Pair o1, Pair o2) {
            double value = o1.weight - o2.weight;
            if (value > 0) {
                return -1;
            } else if (value < 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
