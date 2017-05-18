
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author ACER
 */
public class CreateFile implements Runnable {

    private File file = null;
    private final int numerOfNodes;
    Stage primaryStage;

    public CreateFile(int numberOfNodes, Stage primaryStage) {
        this.numerOfNodes = numberOfNodes;
        this.primaryStage = primaryStage;
    }

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

    private void create() {
        chooseFile();
        if (file != null) {
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
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Soubor: " + file.getName() + " byl v pořádku vytvořen");
        alert.show();
    }

    @Override
    public void run() {
        create();
    }

}
