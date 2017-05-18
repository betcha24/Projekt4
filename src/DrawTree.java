


import javafx.scene.canvas.GraphicsContext;

/**
 * Trida pro vykresleni stromu do grafickeho kontextu
 * @author Martina
 */
public final class DrawTree {
    public AVLTree tree;
    GraphicsContext gc;
    private final int width;
    private final int height;
    private final int radius = 15; // Tree node radius
    private final int vGap = 50; // Gap between two levels in a tree
    
    /**
     * Konstruktor pro vykresleni vola metodu DrawTree()
     * @param gc graficky kontext, do ktereho vykreslujeme
     * @param tree vstupni strom pro vykresleni
     */
    public DrawTree(GraphicsContext gc, AVLTree tree){
        this.tree = tree;
        this.gc = gc;
        width = (int) gc.getCanvas().getWidth()/2;
        height= (int) gc.getCanvas().getHeight();
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), height);
        drawTree();
    }
    
    /**
     * Metoda pro vykresleni vrcholu
     * @param n vrchol 
     * @param x x-ova souradnice
     * @param y y-ova souradnice
     */
    public void drawNode(Node n, int x, int y){
        gc.strokeOval(x - radius, y - radius, 2 * radius, 2 * radius);
        gc.fillText(Integer.toString(n.key), x - 6, y + 4);
    }
    
    /**
     * Metoda, ktera zavola rekurzivni metodu drawTree2
     */
    public void drawTree(){
        drawTree2(tree.root, width,30 ,400);
    }
    
    /**
     * Rekurzivni metoda vykresluje rekurzivne vrcholy az k listum
     * @param n vrchol  
     * @param x x-ova souradnice
     * @param y y-iva souradnice
     * @param hGap vzdalenost 
     */
    public void drawTree2(Node n, int x, int y,int hGap){
       drawNode(n, x, y);
       if (n.left != null) {
            // Spaji vrchol a leveho syna
            connectLeftChild( x - hGap, y + vGap, x, y);
            // Vykresli rekurzivne levy podstrom
            drawTree2( n.left, x - hGap, y + vGap, hGap / 2);
      }
        if (n.right != null) {
            // Spaji vrchol a praveho syna
            connectRightChild(x + hGap, y + vGap, x, y);
            // Vykresli rekurzivne pravy podstrom
            drawTree2( n.right, x + hGap, y + vGap, hGap / 2);  
      }
        
    }
    
    /**
     * Metoda pro spojeni vrcholu a leveho syna
     * @param x1 x-ova souradnice leveho syna
     * @param y1 y-ova souradnice leveho syna
     * @param x2 x-ova souradnice vrcholu
     * @param y2 y-ova souradnice vrcholu
     */
     private void connectLeftChild(int x1, int y1, int x2, int y2) { 
      double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
      int x11 = (int)(x1 + radius * (x2 - x1) / d);
      int y11 = (int)(y1 - radius * vGap / d);
      int x21 = (int)(x2 - radius * (x2 - x1) / d);
      int y21 = (int)(y2 + radius * vGap / d);
      gc.strokeLine(x11, y11,x21, y21);
    }

   /**
     * Metoda pro spojeni vrcholu a praveho syna
     * @param x1 x-ova souradnice praveho syna
     * @param y1 y-ova souraadnice praveho syna
     * @param x2 x-ova souradnice vrcholu
     * @param y2 y-ova souradnice vrcholu
     */
    private void connectRightChild( int x1, int y1, int x2, int y2) {
      double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
      int x11 = (int)(x1 - radius * (x1 - x2) / d);
      int y11 = (int)(y1 - radius * vGap / d);
      int x21 = (int)(x2 + radius * (x1 - x2) / d);
      int y21 = (int)(y2 + radius * vGap / d);
      gc.strokeLine(x11, y11,x21, y21);

    }

   
}
