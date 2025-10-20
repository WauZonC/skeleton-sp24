import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {

    private int length;
    private int maxSize;
    /**the first element of the array*/
    private int front;
    /**the one after last element of the array*/
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
        copyAndChange(maxSize / 2);
    }

    /**a method to changing the array to a maxSize-length new array
     * @param prevLength what maxSize used to be*/
    public void copyAndChange(int prevLength) {
        T[] newArray = (T[]) new Object[maxSize];
        int i = front;
        for (int cnt = 0; cnt < length; cnt++) {
            newArray[cnt] = array[i];
            i = Math.floorMod(i + 1, prevLength);
        }

        array = newArray;
        front = 0;
        end = length;
    }

    /**check whether an array is fully to be resized*/
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
        return length == 0;
    }

    @Override
    public int size() {
        return length;
    }

    public int getMaxSize() {
        return maxSize;
    }

    /**a method checking that if the actual length is less than
     * 25% maxSize and call sizeDown*/
    public void checkEmpty() {
        if (length <= maxSize / 4 && maxSize >= 8 * 2) {
            sizeDown();
        }
    }

    /**a method to sizing down the array list*/
    public void sizeDown() {
        maxSize = Math.floorDiv(maxSize, 2);
        copyAndChange(maxSize * 2);
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T returnValue = array[front];
        array[front] = null;
        front = real(front + 1);
        length--;
        checkEmpty();
        return returnValue;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        end = real(end - 1);
        T returnValue = array[end];
        length--;
        checkEmpty();
        return returnValue;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= length) {
            return null;
        }
        return array[real(front + index)];
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= length) {
            return null;
        }
        return getRecursiveHelper(front, index);
    }

    /**a helper method for getRecursive*/
    private T getRecursiveHelper(int present, int index) {
        return index == 0 ? array[present] : getRecursiveHelper(real(present + 1), index - 1);
    }
}
