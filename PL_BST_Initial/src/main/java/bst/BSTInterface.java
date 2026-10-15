package bst;

import java.util.List;
import java.util.Map;

public interface BSTInterface<E> {

    boolean isEmpty();
    void insert(E element);
    void remove(E element);

    int size();
    int height();
    
    E smallestElement();
    Iterable<E> inOrder();
    Iterable<E> preOrder();
    Iterable<E> postOrder();
    Map<Integer,List<E>> nodesByLevel();
}
