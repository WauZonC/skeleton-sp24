import deque.LinkedListDeque61B;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;


public class LinkedListDeque61BTest {

    @Test
    @DisplayName("Iterator")
    public void iteratorTest() {
        LinkedListDeque61B<Integer> list = new LinkedListDeque61B<>();
        for (int i = 0; i < 10; i++) {
            list.addLast(i);
        }
        assertThat(list).containsExactly(0,1,2,3,4,5,6,7,8,9).inOrder();

        Iterator<Integer> iter = list.iterator();
        for (int i = 0; i < 10; i++) {
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
        LinkedListDeque61B<Integer> list = new LinkedListDeque61B<>();
        assertThat(list.toString()).isEqualTo("[]");

        for (int i = 0; i < 3; i++) {
            list.addLast(i);
        }

        assertThat(list.toString()).isEqualTo("[0, 1, 2]");
    }

    @Test
    @DisplayName("Equals")
    public void equalsTest() {
        LinkedListDeque61B<Integer> list1 = new LinkedListDeque61B<>();

        for (int i = 0; i < 3; i++) {
            list1.addLast(i);
        }

        LinkedListDeque61B<Integer> list2 = new LinkedListDeque61B<>();

        for (int i = 0; i < 3; i++) {
            list2.addLast(i);
        }

        assertThat(list1.equals(list2)).isEqualTo(true);

        list2.removeFirst();
        assertThat(list1.equals(list2)).isEqualTo(false);
    }
}
