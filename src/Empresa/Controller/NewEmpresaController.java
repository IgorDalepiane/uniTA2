package Empresa.Controller;

import Empresa.Exception.CadastroException;
import Empresa.Main;
import Empresa.Model.dao.EmpresaDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
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
import java.util.ResourceBundle;

public class NewEmpresaController implements Initializable {
    private static Scene scene;
    @FXML
    private TextField nomeEmpresa;
    @FXML
    private TextField emailEmpresa;
    @FXML
    private PasswordField senhaEmpresa;

    private Empresa empresa = new Empresa();


    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        empresaDAO.setConnection(connection);
    }

    public static void showView() throws IOException {
        Parent root = FXMLLoader.load(NewEmpresaController.class.getResource("../View/newEmpresa.fxml"));
        if(scene == null)
            scene = new Scene(root, 350,275);
        Main.stage.setTitle("Nova conta");
        Main.stage.setScene(scene);
    }

    public void handleSubmit(ActionEvent actionEvent) throws IOException {
        //TODO enviar os dados do usuário ao banco de dados
        if (validarEntradaDeDados()) {
            empresa.setNome(nomeEmpresa.getText());
            empresa.setEmail(emailEmpresa.getText());
            empresa.setSenha(senhaEmpresa.getText());
            try {
                empresaDAO.inserir(empresa);
                limpaCampos();
                HomeController.showView();
            } catch (CadastroException e) {
                alerta(e.getMessage());
            }
        }
    }
    private void limpaCampos() {
        nomeEmpresa.clear();
        emailEmpresa.clear();
        senhaEmpresa.clear();
    }
    private void alerta(String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (nomeEmpresa.getText() == null || nomeEmpresa.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (emailEmpresa.getText() == null || emailEmpresa.getText().length() == 0) {
            errorMessage += "Email inválido!\n";
        }
        if (senhaEmpresa.getText() == null || senhaEmpresa.getText().length() < 3) {
            errorMessage += "Senha inválida!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    public void handleReturn(ActionEvent actionEvent) throws IOException {
        HomeController.showView();
    }
}
