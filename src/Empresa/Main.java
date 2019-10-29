package Empresa;

import Empresa.Controller.HomeController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        HomeController.showView();
        stage.setResizable(false);
        stage.show();
        center();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void center() {
        Rectangle2D monitor = Screen.getPrimary().getVisualBounds();
        stage.setX((monitor.getWidth() - stage.getWidth()) / 2);
        stage.setY((monitor.getHeight() - stage.getHeight()) / 2);
    }
}
