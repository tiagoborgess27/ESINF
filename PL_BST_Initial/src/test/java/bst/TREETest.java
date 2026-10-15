package bst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;


public class TREETest {
    Integer[] arr = {20,15,10,13,8,17,40,50,30,7};
    int[] nnodes= {1,2,4,2,1};
    Integer[] inorderT= {7,8,10,13,15,17,20,30,40,50};
    Integer[] posorderT= {7,8,13,10,17,15,30,50,40,20};
    Integer[] bstAscDes = {7, 8, 10, 13, 15, 17, 20, 50, 40, 30};
    TREE<Integer> instance;
        
    public TREETest() {
    }
    
    @org.junit.jupiter.api.BeforeEach
    public void setUp(){
        instance = new TREE<>();
        for(int i :arr)
            instance.insert(i);        
    }
    
    
    /**
     * Test of path method, of class TREE.
     */
    @Test
    public void testpath() {
        Integer[] expectedResult = {20, 15, 10, 8, 7};
        assertArrayEquals(expectedResult, instance.path(7).toArray());
        Integer[] expectedResult2 = {20, 15, 10, 13};
        assertArrayEquals(expectedResult2, instance.path(13).toArray());
        assertNull(instance.path(80));
        assertNull(instance.path(45));
    }

    /**
     * Test of leafs method, of class TREE.
     */
    @Test
    public void testleafs() {
        Set<Integer> expectedResult = new HashSet<>(Arrays.asList(7, 13, 17, 30, 50));
        assertEquals(expectedResult, instance.leafs());
    }

    /**
     * Test of range method, of class TREE.
     */
    @Test
    public void testrange() {
        Integer[] expectedResult = {7, 50};
        assertArrayEquals(expectedResult, instance.range());
    }

    /**
     * Test of diameter method, of class TREE.
     */
    @Test
    public void testdiameter() {
        Set<Integer> expectedResult = new HashSet<>(Arrays.asList(7, 8, 10, 15, 20, 30, 40));
        assertEquals(expectedResult, instance.diameter());
    }

    /**
     * Test of findPredecessor method, of class TREE.
     */
    @Test
    public void testfindPredecessor() {
        assertEquals(13, instance.findPredecessor(15));
        assertEquals(8, instance.findPredecessor(10));
        assertNull(instance.findPredecessor(7));
        assertNull(instance.findPredecessor(999));
    }

    /**
     * Test of truncate method, of class TREE.
     */
    @Test
    public void testtruncate() {
        instance.truncate(10, 30);
        assertEquals(Arrays.asList(10, 13, 15, 17, 20, 30), toList(instance.inOrder()));
        assertEquals(new HashSet<>(Arrays.asList(13, 17, 30)), instance.leafs());
    }

    /**
     * Test of isSubTree method, of class TREE.
     */
    @Test
    public void testisSubTree() {
        TREE<Integer> subTree = new TREE<>();
        for (int value : new int[]{10, 8, 13, 7}) {
            subTree.insert(value);
        }

        TREE<Integer> otherTree = new TREE<>();
        for (int value : arr) {
            otherTree.insert(value);
        }

        assertTrue(otherTree.isSubTree(subTree));
        subTree.insert(999);
        assertFalse(otherTree.isSubTree(subTree));
    }

    private static <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for (T item : iterable) {
            list.add(item);
        }
        return list;
    }
    
}
