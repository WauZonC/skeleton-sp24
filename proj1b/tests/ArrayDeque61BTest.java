import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    @DisplayName("AddFirst no oversize")
    public void addFirstNoOversizeTest() {
        ArrayDeque61B<Integer> test1 = new ArrayDeque61B<>();
        for (int i = 0; i < 3; i++) {
            test1.addFirst(i);
        }
        assertThat(test1.toList()).containsExactly(2, 1, 0).inOrder();
    }

    @Test
    @DisplayName("AddFirst Oversize")
    public void addFirstOversizeTest() {
        ArrayDeque61B<Integer> test2 = new ArrayDeque61B<>();
        for (int i = 0; i < 10; i++) {
            test2.addFirst(i);
        }
        assertThat(test2.toList()).containsExactly(9, 8, 7, 6, 5, 4, 3, 2, 1, 0).inOrder();
    }

    @Test
    @DisplayName("AddLast no oversize")
    public void addLastNoOversizeTest() {
        ArrayDeque61B<Integer> test1 = new ArrayDeque61B<>();
        for (int i = 0; i < 3; i++) {
            test1.addLast(i);
        }
        assertThat(test1.toList()).containsExactly(0, 1, 2).inOrder();
    }

    @Test
    @DisplayName("AddLast Oversize")
    public void addLastOversizeTest() {
        ArrayDeque61B<Integer> test2 = new ArrayDeque61B<>();
        for (int i = 0; i < 10; i++) {
            test2.addLast(i);
        }
        assertThat(test2.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).inOrder();
    }

    @Test
    @DisplayName("AddLast and AddFirst Oversive")
    public void addLastFirstOversizeTest() {
        ArrayDeque61B<Integer> test1 = new ArrayDeque61B<>();
        for (int i = 0; i < 10; i++) {
            test1.addFirst(i);
        }
        for (int i = 0; i < 10; i++) {
            test1.addLast(i + 10);
        }
        assertThat(test1.toList()).containsExactly(9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19).inOrder();
    }

    @Test
    @DisplayName("RemoveFirst no sizeDown")
    public void removeFirstTest() {
        ArrayDeque61B<Integer> test1 = new ArrayDeque61B<>();
        assertThat(test1.removeFirst()).isEqualTo(null);
        assertThat(test1.size()).isEqualTo(0);
        for (int i = 0; i < 10; i++) {
            test1.addFirst(i);
        }
        assertThat(test1.removeFirst()).isEqualTo(9);
        assertThat(test1.size()).isEqualTo(9);
    }

    @Test
    @DisplayName("RemoveFirst with sizeDown")
    public void removeFirstWithSizeDown() {
        ArrayDeque61B<Integer> test1 = new ArrayDeque61B<>();
        assertThat(test1.removeFirst()).isEqualTo(null);
        assertThat(test1.size()).isEqualTo(0);
        for (int i = 0; i < 20; i++) {
            test1.addFirst(i);
        }
        assertThat(test1.toList()).containsExactly(19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        assertThat(test1.getMaxSize()).isEqualTo(32);
        for (int i = 0; i < 15; i++) {
            assertThat(test1.removeFirst()).isEqualTo(19 - i);
        }
        assertThat(test1.getMaxSize()).isEqualTo(16);
        assertThat(test1.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("RemoveLast with sizeDown")
    public void removeLastWithSizeDown() {
        ArrayDeque61B<Integer> test1 = new ArrayDeque61B<>();
        assertThat(test1.removeLast()).isEqualTo(null);
        assertThat(test1.size()).isEqualTo(0);
        for (int i = 0; i < 20; i++) {
            test1.addFirst(i);
        }
        assertThat(test1.toList()).containsExactly(19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        assertThat(test1.getMaxSize()).isEqualTo(32);
        for (int i = 0; i < 15; i++) {
            assertThat(test1.removeLast()).isEqualTo(i);
        }
        assertThat(test1.getMaxSize()).isEqualTo(16);
        assertThat(test1.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("get")
    public void getTest() {
        ArrayDeque61B<Integer> test1 = new ArrayDeque61B<>();
        for (int i = 0; i < 20; i++) {
            test1.addLast(i);
        }
        for (int i = 0; i < 20; i++) {
            assertThat(test1.get(i)).isEqualTo(i);
        }
        assertThat(test1.get(-1)).isEqualTo(null);
        assertThat(test1.get(100)).isEqualTo(null);
    }

    @Test
    @DisplayName("get recursive")
    public void getRecursiveTest() {
        ArrayDeque61B<Integer> test1 = new ArrayDeque61B<>();
        for (int i = 0; i < 20; i++) {
            test1.addLast(i);
        }
        for (int i = 0; i < 20; i++) {
            assertThat(test1.getRecursive(i)).isEqualTo(i);
        }
        assertThat(test1.getRecursive(-1)).isEqualTo(null);
        assertThat(test1.getRecursive(100)).isEqualTo(null);
    }
}
