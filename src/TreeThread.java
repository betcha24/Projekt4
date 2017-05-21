
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import javafx.scene.canvas.GraphicsContext;

/**
 * Třída pro vlákno vytvoření stromu
 * @author Martina Bečaverová
 * @since 2017
 */
public class TreeThread implements Runnable {
    
    /**
     * Fronta pro načtené vrcholy ze souboru.
     */
    private final BlockingQueue<String> queue;
    /**
     * AVL Strom.
     */
    private final AVLTree tree = new AVLTree();
    /**
     * Vykreslení stromu.
     */
    private DrawTree drTree;
    /**
     * Grafický okno, kde se strom vykresluje
     */
    private final GraphicsContext gc;
    /**
     * Počítadlo vláken.
     */
    private CountDownLatch latch = null;
    /**
     * Čas pro spomalení vlákna.
     */
    private int time = 500;

    /**
     * Konstruktor třídy.
     * @param  gc grafický okno
     * @param  latch počítadlo vláken
     * @param queue fronta vstupních vrcholů stromu
     **/
    public TreeThread(GraphicsContext gc, CountDownLatch latch, BlockingQueue<String> queue) {
        this.gc = gc;
        this.latch = latch;
        this.queue = queue;
    }

    /**
     * Nastavení rýchlostí vlákna.
     * @param time čas předaný z GUI
     **/
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Čtení vrcholů z fronty a vytváření a vykreslení stromu.
     **/
    public void Tree() {
        String cislo;
        try {
            while (!queue.isEmpty() || (ReadMyFile.done != false)) {
                cislo = queue.take();
                tree.insert(Integer.parseInt(cislo));
                drTree = new DrawTree(gc, tree);
                Thread.currentThread().sleep(time);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            queue.clear();
        }
        latch.countDown();
        System.out.println("Druhe vlakno ukonceno");
    }

    @Override
    public void run() {
        Tree();
    }

}
