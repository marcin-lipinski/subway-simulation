package Projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application
{
    static AnchorPane root;
    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("scene.fxml"));
        root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Subway Simulator");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args)
    {
        launch();
    }
}