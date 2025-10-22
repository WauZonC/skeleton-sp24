import org.junit.jupiter.api.*;

import java.util.Comparator;
import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    @Test
    @DisplayName("max")
    public void maxTest() {
        MaxArrayDeque61B<String> list = new MaxArrayDeque61B<>(new StringLengthComparator());
        assertThat(list.max()).isEqualTo(null);
        list.addLast("");
        assertThat(list.max()).isEqualTo("");
        list.addLast("123");
        assertThat(list.max()).isEqualTo("123");
        list.addLast("12");
        assertThat(list.max()).isEqualTo("123");
        list.removeLast();
        list.removeLast();
        assertThat(list.max()).isEqualTo("");
    }

    @Test
    @DisplayName("max with comparator")
    public void maxWithParameterTest() {
        MaxArrayDeque61B<String> list = new MaxArrayDeque61B<>(new StringLengthComparator());
        assertThat(list.max(Comparator.naturalOrder())).isEqualTo(null);
        list.addLast("");
        assertThat(list.max(Comparator.naturalOrder())).isEqualTo("");
        list.addLast("acc");
        assertThat(list.max(Comparator.naturalOrder())).isEqualTo("acc");
        list.addLast("b");
        assertThat(list.max(Comparator.naturalOrder())).isEqualTo("b");
        assertThat(list.max()).isEqualTo("acc");
        list.removeLast();
        list.removeLast();
        assertThat(list.max(Comparator.naturalOrder())).isEqualTo("");
    }
}
