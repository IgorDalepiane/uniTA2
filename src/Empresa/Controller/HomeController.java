package Empresa.Controller;

import Empresa.Exception.LoginInvalidoException;
import Empresa.Main;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.dao.EmpresaDAO;
import Empresa.Model.domain.Empresa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    //scene individual do controller, 1 ctrl = 1 view
    private static Scene scene;

    @FXML
    private PasswordField senhaEmpresa;
    @FXML
    private TextField emailEmpresa;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        empresaDAO.setConnection(connection);
    }
    /**
     * Carrega o arquivo fxml e define a scene (View)
     * @throws IOException
     */
    public static void showView() throws IOException {
        Parent root = FXMLLoader.load(HomeController.class.getResource("../View/home.fxml"));
        if(scene == null)
            scene = new Scene(root, 300, 275);
        Main.stage.setTitle("Login");
        Main.stage.setScene(scene);
        Main.center();
    }

    public void handleLogin(ActionEvent actionEvent) throws IOException {
        if(validarLogin()){
            Empresa empresa = new Empresa();
            empresa.setEmail(emailEmpresa.getText());
            empresa.setSenha(senhaEmpresa.getText());
            try {
                if (empresaDAO.logar(empresa)){
                    PagInicialController.showView();
                }
            } catch (LoginInvalidoException | SQLException e) {
                alerta(e.getMessage());
            }
        }

    }
    private boolean validarLogin() {
        String errorMessage = "";

        if (emailEmpresa.getText() == null || emailEmpresa.getText().length() == 0) {
            errorMessage += "Email inválido!\n";
        }
        if (senhaEmpresa.getText() == null || senhaEmpresa.getText().length() < 3) {
            errorMessage += "Senha inválida!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            alerta(errorMessage);
            return false;
        }
    }
    private void alerta(String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }
    public void handleNewacc(ActionEvent actionEvent) throws IOException {
        NewEmpresaController.showView();
    }

    public void handleForgotPw(ActionEvent actionEvent) throws IOException {
        ForgotPwController.showView();
    }
}
