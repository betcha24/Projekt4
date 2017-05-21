

/**
 * Trida pro vytvoreni stromu.
 *@author Martina Bečaverová
 * @since 2017
 */
public class AVLTree{
    
    public Node root;
    private String text = "";
    
   
    /**
     * Vkladani vrcholu
     * @param key klic vrcholu
     * @return 
     */
    public boolean insert(int key) {
        if (root == null)
            root = new Node(key, null);
        else {
            Node n = root;
            Node parent;
            while (true) {
                if (n.key == key)
                    return false;
 
                parent = n;
 
                boolean goLeft = n.key > key;
                n = goLeft ? n.left : n.right;
 
                if (n == null) {
                    if (goLeft) {
                        parent.left = new Node(key, parent);
                    } else {
                        parent.right = new Node(key, parent);
                    }
                    rebalance(parent);
                    break;
                }
            }
        }
        return true;
    }
 /**
  * Mazani vrcholu 
  * @param node vrchol ktery vymazeme
  */
    private void delete(Node node){
        if(node.left == null && node.right == null){
            if(node.parent == null) root = null;
            else{
                Node parent = node.parent;
                if(parent.left == node){
                    parent.left = null;
                }else parent.right = null;
                rebalance(parent);
            }
            return;
        }
        if(node.left!=null){
            Node child = node.left;
            while (child.right!=null) child = child.right;
            node.key = child.key;
            delete(child);
        }else{
            Node child = node.right;
            while (child.left!=null) child = child.left;
            node.key = child.key;
            delete(child);
        }
    }
  /**
   * Mazani vrcholu
   * @param delKey mazani vrcholu podle klice
   */
    public void delete(int delKey) {
        if (root == null)
            return;
        Node node = root;
        Node child = root;
 
        while (child != null) {
            node = child;
            child = delKey >= node.key ? node.right : node.left;
            if (delKey == node.key) {
                delete(node);
                return;
            }
        }
    }
 /**
  * Prepocitani balanc faktoru
  * @param n vrchol
  */
    private void rebalance(Node n) {
        setBalance(n);
 
        if (n.balance == -2) {
            if (height(n.left.left) >= height(n.left.right))
                n = rotateRight(n);
            else
                n = rotateLeftThenRight(n);
 
        } else if (n.balance == 2) {
            if (height(n.right.right) >= height(n.right.left))
                n = rotateLeft(n);
            else
                n = rotateRightThenLeft(n);
        }
 
        if (n.parent != null) {
            rebalance(n.parent);
        } else {
            root = n;
        }
    }
 /**
  * Leva rotace
  * @param a vrchol
  * @return vrati vrchol
  */
    private Node rotateLeft(Node a) {
        Node b = a.right;
        b.parent = a.parent;
        a.right = b.left;
        if (a.right != null)
            a.right.parent = a;
        b.left = a;
        a.parent = b;
        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }
        setBalance(a, b);
        return b;
    }
 
    /**
     * Prava rotace
     * @param a vrchol
     * @return vrati vrchol
     */
    private Node rotateRight(Node a) {
        Node b = a.left;
        b.parent = a.parent;
        a.left = b.right;
        if (a.left != null)
            a.left.parent = a;
        b.right = a;
        a.parent = b;
        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }
        setBalance(a, b);
        return b;
    }
 
    /**
     * Leva rotace, prava rotace
     * @param n vstupni vrchol
     * @return vystupni vrchol
     */
    private Node rotateLeftThenRight(Node n) {
        n.left = rotateLeft(n.left);
        return rotateRight(n);
    }
 
    /**
     * Prava rotace, leva rotace
     * @param n vstupni vrchol
     * @return vystupni vrchol
     */
    private Node rotateRightThenLeft(Node n) {
        n.right = rotateRight(n.right);
        return rotateLeft(n);
    }
 
    /**
     * Vrati vysku vrchola
     * @param n vstyoni vrchol
     * @return vyska vrchola
     */
    private int height(Node n) {
        if (n == null)
            return -1;
        return n.height;
    }
 
    /**
     * Nastavi balanc faktor pro vsechny vrcholy
     * @param nodes vstupni vrcholy
     */
    private void setBalance(Node... nodes) {
        for (Node n : nodes){
            reheight(n);
            n.balance = height(n.right) - height(n.left);
        }
    }
 
    /**
     * Zavola rekurzivnu proceduru pro vypis balanc faktorov
     */
    public void printBalance() {
        printBalance(root);
    }
 
    /**
     * Vypise balance faktor-rekurzivne pro vsechny vrcholy
     * @param n 
     */
    private void printBalance(Node n) {
        if (n != null) {
            printBalance(n.left);
            System.out.printf("%s ", n.balance);
            printBalance(n.right);
        }
    }
    
    /**
     * Vypise preorder po tom, co zavola rekurzivni funkci preOrder(Node n)
     * @return vypise vrcholy v preorder-u
     */
    public String printPreOrder(){
        text = "";
    	text = text + preOrder(root);
       // System.out.println(text);
        return text;
    }
    
    /**
     * Rekurzivni funkce pro preOrder
     * @param n vrchol
     * @return hodnota vrcholu 
     */
    public String preOrder(Node n){
    	if (n!=null){
    		text = text + n.print();
    	        preOrder(n.left);
    		preOrder(n.right);
               // System.out.println(text);
    	}
        return text;
    }
    
    /**
     * Vypise postOrder po tom, co zavola rekurzivni funkci postOrder(Node n)
     * @return vypise vrcholy v postOrder-u
     */
    public String printPostOrder(){
        text = "";
    	text = text + posOrder(root);
        return text;
    }
    
    /**
     * Rekurzivni funkce pro postOrder
     * @param n vrchol
     * @return hodnota vrcholu 
     */
    public String posOrder(Node n){
    	if (n!=null){
    		posOrder(n.left);
    		posOrder(n.right);
    		text = text + n.print();
    	}
        return text;
    }
    
    /**
     * Vypise inOrder po tom, co zavola rekurzivni funkci inOrder(Node n)
     * @return vypise vrcholy v inOrder-u
     */
    public String printInOrder(){
          text = "";
    	text = text + inOrder(root);
        return text;
    }
    
    /**
     * Rekurzivni funkce pro inOrder
     * @param n vrchol
     * @return hodnota vrcholu 
     */
    public String inOrder(Node n){
    	if (n!=null){
    		inOrder(n.left);
    		text = text + n.print();
    		inOrder(n.right);
    	}
        return text;
    }
  
    /**
     * Prepocita vysku vrcholu
     * @param node vrchol 
     */
    private void reheight(Node node){
        if(node!=null){
            node.height=1 + Math.max(height(node.left), height(node.right));
        }
    }

    
}
