import deque.ArrayDeque61B;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("Iterator")
    public void iteratorTest() {
        ArrayDeque61B<Integer> list = new ArrayDeque61B<>();
        for (int i = 0; i < 8; i++) {
            list.addLast(i);
        }
        assertThat(list).containsExactly(0,1,2,3,4,5,6,7).inOrder();

        Iterator<Integer> iter = list.iterator();
        for (int i = 0; i < 8; i++) {
            assertThat(iter.hasNext()).isEqualTo(true);
            assertThat(iter.next()).isEqualTo(i);
        }
        assertThat(iter.hasNext()).isEqualTo(false);

        int cnt = 0;
        for (int item : list) {
            assertThat(item).isEqualTo(cnt);
            cnt++;
        }
    }

    @Test
    @DisplayName("toString")
    public void toStringTest() {
        ArrayDeque61B<Integer> list = new ArrayDeque61B<>();
        assertThat(list.toString()).isEqualTo("[]");

        for (int i = 0; i < 3; i++) {
            list.addLast(i);
        }

        assertThat(list.toString()).isEqualTo("[0, 1, 2]");
    }

    @Test
    @DisplayName("Equals")
    public void equalsTest() {
        ArrayDeque61B<Integer> list1 = new ArrayDeque61B<>();

        ArrayDeque61B<Integer> list2 = new ArrayDeque61B<>();

        assertThat(list1.equals(list2)).isEqualTo(true);
        for (int i = 0; i < 3; i++) {
            list1.addLast(i);
        }
        for (int i = 0; i < 3; i++) {
            list2.addFirst(i);
        }

        assertThat(list1.equals(list2)).isEqualTo(false);

        list2.removeFirst();
        assertThat(list1.equals(list2)).isEqualTo(false);
    }
}
