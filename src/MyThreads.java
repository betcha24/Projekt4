
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Třída pro správu vláken.
 *
 * @author Martina Bečaverová
 * @since 2017
 */
public class MyThreads {

    /**
     * Soubor, ze kterého se má číst.
     */
    private File file;

    /**
     * Okno.
     *
     */
    private final Stage primaryStage;

    /**
     * Fronta vrcholů.
     *
     */
    private final BlockingQueue<String> queue;

    /**
     * Počítadlo vláken
     *
     */
    private CountDownLatch latch;

    /**
     * Čtení souboru.
     *
     */
    public ReadMyFile producer;

    /**
     * Vytváření stromu.
     *
     */
    public TreeThread consument;

    /**
     * Nastavení počtu jáder
     *
     */
    public ExecutorService ex = Executors.newFixedThreadPool(5);

    /**
     * Konstruktor třídy.
     *
     * @param primaryStage hlavní okno
     * @param gc grafický okno¨
     *
     */
    public MyThreads(Stage primaryStage, GraphicsContext gc) {
        this.primaryStage = primaryStage;
        latch = new CountDownLatch(2);
        queue = new LinkedBlockingQueue<>();

        if (chooseFile()) {
            producer = new ReadMyFile(queue, file, latch);
            consument = new TreeThread(gc, latch, queue);

            ex.execute(producer);//spusteni 1.vlákna
            ex.execute(consument);//spustení 2.vlákna
            ex.execute(() -> {
                try {
                    latch.await();
                } catch (InterruptedException ex1) {
                    ex1.printStackTrace();
                }
                try {
                    ex.shutdown();
                    ex.awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    System.err.println("tasks interrupted");
                } finally {
                    ex.shutdownNow();
                }
            } //cekani na dobehnuti vlaken
            );
        }
    }

    /**
     * Okno pro výber souboru.
     *
     *
     */
    public final boolean chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Vyber soubor");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text", "*.txt")
        );

        file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            return true;
        }
        return false;
    }

}
