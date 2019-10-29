package Empresa.Controller;

import Empresa.Model.dao.ClienteDAO;
import Empresa.Model.dao.FuncionarioDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.Apto;
import Empresa.Model.domain.Funcionario;
import Empresa.Model.domain.Servico;
import Empresa.Model.domain.ServicoPrestado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ServicoPrestadoDialogController implements Initializable {

    //pessoa
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldDescricao;
    @FXML
    private TextField textFieldPreco;
    @FXML
    private Label labelAptoCadastro;
    @FXML
    private ComboBox comboBoxAptoCadastro;
    @FXML
    private ComboBox comboBoxAptos;

    private List<Funcionario> listFuncionarios;
    private ObservableList<Funcionario> observableListFuncionarios;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private ServicoPrestado servPrestado;
    //private Apto cadApto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        funcionarioDAO.setConnection(connection);
    }


    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage, int s) {

    }

    public ServicoPrestado getServicoPrestado() {
        return this.servPrestado;
    }

    public void setServPres(ServicoPrestado servPres) throws SQLException {

    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {

    }

    @FXML
    private void handleButtonCancelar() {
        dialogStage.close();
    }

    private boolean validarEntradaDeDados() {
        return false;
    }

    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }


}
