import net.sf.saxon.functions.Minimax;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {

    private int length;
    private int maxSize;
    /**front-the first element of the array*/
    private int front;
    /**end-the one after last element of the array*/
    private int end;
    private T[] array;

    public ArrayDeque61B() {
        maxSize = 8;
        array = (T[]) new Object[maxSize];
        front = 0;
        end = 0;
        length = 0;
    }

    /** returns the in-bound index of a num.
     *
     * @param i the primitive index
     * @return the in-bound index
     */
    private int real(int i) {
        return Math.floorMod(i, maxSize);
    }

    /**a method to resize the array if it is full     */
    public void resize() {
        maxSize *= 2;
        T[] newArray = (T[]) new Object[maxSize];
        int i = front;
        for (int cnt = 0; cnt < length; cnt++) {
            newArray[cnt] = array[i];
            i = Math.floorMod(i + 1, length);
        }

        array = newArray;
        front = 0;
        end = length;
    }

    public void checkFull() {
        if (length == maxSize) {
            resize();
        }
    }

    @Override
    public void addFirst(T x) {
        checkFull();

        front = real(front - 1);
        array[front] = x;

        length++;
    }

    @Override
    public void addLast(T x) {
        checkFull();

        array[real(end)] = x;
        end++;

        length++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = real(front); i != real(end); i = real(i + 1)) {
            returnList.addLast(array[i]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
