package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.List;
import java.util.TreeSet;

public class WordNet {
    private final DirectedGraph directedGraph;

    public WordNet(String synsetFile, String hyponymFile) {
        directedGraph = new DirectedGraph();
        In in = new In(synsetFile);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] contents = line.split(",");
            directedGraph.createNode(Integer.parseInt(contents[0]),
                    new TreeSet<String>(List.of(contents[1].split(" "))), contents[2]);
        }
        in = new In(hyponymFile);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] contents = line.split(",");
            int head = Integer.parseInt(contents[0]);
            for (int i = 1; i < contents.length; i++) {
                directedGraph.makeEdge(head, Integer.parseInt(contents[i]));
            }
        }
    }

    /**
     * The Basic Query Version with k remains zero
     * @param words the words waiting to be queried
     * @return a String in List form for adequate synsets in lexical order
     */
    public String query(List<String> words) {
        return queryForSet(words).toString();
    }

    /**
     * The advanced Query Version
     * @param words the words to be queried
     * @return a TreeSet of words
     */
    public TreeSet<String> queryForSet(List<String> words) {
        TreeSet<String> returnSet = null;
        for (String word : words) {
            List<Integer> labels = directedGraph.findWord(word);
            TreeSet<String> synonyms = directedGraph.findReachableNodes(labels);
            if (returnSet == null) {
                returnSet = synonyms;
            } else {
                returnSet.retainAll(synonyms);
            }
        }
        return returnSet;
    }

    public TreeSet<String> ancestorForSet(List<String> words) {
        TreeSet<String> returnSet = null;
        for (String word : words) {
            List<Integer> labels = directedGraph.findWord(word);
            TreeSet<String> synonyms = directedGraph.reachableAncestors(labels);
            if (returnSet == null) {
                returnSet = synonyms;
            } else {
                returnSet.retainAll(synonyms);
            }
        }
        return returnSet;
    }

    public String ancestor(List<String> words) {
        return ancestorForSet(words).toString();
    }
}
