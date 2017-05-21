
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;


/**
 * .Třída pro načtení souboru.
 * 
 * @author Martina Bečaverová
 * @since 2017
 */
public class ReadMyFile implements Runnable {

    private FileReader fr;
    private BufferedReader in;
    /**
     * Fronta vrcholu.
     **/
    private final BlockingQueue<String> queue;
    
    /**
     * Soubor pro čtení
     **/
    private final File file;
    
    /**
     * Konec souboru.
     **/
    public  static boolean done = false;
    
    /**
     * Počítadlo vláken.
     **/
    private CountDownLatch latch = null;
    
    /**
     * Čas pro spomalení vlákna.
     **/
    public int time;

    
    /**
     * Konstuktor třídy.
     * @param queue fronta znaků
     * @param file soubor pro čtení
     * @param latch počítadlo vláken
     **/
    public ReadMyFile(BlockingQueue<String> queue, File file, CountDownLatch latch) {
        this.queue = queue;
        this.file = file;
        this.latch = latch;
        this.time = 500;
    }

    /**
     * Nastavení rýchlostí vlákna.
     * @param time rýchlosť vlákna
     **/
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Čtení souboru.
     **/
    public void readFromFile() {
        String cislo;
        try {
            fr = new FileReader(file);
            in = new BufferedReader(fr);
            while ((cislo = in.readLine()) != null) {
                try {
                    queue.put(cislo); //přídá vrchol do souborů
                    Thread.currentThread().sleep(time);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    done = true;
                    fr.close();
                }
            }
            done = true;
            fr.close();
        } catch (IOException e) {
            System.err.println(e);
        }
        latch.countDown();
    }

    @Override
    public void run() {
        readFromFile();
        return;
    }

}
