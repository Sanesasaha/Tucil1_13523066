package GUI;

import Function.GUIValidation;
import Function.FileIO;
import Function.Solve;
import Function.InputData;
import Function.Solve;
import Function.Board;
import Function.ImageSave;

import java.io.File;
import java.nio.file.*;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;


public class Controller {
    String myFile = "";
    boolean isImageSaved = false;
    InputData global;
    long searchTime;

    public void uploadFile(ImageView imageView, Label currentFile, Label errorMessage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setTitle("Select Text File");

        File selectedFile = fileChooser.showOpenDialog(null);
        

        if(selectedFile != null){
            File destinationFolder = new File("data/input/");
            if(!destinationFolder.exists()){
                destinationFolder.mkdirs();
            }
            
            File destinationFile = new File(destinationFolder, selectedFile.getName());
            
            try{
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                myFile = selectedFile.getName();
                currentFile.setText("Current File: " + selectedFile.getName());
                imageView.setImage(new Image("img/wonderhoy.png"));
                errorMessage.setText("...");
                System.out.println("File uploaded to: " + destinationFile.getAbsolutePath());
            }catch(IOException ex){
                System.out.println("Error");
                myFile = "";
            }
        } else{
            currentFile.setText("Current File: " + "None");
            myFile = "";
        }
    }

    public void fullSolve(ImageView imageView, Label errorMessage, GridPane grid, VBox secondScreen, Label solution_state, Label solveInfo, HBox buttons, ImageView solutionImg){
        Function.FileIO fileIO = new FileIO();
        Function.Solve solve = new Solve();
        Function.GUIValidation validation = new Function.GUIValidation();
        Function.InputData data = new InputData();
        
        boolean isValid;
        long start, end, exec;
        exec = 0;
        
        isImageSaved = false;
        isValid = validation.txt(imageView, errorMessage, "data/input/" , myFile);
        if(isValid){
            data = fileIO.readInputTxt("data/input/", myFile);
            if(data.B.status != -1){
                errorMessage.setText("Mencari solusi...");
                
                start = System.nanoTime();
                data.B  = Solve.BruteForce(data, 0, 0);
                end = System.nanoTime();
                
                exec = (end - start)/1000000;
                
                global = data;
                searchTime = exec;
                if(data.B.status == 1){
                    solution_state.setText("Solution Found!");
                    buttons.setVisible(true);
                    ImageSave.create(data.B, myFile);
                    solutionImg.setImage(new Image("file:data/output/" + myFile.replace(".txt", ".png")));
                    solutionImg.setFitWidth(600);
                    solutionImg.setFitHeight(600);
                } else{
                    solution_state.setText("Solution Not Found...");
                    buttons.setVisible(false);
                    solutionImg.setImage(new Image("file:img/nosolution.jpg"));
                    solutionImg.setFitWidth(300);
                    solutionImg.setFitHeight(300);
                    solutionImg.setPreserveRatio(true);        
                }

                solveInfo.setText("Waktu pencarian (ms): " + exec + " Banyak kasus yang ditinjau: " + data.B.total_case);
                
                data.B.printState();

                grid.setVisible(false);
                secondScreen.setVisible(true);
            } else{
                errorMessage.setText("Error: terdapat dimensi piece yang tidak valid");
            }
        }
    }

    public void deleteImage(){
        if(!isImageSaved){
            try{
                Path path = Paths.get("data/output/" + myFile.replace(".txt", ".png"));
                Files.delete(path);
            } catch(Exception e){
                System.out.println("Error while saving image");
            }
        }
    }

    public void saveTxt(){
        FileIO.writeOutputTxt(myFile, global, searchTime);
    }
}
