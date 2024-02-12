
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *  This class is the main class of the application. This class boots up the 
 *  first window and first scene (the welcome page)
 *
 *  To start the application please create an object of this class
 *
 *
 */
public class Main extends Application {

    /**
     * This method starts the program off
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("WelcomePage.fxml"));
        primaryStage.setTitle("Home Page");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("pics/logo.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}