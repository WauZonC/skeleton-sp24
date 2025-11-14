package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    Map<String, TimeSeries> cacheOfWords;
    TimeSeries cacheOfCounts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        this.cacheOfWords = new HashMap<>();
        In wordsFile = new In(wordsFilename);
        while (wordsFile.hasNextLine()) {
            String line = wordsFile.readLine();
            String[] contents = line.split("\t");
            TimeSeries findTimeSeries = cacheOfWords.get(contents[0]);
            if (findTimeSeries == null) {
                findTimeSeries = new TimeSeries();
            }
            findTimeSeries.put(Integer.valueOf(contents[1]), Double.valueOf(contents[2]));
            cacheOfWords.put(contents[0], findTimeSeries);
        }

        this.cacheOfCounts = new TimeSeries();
        In countsFile = new In(countsFilename);
        while (countsFile.hasNextLine()) {
            String line = countsFile.readLine();
            String[] contents = line.split(",");
            cacheOfCounts.put(Integer.valueOf(contents[0]), Double.valueOf(contents[1]));
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries findTimeSeries = cacheOfWords.get(word);
        if (findTimeSeries == null) {
            return new TimeSeries();
        }
        return new TimeSeries(findTimeSeries, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        TimeSeries findTimeSeries = cacheOfWords.get(word);
        if (findTimeSeries == null) {
            return new TimeSeries();
        }
        return (TimeSeries) findTimeSeries.clone();
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return (TimeSeries) cacheOfCounts.clone();
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries returnTimeSeries = cacheOfWords.get(word);
        if (returnTimeSeries == null) {
            return new TimeSeries();
        }
        returnTimeSeries = new TimeSeries(returnTimeSeries, startYear, endYear);
        returnTimeSeries = returnTimeSeries.dividedBy(cacheOfCounts);

        return returnTimeSeries;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries returnTimeSeries = new TimeSeries();
        for (String word : words) {
            TimeSeries timeSeriesOfThisWord = new TimeSeries(cacheOfWords.get(word), startYear, endYear);
            returnTimeSeries = returnTimeSeries.plus(timeSeriesOfThisWord.dividedBy(cacheOfCounts));
        }
        return returnTimeSeries;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }
}
