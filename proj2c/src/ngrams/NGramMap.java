package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Map;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
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
     * Constructs an NGramMap from wordsFilename and countsFilename.
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
     * Provides a TimeSeries containing the relative frequency per year of WORD between startYear
     * and endYear, inclusive of both ends. If the word is not in the data files, returns an empty
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
    public Double weight(String word, int startYear, int endYear) {
        TimeSeries returnTimeSeries = cacheOfWords.get(word);
        if (returnTimeSeries == null) {
            return null;
        }
        returnTimeSeries = new TimeSeries(returnTimeSeries, startYear, endYear);
        double wordCount = 0.0;
        double totalCount = 0.0;
        for (Map.Entry<Integer, Double> entry : returnTimeSeries.entrySet()) {
            totalCount += cacheOfCounts.get(entry.getKey());
            wordCount += entry.getValue();
        }
        return wordCount / totalCount;
    }
}
