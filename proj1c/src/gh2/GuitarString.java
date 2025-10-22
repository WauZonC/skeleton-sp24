package gh2;

import deque.ArrayDeque61B;
import deque.Deque61B;

//Note: This file will not compile until you complete the Deque61B implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    // TODO: uncomment the following line once you're ready to start this portion
    private Deque61B<Double> buffer;
    private int size;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        size = Math.round((float) ((long) SR / (long) frequency));
        buffer = new ArrayDeque61B<>();
        for (int i = 0; i < size; i++) {
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        for (int i = 0; i < size; i++) {
            buffer.removeFirst();
            buffer.addLast(Math.random() - 0.5);
        }
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double sum = (buffer.get(0) + buffer.get(1)) / 2.0;
        buffer.removeFirst();
        buffer.addLast(DECAY * sum);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}
    // TODO: Remove all comments that say TODO when you're done.
