
import java.util.concurrent.BlockingQueue;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * .Hlavní třída pro pro ukázku práce s vlákny.
 *
 * @author Martina Bečaverová
 * @version 1.0
 * @since 2017
 */
public class Main extends Application {

    public BlockingQueue<String> queue;
    public String poc;
    
    public Slider time;
    public MyThreads threads;

    @Override
    public void start(Stage primaryStage) throws Exception {
        time = new Slider(0, 1000, 500);
        primaryStage.setTitle("Conccurency Example");
        Group root = new Group();
        Canvas canvas = new Canvas(1800, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(5);
        Label label1 = new Label("Count of nodes:");
        TextField nodesTF = new TextField();
        Button nodesBT = new Button("Generate nodes");
        grid.add(label1, 0, 0);
        grid.add(nodesTF, 1, 0);
        grid.add(nodesBT, 2, 0);
        Button generateBT = new Button("Generate a tree");
        grid.add(generateBT, 3, 0);
        Label label2 = new Label("Actualy node: ");
        grid.add(label2, 4, 0);
        Label label3 = new Label();
        grid.add(label3, 5, 0);
        borderPane.setTop(grid);
        //label3.setText(threads.producer.cislo);
       
        VBox box = new VBox();

        box.setAlignment(Pos.CENTER);
        VBox.setMargin(time, new Insets(20));
        
        time.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            threads.producer.setTime(new_val.intValue());
            threads.consument.setTime(new_val.intValue());

        });
        time.setShowTickMarks(true);
        time.setShowTickLabels(true);
        borderPane.setBottom(time);

        nodesBT.setOnAction((ActionEvent event) -> {
            // TODO Auto-generated method stub
            poc = nodesTF.getText();
            if (poc.isEmpty() || !poc.matches("[0-9]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Špatnej vstup");
                alert.setContentText("Prosím vyplň všechna pole");
                alert.show();
            } else {
                CreateFile file = new CreateFile(Integer.parseInt(poc), primaryStage);
                Platform.runLater(file);
            }
        });
        generateBT.setOnAction((ActionEvent event) -> {
            threads = new MyThreads(primaryStage, gc);
        });

        root.getChildren().add(borderPane);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
