package bst;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class TREE_WORDS extends BST<TextWord> {
    
    public void createTree() throws FileNotFoundException{
        Scanner readfile = new Scanner(new File("files/xxx.xxx"));
        while(readfile.hasNextLine()){
            String[] pal = readfile.nextLine().split("(\\,)|(\\s)|(\\.)");
            for(String word : pal)
                if (word.length() > 0 )
                    insert(new TextWord(word, 1));
        }
        readfile.close();
    }

    /**
     * Inserts a new word in the tree, or increments the number of its occurrences.
       * @param element
     */
    @Override
    public void insert(TextWord element){
        root = insert(element, root);
    }

    private Node<TextWord> insert(TextWord element, Node<TextWord> node){
        if (node == null) {
            return new Node<>(element, null, null);   // palavra nova
        }
        int cmp = element.compareTo(node.getElement());
        if (cmp == 0) {
            node.getElement().incOcorrences();        // palavra repetida: +1 ocorrência
        } else if (cmp < 0) {
            node.setLeft(insert(element, node.getLeft()));
        } else {
            node.setRight(insert(element, node.getRight()));
        }
        return node;
    }

    /**
     * Returns a map with a list of words for each occurrence found.
     * @return a map with a list of words for each occurrence found.
     */
    public Map<Integer,List<String>> getWordsOccurrences(){
        Map<Integer,List<String>> result = new TreeMap<>();   // chaves por ordem crescente
        for (TextWord tw : inOrder()) {                       // in-order => palavras ordenadas
            result.computeIfAbsent(tw.getOcorrences(), k -> new ArrayList<>()).add(tw.getWord());
        }
        return result;
    }

}
