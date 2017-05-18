
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;

/**
 *
 * @author ACER
 */
public class ReadMyFile implements Runnable {

    private FileReader fr;
    private BufferedReader in;
    private final BlockingQueue<String> queue;
    private final File file;
    public static boolean done = false;
    private final Stage primaryStage;
    private CountDownLatch latch = null;
    public int time;
    String cislo = null;
    
    public ReadMyFile(Stage primaryStage, BlockingQueue<String> queue, File file,CountDownLatch latch) {
        this.primaryStage = primaryStage;
        this.queue = queue;
        this.file = file;
        this.latch = latch;
        this.time = 500;
    }
    
    public void setTime(int time){
        this.time = time;
    }
    
    public void readFromFile() {
        
       
            try {
                fr = new FileReader(file);
                in = new BufferedReader(fr);
                //cte dokud nedojde az na konec souboru
                while ((cislo = in.readLine()) != null) {
                    try {
                        queue.put(cislo);
                        System.out.println(cislo);
                        Thread.currentThread().sleep(time);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ReadMyFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                done = true;
                latch.countDown();
                fr.close();
            } catch (IOException e) {
                System.err.println(e);
            }
            //latch.countDown();
    }
    
    public String getCislo(){
        return cislo;
    }
    
    @Override
    public void run() {
       readFromFile();
    }

}
