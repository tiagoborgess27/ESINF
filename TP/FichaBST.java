import java.lang.reflect.Array;
import java.util.*;

/*
 * ============================================================================
 *  FICHA: Binary Search Trees / AVL  -  TUDO RESOLVIDO E COMENTADO PARA ESTUDO
 * ============================================================================
 *
 *  Estrutura do ficheiro:
 *    1. BST<E>      -> classe base MINIMA (a ficha nao a mostra completa, por isso
 *                      criei so o necessario para tudo compilar e funcionar).
 *    2. TREE<E>     -> Exercicios 1 a), b), c), d), e), f), g), h), 3 e 4.
 *    3. MyTree      -> Exercicio 2 (isSymmetric).
 *    4. FichaBST    -> main com exemplos para testares tudo.
 *
 *  IDEIAS-CHAVE (le isto antes de ver o codigo):
 *
 *   (1) PROPRIEDADE DA BST: para qualquer no,
 *         tudo na subarvore ESQUERDA  <  elemento do no  <  tudo na DIREITA.
 *       Por isso, para procurar um valor NAO precisas de ver a arvore toda:
 *       em cada no escolhes so um lado. Custo O(h), com h = altura da arvore.
 *
 *   (2) RECURSAO EM ARVORES: quase tudo se resolve com um metodo que trata um
 *       no e chama-se a si proprio para o filho esquerdo e para o direito.
 *       Caso base tipico: no == null.
 *
 *   (3) PADRAO "publico + auxiliar privado": o metodo publico nao recebe o no
 *       (o utilizador nao sabe o que e um Node); o privado recebe o no atual.
 *       E o mesmo padrao do mdc/reverse dos exercicios de recursao.
 *
 *   (4) PERCURSO IN-ORDER (esquerda, no, direita) numa BST devolve os elementos
 *       ORDENADOS. E a base do exercicio 4.
 */

// ============================================================================
//  1. BST BASE (minima)
// ============================================================================
class BST<E extends Comparable<E>> {

    // Raiz da arvore. "protected" -> as subclasses (TREE, MyTree) conseguem aceder.
    protected Node<E> root = null;

    // ------------------------------------------------------------------------
    // No da arvore. E "static" porque nao precisa de aceder ao objeto BST.
    // Os atributos sao private (como na ficha), por isso uso getters/setters.
    // ------------------------------------------------------------------------
    protected static class Node<E> {
        private E element;       // elemento guardado neste no
        private Node<E> left;    // filho esquerdo (ou null)
        private Node<E> right;   // filho direito (ou null)

        public Node(E element, Node<E> left, Node<E> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

        public E getElement()          { return element; }
        public Node<E> getLeft()       { return left; }
        public Node<E> getRight()      { return right; }
        public void setLeft(Node<E> l) { this.left = l; }
        public void setRight(Node<E> r){ this.right = r; }
    }

    public boolean isEmpty() {
        return root == null;
    }

    // Insercao: publico + auxiliar recursivo.
    public void insert(E element) {
        root = insert(element, root);
    }

    // Devolve a raiz da (sub)arvore ja com o elemento inserido.
    // Isto e o truque classico: "node.setLeft(insert(..., node.getLeft()))"
    // religa sempre o filho, mesmo quando nao mudou nada.
    private Node<E> insert(E element, Node<E> node) {
        if (node == null) {
            return new Node<>(element, null, null);    // posicao encontrada
        }
        int c = element.compareTo(node.getElement());
        if (c < 0) {
            node.setLeft(insert(element, node.getLeft()));
        } else if (c > 0) {
            node.setRight(insert(element, node.getRight()));
        }
        // c == 0 -> elemento repetido, ignora (BST normal nao guarda duplicados)
        return node;
    }

    // Percurso in-order: esquerda -> no -> direita => elementos ORDENADOS.
    public List<E> inOrder() {
        List<E> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(Node<E> node, List<E> result) {
        if (node == null) return;               // caso base
        inOrder(node.getLeft(), result);        // 1) tudo o que e menor
        result.add(node.getElement());          // 2) o proprio no
        inOrder(node.getRight(), result);       // 3) tudo o que e maior
    }
}

// ============================================================================
//  2. TREE<E> : EXERCICIOS 1, 3 e 4
// ============================================================================
class TREE<E extends Comparable<E>> extends BST<E> {

    // ------------------------------------------------------------------------
    // 1 a) path: caminho da raiz ate ao elemento.
    //
    // IDEIA: desce a partir da raiz, como numa pesquisa normal; cada no por onde
    // passas e adicionado a lista. Se chegares a null, o elemento nao existe.
    // Complexidade: O(h).
    // ------------------------------------------------------------------------
    public List<E> path(E element) {
        List<E> result = new ArrayList<>();
        Node<E> cur = root;

        while (cur != null) {
            result.add(cur.getElement());               // regista o no atual
            int c = element.compareTo(cur.getElement());
            if (c == 0) {
                return result;                          // encontrou: caminho completo
            }
            cur = (c < 0) ? cur.getLeft() : cur.getRight();   // escolhe 1 lado
        }
        return new ArrayList<>();   // nao existe -> lista vazia
    }

    // ------------------------------------------------------------------------
    // 1 b) leafs: conjunto dos elementos das FOLHAS.
    //
    // Folha = no sem filhos (left == null && right == null).
    // IDEIA: percorrer a arvore toda (aqui nao ha atalho de BST) e adicionar
    // os nos que sao folhas. LinkedHashSet mantem a ordem de insercao
    // (da esquerda para a direita).  Complexidade: O(n).
    // ------------------------------------------------------------------------
    public Set<E> leafs() {
        Set<E> result = new LinkedHashSet<>();
        leafs(root, result);
        return result;
    }

    private void leafs(Node<E> node, Set<E> result) {
        if (node == null) return;
        if (node.getLeft() == null && node.getRight() == null) {
            result.add(node.getElement());              // e folha
            return;
        }
        leafs(node.getLeft(), result);
        leafs(node.getRight(), result);
    }

    // ------------------------------------------------------------------------
    // 1 c) range: {minimo, maximo}.
    //
    // IDEIA: na BST o MINIMO e o no mais a ESQUERDA e o MAXIMO o mais a DIREITA.
    // Nao precisas de percorrer tudo: O(h).
    //
    // NOTA sobre genericos: nao se pode fazer "new E[2]". Crio o array com
    // reflexao, usando a classe real do elemento (ex.: Integer), e assim o
    // resultado e mesmo um Integer[] em tempo de execucao.
    // Devolve null se a arvore estiver vazia.
    // ------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public E[] range() {
        if (root == null) return null;

        Node<E> min = root;
        while (min.getLeft() != null) min = min.getLeft();     // todo para a esquerda

        Node<E> max = root;
        while (max.getRight() != null) max = max.getRight();   // todo para a direita

        E[] result = (E[]) Array.newInstance(root.getElement().getClass(), 2);
        result[0] = min.getElement();
        result[1] = max.getElement();
        return result;
    }

    // ------------------------------------------------------------------------
    // 1 d) diameter: maior caminho entre dois nos (medido em ARESTAS).
    //
    // No exemplo da ficha o caminho tem 10 nos e "length 9" -> 9 arestas.
    //
    // IDEIA: o maior caminho que "passa" por um no X tem comprimento
    //     altura(esq de X) + altura(dir de X) + 2
    // (o +2 sao as duas arestas que ligam X aos filhos). O diametro e o maximo
    // disto sobre TODOS os nos, pois o melhor caminho pode nao passar na raiz.
    //
    // Convencao: altura(null) = -1, altura(folha) = 0.
    // Assim uma folha da 0, e um no com um so filho folha da 1.
    //
    // Calculo as alturas e atualizo o melhor valor na MESMA passagem: O(n).
    // best e um array de 1 posicao porque o Java passa int por valor e preciso
    // que as chamadas recursivas atualizem o mesmo valor.
    // ------------------------------------------------------------------------
    public Integer diameter() {
        int[] best = {0};
        height(root, best);
        return best[0];
    }

    private int height(Node<E> node, int[] best) {
        if (node == null) return -1;                        // caso base
        int hl = height(node.getLeft(), best);
        int hr = height(node.getRight(), best);
        best[0] = Math.max(best[0], hl + hr + 2);           // caminho que passa em "node"
        return Math.max(hl, hr) + 1;                        // altura de "node"
    }

    // ------------------------------------------------------------------------
    // 1 e) findPredecessor: elemento imediatamente anterior (em ordem crescente).
    //
    // Predecessor = MAIOR elemento que e ESTRITAMENTE MENOR que o dado.
    //
    // IDEIA (uma unica descida):
    //   - se o no atual e >= element, o predecessor esta mais a esquerda -> vai esquerda
    //   - se o no atual e <  element, ele e CANDIDATO (guarda-o) e tenta ver se
    //     ha um maior (ainda < element) na direita -> vai direita
    //   No fim, o ultimo candidato guardado e o predecessor.
    //
    // Cobre os 2 casos classicos sem codigo extra:
    //   - tem subarvore esquerda -> predecessor = maximo dessa subarvore
    //   - nao tem                -> predecessor = ancestral de onde se "virou a direita"
    // Devolve null se nao houver (elemento e o minimo). O(h).
    //
    // Exemplo (ficha): predecessor(17) => 15
    //   20: 17<20 esq | 17: igual, esq | 10: <17 guarda 10, dir | 13: guarda 13, dir
    //   | 15: guarda 15, dir | null -> devolve 15
    // ------------------------------------------------------------------------
    public E findPredecessor(E element) {
        Node<E> cur = root;
        E pred = null;

        while (cur != null) {
            if (element.compareTo(cur.getElement()) <= 0) {
                cur = cur.getLeft();
            } else {
                pred = cur.getElement();
                cur = cur.getRight();
            }
        }
        return pred;
    }

    // ------------------------------------------------------------------------
    // 1 f) identical: as duas arvores sao identicas (mesma estrutura E valores)?
    //
    // IDEIA: percorrer as duas ao mesmo tempo (recursao sobre 2 nos):
    //   - ambos null            -> iguais
    //   - so um e null          -> diferentes (estrutura diferente)
    //   - elementos diferentes  -> diferentes
    //   - senao, esquerdas iguais E direitas iguais
    //
    // "tree.root" e acessivel porque esta tudo no mesmo pacote e root e protected.
    // ------------------------------------------------------------------------
    public boolean identical(BST<E> tree) {
        return identical(this.root, tree.root);
    }

    // static + generico <T>: nao precisa de "this", so compara dois nos.
    private static <T> boolean identical(Node<T> a, Node<T> b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        if (!Objects.equals(a.getElement(), b.getElement())) return false;
        return identical(a.getLeft(), b.getLeft())
            && identical(a.getRight(), b.getRight());
    }

    // ------------------------------------------------------------------------
    // 1 g) truncate: remove todos os elementos fora de [low, high].
    //
    // IDEIA (aproveita a ordenacao da BST), para cada no:
    //   - no < low  -> ele e TODA a sua subarvore esquerda estao fora; so a direita
    //                  pode ter coisas validas. Substitui o no pelo resultado de
    //                  truncar a DIREITA.
    //   - no > high -> simetrico: substitui pelo resultado de truncar a ESQUERDA.
    //   - dentro    -> mantem o no e trunca os dois filhos.
    // O metodo devolve a nova raiz da subarvore, e quem chama religa-a.
    // ------------------------------------------------------------------------
    public void truncate(E low, E high) {
        root = truncate(root, low, high);
    }

    private Node<E> truncate(Node<E> node, E low, E high) {
        if (node == null) return null;

        if (node.getElement().compareTo(low) < 0) {
            return truncate(node.getRight(), low, high);        // descarta no + esquerda
        }
        if (node.getElement().compareTo(high) > 0) {
            return truncate(node.getLeft(), low, high);         // descarta no + direita
        }
        node.setLeft(truncate(node.getLeft(), low, high));      // no valido: trunca filhos
        node.setRight(truncate(node.getRight(), low, high));
        return node;
    }

    // ------------------------------------------------------------------------
    // 1 h) isSubTree: "tree" e subarvore desta arvore?
    //
    // Subarvore = existe um no X desta arvore tal que a subarvore que comeca em
    // X (X e TODOS os descendentes) e identica a "tree".
    //
    // IDEIA em 2 passos, reutilizando o que ja existe:
    //   1) procurar nesta arvore o no com o mesmo elemento que a raiz de "tree"
    //      (descida normal da BST, O(h));
    //   2) comparar a partir desse no com identical().
    // Uma arvore vazia e subarvore de qualquer arvore.
    // ------------------------------------------------------------------------
    public boolean isSubTree(BST<E> tree) {
        if (tree.root == null) return true;

        Node<E> cur = this.root;
        while (cur != null) {
            int c = tree.root.getElement().compareTo(cur.getElement());
            if (c == 0) {
                return identical(cur, tree.root);       // encontrou o candidato: compara
            }
            cur = (c < 0) ? cur.getLeft() : cur.getRight();
        }
        return false;   // nenhum no com esse elemento
    }

    // ------------------------------------------------------------------------
    // 3) Subarvore minima que contem um conjunto de nos S.
    //
    // E o problema do ANCESTRAL COMUM MAIS BAIXO (LCA) generalizado.
    //
    // IDEIA: so interessam o MENOR (min) e o MAIOR (max) de S; se esses dois
    // ficarem na subarvore, todos os do meio tambem ficam (propriedade BST).
    // Desce a partir da raiz:
    //   - max < no atual -> todos estao a esquerda -> vai esquerda
    //   - min > no atual -> todos estao a direita  -> vai direita
    //   - senao          -> "separam-se" aqui (ou o no e um deles): e o LCA, PARA.
    // O resultado e uma COPIA da subarvore com raiz no LCA.
    //
    // Exemplo (ficha): S={1,15}. 54: ambos < 54 -> esq. 23: idem -> esq.
    // 13: 1 < 13 < 15 -> separam-se -> LCA = 13 -> {13,11,3,1,17,15,20}.
    // ------------------------------------------------------------------------
    public TREE<E> minSubTree(Set<E> s) {
        TREE<E> result = new TREE<>();
        if (s.isEmpty() || root == null) return result;

        E min = null, max = null;
        for (E e : s) {
            if (path(e).isEmpty()) {
                throw new IllegalArgumentException("Elemento nao existe na arvore: " + e);
            }
            if (min == null || e.compareTo(min) < 0) min = e;
            if (max == null || e.compareTo(max) > 0) max = e;
        }

        Node<E> cur = root;
        while (true) {
            if (max.compareTo(cur.getElement()) < 0) {
                cur = cur.getLeft();
            } else if (min.compareTo(cur.getElement()) > 0) {
                cur = cur.getRight();
            } else {
                break;      // LCA encontrado
            }
        }
        result.root = copy(cur);
        return result;
    }

    // Copia profunda de uma subarvore (para o resultado nao partilhar nos).
    private Node<E> copy(Node<E> node) {
        if (node == null) return null;
        return new Node<>(node.getElement(), copy(node.getLeft()), copy(node.getRight()));
    }

    // ------------------------------------------------------------------------
    // 4) Ordenar uma List<E> desordenada, com um metodo generico.
    //
    // IDEIA ("tree sort"): inserir tudo numa BST e fazer um percurso IN-ORDER,
    // que devolve os elementos ordenados.
    //
    // "<T extends Comparable<T>>" garante que os elementos se podem comparar.
    // Complexidade: O(n log n) em media; O(n^2) no pior caso (arvore degenerada,
    // ex.: lista ja ordenada) - e para isso que existem as AVL (auto-balanceadas).
    //
    // LIMITACAO: esta BST ignora duplicados, logo repetidos desaparecem.
    // (Para manter repetidos: guardar contagens, ou usar Collections.sort.)
    // ------------------------------------------------------------------------
    public static <T extends Comparable<T>> List<T> sort(List<T> list) {
        BST<T> bst = new BST<>();
        for (T e : list) {
            bst.insert(e);
        }
        return bst.inOrder();
    }
}

// ============================================================================
//  3. MyTree : EXERCICIO 2 (isSymmetric)
// ============================================================================
class MyTree extends BST<Integer> {

    // Simetrica = subarvore esquerda da raiz e o ESPELHO da direita, em
    // estrutura E em valores: o valor a esquerda e negativo e o espelho tem o
    // mesmo valor absoluto com sinal positivo (ex.: -10 <-> 10).
    // O valor da raiz nao e comparado com nada.
    public boolean isSymmetric() {
        if (root == null) return true;
        return mirror(root.getLeft(), root.getRight());
    }

    // IDEIA: recursao sobre DOIS nos ao mesmo tempo, cruzando os filhos:
    //   esquerda de l <-> direita de r     e     direita de l <-> esquerda de r
    // (e assim que se constroi um espelho).
    private boolean mirror(Node<Integer> l, Node<Integer> r) {
        if (l == null && r == null) return true;        // ambos acabam: ok
        if (l == null || r == null) return false;       // estrutura diferente

        int lv = l.getElement();
        int rv = r.getElement();

        // lado esquerdo tem de ser negativo e o direito o seu simetrico positivo
        if (lv >= 0 || rv != -lv) return false;

        return mirror(l.getLeft(), r.getRight())
            && mirror(l.getRight(), r.getLeft());
    }
}

// ============================================================================
//  4. MAIN: exemplos para testar tudo
// ============================================================================
public class FichaBST {

    private static TREE<Integer> build(int... values) {
        TREE<Integer> t = new TREE<>();
        for (int v : values) t.insert(v);
        return t;
    }

    public static void main(String[] args) {

        // Arvore do exercicio 1 e): 20 na raiz; 17 e 30; 10 | 50; 8 e 13 | 40; 7 | 15
        int[] ex1 = {20, 17, 30, 10, 50, 8, 13, 40, 7, 15};
        TREE<Integer> t = build(ex1);

        System.out.println("=== Exercicio 1 ===");
        System.out.println("in-order:           " + t.inOrder());       // [7, 8, 10, 13, 15, 17, 20, 30, 40, 50]
        System.out.println("a) path(15):        " + t.path(15));        // [20, 17, 10, 13, 15]
        System.out.println("   path(99):        " + t.path(99));        // [] (nao existe)
        System.out.println("b) leafs:           " + t.leafs());         // [7, 15, 40]
        System.out.println("c) range:           " + Arrays.toString(t.range()));   // [7, 50]
        System.out.println("d) diameter:        " + t.diameter());      // 7
        System.out.println("e) pred(50)=" + t.findPredecessor(50)       // 40
                + " pred(40)=" + t.findPredecessor(40)                   // 30
                + " pred(15)=" + t.findPredecessor(15)                   // 13
                + " pred(17)=" + t.findPredecessor(17)                   // 15
                + " pred(7)=" + t.findPredecessor(7));                   // null

        TREE<Integer> igual = build(ex1);
        TREE<Integer> diferente = build(20, 17, 30);
        System.out.println("f) identical (igual):      " + t.identical(igual));        // true
        System.out.println("   identical (diferente):  " + t.identical(diferente));    // false

        TREE<Integer> sub1 = build(10, 8, 13, 7, 15);   // e mesmo a subarvore do 10
        TREE<Integer> sub2 = build(10, 8, 13);          // falta o 7 e o 15
        System.out.println("h) isSubTree (sub1): " + t.isSubTree(sub1));   // true
        System.out.println("   isSubTree (sub2): " + t.isSubTree(sub2));   // false

        TREE<Integer> paraTruncar = build(ex1);
        paraTruncar.truncate(10, 30);
        System.out.println("g) truncate(10,30): " + paraTruncar.inOrder());   // [10, 13, 15, 17, 20, 30]

        System.out.println("\n=== Exercicio 2 ===");
        MyTree sim = new MyTree();
        for (int v : new int[]{0, -1, 1, -10, 10, -20, -5, 5, 20, -7, 7}) sim.insert(v);
        System.out.println("isSymmetric (exemplo 1): " + sim.isSymmetric());    // true

        MyTree nao = new MyTree();
        for (int v : new int[]{0, -1, 1, -10, 10, -5, 5, 20, -7, 7}) nao.insert(v);   // sem o -20
        System.out.println("isSymmetric (exemplo 3): " + nao.isSymmetric());    // false

        System.out.println("\n=== Exercicio 3 ===");
        TREE<Integer> t3 = build(54, 23, 65, 13, 43, 70, 11, 17, 66, 75, 3, 15, 20, 1);
        TREE<Integer> minima = t3.minSubTree(new HashSet<>(Arrays.asList(1, 15)));
        System.out.println("subarvore minima de {1,15}: " + minima.inOrder());  // [1, 3, 11, 13, 15, 17, 20]

        System.out.println("\n=== Exercicio 4 ===");
        List<Integer> desordenada = Arrays.asList(5, 3, 9, 1, 7, 2);
        System.out.println("sort: " + TREE.sort(desordenada));   // [1, 2, 3, 5, 7, 9]
        List<String> palavras = Arrays.asList("rui", "ana", "zé", "bia");
        System.out.println("sort (String): " + TREE.sort(palavras));   // [ana, bia, rui, zé]
    }
}