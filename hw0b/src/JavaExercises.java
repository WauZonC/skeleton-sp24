import java.util.ArrayList;
import java.util.List;

public class JavaExercises {

    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() {
        int n = 6;
        int[] dice = new int[n];
        for (int i = 0; i < n; i++) {
            dice[i] = i + 1;
        }
        return dice;
    }

    /** Returns the order depending on the customer.
     *  If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     *  If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     *  In any other case, return an empty String[] of size 3. */
    public static String[] takeOrder(String customer) {
        String[] res;
        if (customer.equals("Ergun")) {
            res = new String[]{"beyti", "pizza", "hamburger", "tea"};
        } else if (customer.equals("Erik")) {
            res = new String[]{"sushi", "pasta", "avocado", "coffee"};
        } else {
            res = new String[3];
        }
        return res;
    }

    /** Returns the positive difference between the maximum element and minimum element of the given array.
     *  Assumes array is nonempty. */
    public static int findMinMax(int[] array) {
        int min = array[0];
        int max = array[0];
        for (int num : array) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
        }
        return max - min;
    }

    /**
      * Uses recursion to compute the hailstone sequence as a list of integers starting from an input number n.
      * Hailstone sequence is described as:
      *    - Pick a positive integer n as the start
      *        - If n is even, divide n by 2
      *        - If n is odd, multiply n by 3 and add 1
      *    - Continue this process until n is 1
      */
    public static List<Integer> hailstone(int n) {
        return hailstoneHelper(n, new ArrayList<>());
    }

    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        list.add(x);
        while (x != 1) {
            if (x % 2 == 0) {
                x = x / 2;
                list.add(x);
            } else {
                x = x * 3 + 1;
                list.add(x);
            }
        }
        return list;
    }

}
