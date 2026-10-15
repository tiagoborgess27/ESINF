package bst;


public class AVL <E extends Comparable<E>> extends BST<E> {
    
    /** height(left) - height(right): > 0 pesa para a esquerda, < 0 para a direita. */
    private int balanceFactor(Node<E> node){
        return height(node.getLeft()) - height(node.getRight());
    }

    /** Caso esquerda-esquerda: o filho esquerdo sobe e o nó desce para a direita dele. */
    private Node<E> rightRotation(Node<E> node){
        Node<E> pivot = node.getLeft();
        node.setLeft(pivot.getRight());   // subárvore do meio passa a filho esquerdo do nó
        pivot.setRight(node);
        return pivot;                     // nova raiz desta subárvore
    }

    /** Caso direita-direita: o filho direito sobe e o nó desce para a esquerda dele. */
    private Node<E> leftRotation(Node<E> node){
        Node<E> pivot = node.getRight();
        node.setRight(pivot.getLeft());
        pivot.setLeft(node);
        return pivot;
    }

    /** Casos esquerda-direita e direita-esquerda: primeiro roda o filho, depois o nó. */
    private Node<E> twoRotations(Node<E> node){
        if (balanceFactor(node) > 0) {                       // esquerda-direita
            node.setLeft(leftRotation(node.getLeft()));
            return rightRotation(node);
        }
        node.setRight(rightRotation(node.getRight()));       // direita-esquerda
        return leftRotation(node);
    }

    private Node<E> balanceNode(Node<E> node)
    {
        int bf = balanceFactor(node);
        if (bf > 1) {                                        // pesado à esquerda
            if (balanceFactor(node.getLeft()) >= 0) {
                return rightRotation(node);                  // esquerda-esquerda
            }
            return twoRotations(node);                       // esquerda-direita
        }
        if (bf < -1) {                                       // pesado à direita
            if (balanceFactor(node.getRight()) <= 0) {
                return leftRotation(node);                   // direita-direita
            }
            return twoRotations(node);                       // direita-esquerda
        }
        return node;                                         // já equilibrado
    }

    @Override
    public void insert(E element){
        root = insert(element, root);
    }
    private Node<E> insert(E element, Node<E> node){
        if (node == null) {
            return new Node<>(element, null, null);
        }
        int cmp = element.compareTo(node.getElement());
        if (cmp == 0) {
            node.setElement(element);
            return node;
        }
        if (cmp < 0) {
            node.setLeft(insert(element, node.getLeft()));
        } else {
            node.setRight(insert(element, node.getRight()));
        }
        return balanceNode(node);   // ao "voltar" da recursão reequilibra cada ancestral
    }
    
    @Override  
    public void remove(E element){
        root = remove(element, root());
    }

    private Node<E> remove(E element, Node<E> node) {
        if (node == null) {
            return null;
        }   
        if (element.compareTo(node.getElement())==0) { // node is the Node to be removed
            
            if (node.getLeft() == null && node.getRight() == null) { //node is a leaf (has no childs)
                return null;
            }
            if (node.getLeft() == null) {   //has only right child
                return node.getRight();
            }
            if (node.getRight() == null) {  //has only left child
                return node.getLeft();
            }
            //has two child trees
            //replace the elem with the smallest of right subtree and remove it
            E smallElem = smallestElement(node.getRight());  
            node.setElement(smallElem);  
            node.setRight( remove(smallElem, node.getRight()) );
            node = balanceNode(node);
        }
        else if (element.compareTo(node.getElement()) < 0) {
            node.setLeft( remove(element, node.getLeft()) );
            node = balanceNode(node);
        }  
        else {
            node.setRight( remove(element, node.getRight()) );
            node = balanceNode(node);
        }
        return node;
    }
    

    @Override
    public boolean equals(Object otherObj) {

        if (this == otherObj) 
            return true;

        if (otherObj == null || this.getClass() != otherObj.getClass())
            return false;

        @SuppressWarnings("unchecked")
        AVL<E> second = (AVL<E>) otherObj;
        return equals(root, second.root);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (root != null ? root.hashCode() : 0);
        return hash;
    }

    public boolean equals(Node<E> root1, Node<E> root2) {
        if (root1 == null && root2 == null) 
           return true;
        else if (root1 != null && root2 != null) {
            if (root1.getElement().compareTo(root2.getElement()) == 0) {
                return equals(root1.getLeft(), root2.getLeft())
                        && equals(root1.getRight(), root2.getRight());
            } else  
                return false; 
        }
        else return false;
    }
   
}