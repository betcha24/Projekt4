
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ACER
 */
public class MyThreads {

    File file;
    public Stage primaryStage;
    public BlockingQueue<String> queue;
    //pocitadlo bezicich vlaken	
    private CountDownLatch latch;
    public TreeThread consument;
    public ExecutorService ex = Executors.newFixedThreadPool(5);
    ReadMyFile producer;
    DrawTree consument2;

    public MyThreads(Stage primaryStage, GraphicsContext gc) {
        this.primaryStage = primaryStage;
        latch = new CountDownLatch(2);
        queue = new LinkedBlockingQueue<>();

        if (chooseFile()) {
            producer = new ReadMyFile(primaryStage, queue, file, latch);
            consument = new TreeThread(gc, latch);
            consument.setQueue(queue);

            ex.execute(producer);
            ex.execute(consument);

            ex.execute(() -> {
                try {
                    latch.await();
                } catch (InterruptedException ex1) {
                    ex1.printStackTrace();
                }
                finish();
            } //cekani na dobehnuti vlaken
            );
        }

    }

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

    private void finish() {
        try {
            ex.shutdown();
            ex.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            ex.shutdownNow();
        }
    }
    
}
