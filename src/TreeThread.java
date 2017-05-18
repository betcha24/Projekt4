
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ACER
 */
public class TreeThread implements Runnable {

    BlockingQueue<String> queue;
   public AVLTree tree = new AVLTree();
   public DrawTree drTree;
   GraphicsContext gc;
   private CountDownLatch latch = null;
   
   public TreeThread(GraphicsContext gc, CountDownLatch latch){
       this.gc = gc;
       this.latch = latch;
   }
   
    public void setQueue(BlockingQueue<String> queue ){
        this.queue = queue;
    }
    
    @Override
    public void run() {
        String cislo;
        System.out.println("Consumer Started");
        //System.out.println(queue.size());
        try {
            while ((cislo = queue.take()) != null) {
                System.out.println("Removed: " + cislo);
                tree.insert(Integer.parseInt(cislo));
                drTree = new DrawTree(gc, tree);
            }
            queue.clear();
        } catch (InterruptedException ex) {
            Logger.getLogger(TreeThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //Thread.currentThread().sleep(200);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        latch.countDown();
    }

}
