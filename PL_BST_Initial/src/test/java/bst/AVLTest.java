package bst;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AVLTest {

    @Test
    void newAvlShouldBeEmpty() {
        AVL<Integer> avl = new AVL<>();

        assertTrue(avl.isEmpty());
        assertEquals(0, avl.size());
        assertEquals(-1, avl.height());
    }

    @Test
    void insertShouldBalanceWithLeftLeftRotation() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(30);
        avl.insert(20);
        avl.insert(10);

        assertEquals(List.of(20, 10, 30), toList(avl.preOrder()));
        assertEquals(List.of(10, 20, 30), toList(avl.inOrder()));
        assertEquals(1, avl.height());
        assertTrue(avl.isBalanced());
    }

    @Test
    void insertShouldBalanceWithRightRightRotation() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(10);
        avl.insert(20);
        avl.insert(30);

        assertEquals(List.of(20, 10, 30), toList(avl.preOrder()));
        assertEquals(List.of(10, 20, 30), toList(avl.inOrder()));
        assertEquals(1, avl.height());
        assertTrue(avl.isBalanced());
    }

    @Test
    void insertShouldBalanceWithLeftRightRotation() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(30);
        avl.insert(10);
        avl.insert(20);

        assertEquals(List.of(20, 10, 30), toList(avl.preOrder()));
        assertEquals(List.of(10, 20, 30), toList(avl.inOrder()));
        assertEquals(1, avl.height());
        assertTrue(avl.isBalanced());
    }

    @Test
    void insertShouldBalanceWithRightLeftRotation() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(10);
        avl.insert(30);
        avl.insert(20);

        assertEquals(List.of(20, 10, 30), toList(avl.preOrder()));
        assertEquals(List.of(10, 20, 30), toList(avl.inOrder()));
        assertEquals(1, avl.height());
        assertTrue(avl.isBalanced());
    }

    @Test
    void largerInsertSetShouldRemainBalanced() {
        AVL<Integer> avl = new AVL<>();
        for (int value : List.of(50, 20, 70, 10, 30, 60, 80, 25, 27, 26, 65, 85)) {
            avl.insert(value);
        }

        assertEquals(List.of(10, 20, 25, 26, 27, 30, 50, 60, 65, 70, 80, 85), toList(avl.inOrder()));
        assertEquals(12, avl.size());
        assertTrue(avl.isBalanced());
    }

    @Test
    void removeShouldRebalanceTree() {
        AVL<Integer> avl = new AVL<>();
        for (int value : List.of(30, 20, 40, 10, 25, 35, 50, 5, 15, 45, 60)) {
            avl.insert(value);
        }

        avl.remove(5);
        avl.remove(10);
        avl.remove(15);

        assertEquals(List.of(20, 25, 30, 35, 40, 45, 50, 60), toList(avl.inOrder()));
        assertEquals(8, avl.size());
        assertTrue(avl.isBalanced());
    }

    @Test
    void removeRootWithTwoChildrenShouldKeepAvlBalanced() {
        AVL<Integer> avl = new AVL<>();
        for (int value : List.of(40, 20, 60, 10, 30, 50, 70, 25, 35)) {
            avl.insert(value);
        }

        avl.remove(40);

        assertEquals(List.of(10, 20, 25, 30, 35, 50, 60, 70), toList(avl.inOrder()));
        assertEquals(8, avl.size());
        assertTrue(avl.isBalanced());
    }

    @Test
    void equalsShouldCompareTreeStructureAndValues() {
        AVL<Integer> first = new AVL<>();
        AVL<Integer> second = new AVL<>();
        for (int value : List.of(30, 20, 40, 10, 25, 35, 50)) {
            first.insert(value);
            second.insert(value);
        }

        AVL<Integer> different = new AVL<>();
        for (int value : List.of(30, 20, 40, 10, 26, 35, 50)) {
            different.insert(value);
        }

        assertTrue(first.equals(second));
        assertFalse(first.equals(different));
        assertFalse(first.equals(null));
    }

    @Test
    void insertShouldTriggerTwoRotationsForLeftRightCase() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(30);
        avl.insert(10);
        avl.insert(20);

        assertEquals(List.of(20, 10, 30), toList(avl.preOrder()));
        assertEquals(List.of(10, 20, 30), toList(avl.inOrder()));
        assertTrue(avl.isBalanced());
    }

    @Test
    void insertShouldTriggerTwoRotationsForRightLeftCase() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(10);
        avl.insert(30);
        avl.insert(20);

        assertEquals(List.of(20, 10, 30), toList(avl.preOrder()));
        assertEquals(List.of(10, 20, 30), toList(avl.inOrder()));
        assertTrue(avl.isBalanced());
    }

    private static <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for (T item : iterable) {
            list.add(item);
        }
        return list;
    }
}
