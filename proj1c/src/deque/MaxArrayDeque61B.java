package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    private Comparator<T> comparator;
    /**@param c a comparator for MaxArrayDeque so that it has order
     * */
    public MaxArrayDeque61B(Comparator<T> c) {
        comparator = c;
    }

    public T max() {
        if (size() == 0) {
            return null;
        }
        T maxItem = get(0);
        for (int i = 0; i < size(); i++) {
            if (comparator.compare(get(i), maxItem) > 0) {
                maxItem = get(i);
            }
        }
        return maxItem;
    }

    public T max(Comparator<T> newComparator) {
        Comparator<T> previousComparator = comparator;
        comparator = newComparator;
        T returnValue = max();
        comparator = previousComparator;
        return returnValue;
    }
}
