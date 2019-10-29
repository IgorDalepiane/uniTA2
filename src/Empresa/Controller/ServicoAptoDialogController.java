package Empresa.Controller;

import Empresa.Model.dao.AptidaoDAO;
import Empresa.Model.dao.FuncionarioDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.Apto;
import Empresa.Model.domain.Funcionario;
import Empresa.Model.domain.Servico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ServicoAptoDialogController implements Initializable {

    @FXML
    private ComboBox comboBoxAptos;

    private List<Funcionario> listFuncionarios;
    private List<Funcionario> listAptos;
    private ObservableList<Funcionario> observableListFuncionarios;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final AptidaoDAO aptidaoDAO = new AptidaoDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Apto apto;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Apto getApto() {
        return this.apto;
    }

    public void setApto(Apto apto) throws SQLException {
        this.apto = apto;
        funcionarioDAO.setConnection(connection);
        aptidaoDAO.setConnection(connection);

        listFuncionarios = funcionarioDAO.listar();
        listAptos=aptidaoDAO.buscarPorServico(this.apto.getServico());

        observableListFuncionarios = FXCollections.observableArrayList(listFuncionarios);
        comboBoxAptos.setItems(observableListFuncionarios);

        comboBoxAptos.setConverter(new StringConverter<Funcionario>() {
            @Override
            public String toString(Funcionario object) {
                if(object != null)
                    return object.getNome();
                return null;
            }
            @Override
            public Funcionario fromString(String string) {
                return null;
            }
        });
    }
    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }
    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            apto.setServico(this.apto.getServico());
            apto.setFuncionario((Funcionario) comboBoxAptos.getSelectionModel().getSelectedItem());
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleButtonCancelar() {
        dialogStage.close();
    }

    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        if (comboBoxAptos.getSelectionModel().getSelectedItem()==null){
            errorMessage += "Selecione algum Funcionario!\n";
        }
        if (listAptos.contains(comboBoxAptos.getSelectionModel().getSelectedItem())){
            errorMessage += "Funcionario ja é apto!\n";
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
}
