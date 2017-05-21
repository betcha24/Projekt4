
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * .Třída pro vytvoření souboru.
 *
 * @author Martina Bečaverová
 * @since 2017
 */
public class CreateFile implements Runnable {

    /**
     * Soubor.
     *
     */
    private File file = null;

    /**
     * Počet vrcholů, které se mají vygenerovat.
     *
     */
    private final int numerOfNodes;

    /**
     * Hlavní okno.
     *
     */
    private final Stage primaryStage;

    /**
     * Konstruktor třídy.
     *
     * @param numberOfNodes počet vrcholů
     * @param primaryStage hlavní okno
     *
     */
    public CreateFile(int numberOfNodes, Stage primaryStage) {
        this.numerOfNodes = numberOfNodes;
        this.primaryStage = primaryStage;
    }

    /**
     * Vytvoření novýho souboru.
     *
     */
    private boolean chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create a file");
        
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text", "*.txt")
        );
        
        file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            return true;
        }
        return false;
    }

    /**
     * Vytvoření suoboru.
     *
     */
    private void create() {
        if (chooseFile()) {
            Random r = new Random();
            try {
                PrintWriter writer = new PrintWriter(file, "UTF-8");
                for (int i = 1; i <= numerOfNodes; i++) {
                    writer.println(r.nextInt(numerOfNodes * 2));
                }
                writer.close();
            } catch (IOException e) {
                System.err.println(e);
            }
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Soubor: " + file.getName() + " byl v pořádku vytvořen");
            alert.show();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Soubor nebyl vytvořen");
            alert.show();
        }
    }
    
    @Override
    public void run() {
        create();
    }
    
}
