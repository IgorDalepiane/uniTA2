package Empresa.Controller;

import Empresa.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPwController implements Initializable {
    private static Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static void showView() throws RuntimeException, IOException {
        Parent root = FXMLLoader.load(ForgotPwController.class.getResource("../View/forgotPw.fxml"));
        if (scene == null)
            scene = new Scene(root, 300, 275);
        Main.stage.setTitle("Recuperar senha");
        Main.stage.setScene(scene);
    }

    public void handlePassRecovery(ActionEvent actionEvent) {
        //TODO enviar e-mail com a nova senha
    }

    public void handleReturn(ActionEvent actionEvent) throws IOException {
        HomeController.showView();
    }

}
