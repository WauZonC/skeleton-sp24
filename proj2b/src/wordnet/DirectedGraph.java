package wordnet;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class DirectedGraph {
    private final ArrayList<Node> nodes;
    /**The Directed Graph for WordNet
     * With three methods:
     * 1. create nodes —— create a new node with contents from synset dataset
     * 2. make edge —— from a to b : Stored into the int array list of the node
     * 3. find reachable nodes —— iterate through its descendant
     * */
    public DirectedGraph() {
        nodes = new ArrayList<>();
    }

    /**
     * The basic method for adding node to the Directed Graph
     * @param label the number for the node's representation
     * @param synset a TreeSet storing all the synonyms
     * @param description optional description for the synonyms
     */
    public void createNode(int label, TreeSet<String> synset, String description) {
        nodes.add(new Node(label, synset, description));
    }

    /**
     * The basic method for adding edge to the Directed Graph
     * @param fromLabel the root of the arrow
     * @param toLabel the head of the arrow
     */
    public void makeEdge(int fromLabel, int toLabel) {
        nodes.get(fromLabel).descendants.add(toLabel);
    }

    /**
     * The basic method for finding reachable nodes
     * @param labels the sources (excluded)
     * @return a boolean array with reachable nodes marked true
     */
    public TreeSet<String> findReachableNodes(List<Integer> labels) {
        boolean[] markedList = new boolean[nodes.size()];
        TreeSet<String> returnSet = new TreeSet<>();
        for (int label : labels) {
            reachableNodes(label, markedList, returnSet);
        }
        return returnSet;
    }

    /**
     * A helper method for marking reachable nodes
     * */
    private void reachableNodes(int label, boolean[] markedList, TreeSet<String> set) {
        markedList[label] = true;
        set.addAll(nodes.get(label).synsets);
        for (int descendant : nodes.get(label).descendants) {
            if (!markedList[descendant]) {
                reachableNodes(descendant, markedList, set);
            }
        }
    }

    public List<Integer> findWord(String word) {
        List<Integer> returnList = new ArrayList<>();
        for (Node node : nodes) {
            if (node.synsets.contains(word)) {
                returnList.add(node.label);
            }
        }
        return returnList;
    }

    /**
     * The Node for Directed Graph
     * With label for repr
     * With int array list for descendants nodes' labels
     * With TreeSet String for synsets
     * With String for synonyms' description
     */
    private class Node {
        int label;
        ArrayList<Integer> descendants;
        TreeSet<String> synsets;
        String description;
        public Node(int label, TreeSet<String> synsets, String description) {
            this.label = label;
            this.synsets = synsets;
            this.description = description;
            this.descendants = new ArrayList<>();
        }
    }
}
