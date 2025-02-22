package GUI;
import org.xml.sax.HandlerBase;

import Function.GUIValidation;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Controller controller = new Controller();
        Function.GUIValidation validation = new Function.GUIValidation();

        // Kolom kiri
        Label title = new Label("IQ Puzzler Pro Solver");
        title.setStyle("-fx-font-size: 20px;");
        Label identity = new Label("M. Ghifary Komara P. - 13523066");
        
        
        Button button1 = new Button("Upload");
        Label currentFile = new Label("Current File: None");
        Button button2 = new Button("Solve");
        Label errorMessage = new Label("...");

        
        VBox leftCol = new VBox(
            5, 
            title, 
            identity,
            button1,
            currentFile,
            button2,
            errorMessage
            );

        leftCol.setAlignment(Pos.CENTER_LEFT);
        leftCol.setPrefWidth(300);
        
        // Image
        String currentImg = "img/wonderhoy.png";
        ImageView imageView = new ImageView(new Image("file:" + currentImg));
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        
        // Grid
        GridPane grid =  new GridPane();
        grid.setPrefSize(600, 400);
        grid.setMaxSize(600, 400);
        grid.setAlignment(Pos.CENTER);
        
        grid.add(leftCol, 0, 0);
        grid.add(imageView, 1, 0);
        
        grid.setStyle("-fx-background-color: white;" +
        "-fx-border-color: white;" +
        "-fx-padding: 20px;"
        );
        
        GridPane.setValignment(imageView, javafx.geometry.VPos.CENTER);
        
        // Container
        StackPane container = new StackPane(grid);
        container.setAlignment(Pos.CENTER);
        container.setPrefSize(1200, 800);
        
 
        // StackPane Solusi
        Label solution_state = new Label("Solution Not Found...");
        Label solveInfo = new Label("Waktu pencarian (ms): -     Banyak kasus yang ditinjau: -");


        Button saveTxtButton = new Button("Save Solution");
        Button saveImageButton = new Button("Save Image");
        Button backButton = new Button("Back");
        HBox buttons = new HBox(
            5,
            saveTxtButton,
            saveImageButton
            );
        buttons.setAlignment(Pos.CENTER);
        buttons.setVisible(false);
        
        ImageView solutionImg = new ImageView(new Image("file:" + "img/nosolution.jpg"));
        solutionImg.setFitWidth(300);
        solutionImg.setFitHeight(300);
        solutionImg.setPreserveRatio(true);


        VBox secondScreen = new VBox(
            5,
            solution_state,
            solveInfo,
            buttons,
            backButton,
            solutionImg
            );
        secondScreen.setStyle("-fx-background-color: white;");
        secondScreen.setPrefSize(600, 400);
        secondScreen.setMaxSize(900, 600);
        secondScreen.setAlignment(Pos.CENTER);
        secondScreen.setVisible(false); // Hide initially
        
        
        // Action
        button1.setOnAction(e -> controller.uploadFile(imageView, currentFile, errorMessage));
        button2.setOnAction(e -> controller.fullSolve(
            imageView, errorMessage, grid, secondScreen,
            solution_state, solveInfo, buttons, solutionImg
            ));
        
        saveImageButton.setOnAction(e -> controller.isImageSaved = true);
        saveTxtButton.setOnAction(e -> controller.saveTxt());
        backButton.setOnAction(e -> {
            grid.setVisible(true);
            secondScreen.setVisible(false);
            errorMessage.setText("...");
            controller.deleteImage();
        });


        StackPane root = new StackPane(grid, secondScreen);
        root.setAlignment(grid, Pos.CENTER);
        root.setStyle(
            "-fx-background-image: url('file:img/background.png');" +
            "-fx-background-size: cover;"
            );

        Scene scene = new Scene(root, 1200, 800);

        primaryStage.setTitle("IQ Puzzler Pro Solver");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image("img/wonderhoy.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}