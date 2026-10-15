package bst;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BSTTest {

    @Test
    void newTreeShouldBeEmpty() {
        BST<Integer> bst = new BST<>();

        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());
        assertEquals(-1, bst.height());
        assertNull(bst.smallestElement());
        assertEquals(List.of(), toList(bst.inOrder()));
        assertEquals(List.of(), toList(bst.preOrder()));
        assertEquals(List.of(), toList(bst.postOrder()));
        assertTrue(bst.nodesByLevel().isEmpty());
    }

    @Test
    void insertShouldBuildValidSearchTreeAndTraversals() {
        BST<Integer> bst = new BST<>();
        bst.insert(8);
        bst.insert(3);
        bst.insert(10);
        bst.insert(1);
        bst.insert(6);
        bst.insert(14);
        bst.insert(4);
        bst.insert(7);
        bst.insert(13);

        assertFalse(bst.isEmpty());
        assertEquals(9, bst.size());
        assertEquals(3, bst.height());
        assertEquals(1, bst.smallestElement());
        assertEquals(List.of(1, 3, 4, 6, 7, 8, 10, 13, 14), toList(bst.inOrder()));
        assertEquals(List.of(8, 3, 1, 6, 4, 7, 10, 14, 13), toList(bst.preOrder()));
        assertEquals(List.of(1, 4, 7, 6, 3, 13, 14, 10, 8), toList(bst.postOrder()));
    }

    @Test
    void nodesByLevelShouldGroupNodesByDepth() {
        BST<Integer> bst = new BST<>();
        bst.insert(8);
        bst.insert(3);
        bst.insert(10);
        bst.insert(1);
        bst.insert(6);
        bst.insert(14);
        bst.insert(4);
        bst.insert(7);
        bst.insert(13);

        Map<Integer, List<Integer>> byLevel = bst.nodesByLevel();
        assertEquals(List.of(8), byLevel.get(0));
        assertEquals(List.of(3, 10), byLevel.get(1));
        assertEquals(List.of(1, 6, 14), byLevel.get(2));
        assertEquals(List.of(4, 7, 13), byLevel.get(3));
    }

    @Test
    void removeLeafShouldKeepTreeValid() {
        BST<Integer> bst = new BST<>();
        bst.insert(8);
        bst.insert(3);
        bst.insert(10);
        bst.insert(1);
        bst.insert(6);

        bst.remove(1);

        assertEquals(4, bst.size());
        assertEquals(3, bst.smallestElement());
        assertEquals(List.of(3, 6, 8, 10), toList(bst.inOrder()));
    }

    @Test
    void removeNodeWithOneChildShouldKeepTreeValid() {
        BST<Integer> bst = new BST<>();
        bst.insert(8);
        bst.insert(3);
        bst.insert(10);
        bst.insert(14);
        bst.insert(13);

        bst.remove(14);

        assertEquals(4, bst.size());
        assertEquals(List.of(3, 8, 10, 13), toList(bst.inOrder()));
        assertEquals(2, bst.height());
    }

    @Test
    void removeNodeWithTwoChildrenShouldUseInOrderPredecessor() {
        BST<Integer> bst = new BST<>();
        bst.insert(8);
        bst.insert(3);
        bst.insert(10);
        bst.insert(1);
        bst.insert(6);
        bst.insert(4);
        bst.insert(7);

        bst.remove(3);

        assertEquals(6, bst.size());
        assertEquals(List.of(1, 4, 6, 7, 8, 10), toList(bst.inOrder()));
        assertEquals(List.of(8, 4, 1, 6, 7, 10), toList(bst.preOrder()));
    }

    @Test
    void removeRootWithTwoChildrenShouldKeepSearchTreeOrder() {
        BST<Integer> bst = new BST<>();
        bst.insert(8);
        bst.insert(3);
        bst.insert(10);
        bst.insert(1);
        bst.insert(6);
        bst.insert(14);
        bst.insert(4);
        bst.insert(7);
        bst.insert(13);

        bst.remove(8);

        assertEquals(8, bst.size());
        assertEquals(List.of(1, 3, 4, 6, 7, 10, 13, 14), toList(bst.inOrder()));
    }

    @Test
    void removeMissingElementShouldNotChangeTree() {
        BST<Integer> bst = new BST<>();
        bst.insert(5);
        bst.insert(2);
        bst.insert(9);

        bst.remove(7);

        assertEquals(3, bst.size());
        assertEquals(List.of(2, 5, 9), toList(bst.inOrder()));
    }

    @Test
    void isBalancedShouldDetectSkewedTree() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        bst.insert(2);
        bst.insert(3);
        bst.insert(4);
        bst.insert(5);

        assertFalse(bst.isBalanced());
    }

    private static <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for (T item : iterable) {
            list.add(item);
        }
        return list;
    }
}
