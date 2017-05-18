
/**
 * Trida pro vytvoreni vrcholu
 * @author Martina
  */
public class Node {

    int key;
    int balance;
    int height;
    Node left, right, parent;
    
/**
 * @param k klic vkladaneho vrcholu
 * @param p odkaz na rodice
 */
    Node(int k, Node p) {
        key = k;
        parent = p;
    }
/**
 * 
 * @return vraci klic jako text
 */
    String print() {
        String s = Integer.toString(this.key) + ", ";
        return s;
    }
}
