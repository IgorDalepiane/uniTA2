package Empresa.Controller;

import Empresa.Model.dao.FuncionarioDAO;
import Empresa.Model.dao.ServicoDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ServicoDialogController implements Initializable {

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

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Servico serv;
    private Apto cadApto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        funcionarioDAO.setConnection(connection);
    }


    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage, int s) {
        if (s == 2) {
            labelAptoCadastro.setVisible(false);
            comboBoxAptoCadastro.setVisible(false);
        } else {
            labelAptoCadastro.setVisible(true);
            comboBoxAptoCadastro.setVisible(true);
        }
        this.dialogStage = dialogStage;
    }

    public Servico getServico() {
        return this.serv;
    }

    public void setServ(Servico serv) throws SQLException {
        this.serv = serv;
        this.textFieldNome.setText(serv.getNome());
        this.textFieldDescricao.setText(serv.getDescricao());
        this.textFieldPreco.setText(serv.getPreco() == null ? "" : String.valueOf(serv.getPreco()));

        listFuncionarios = funcionarioDAO.listar();
        observableListFuncionarios = FXCollections.observableArrayList(listFuncionarios);

        comboBoxAptoCadastro.setItems(observableListFuncionarios);

        comboBoxAptoCadastro.setConverter(new StringConverter<Funcionario>() {
            @Override
            public String toString(Funcionario object) {
                if (object != null)
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
            serv.setNome(textFieldNome.getText());
            serv.setDescricao(textFieldDescricao.getText());
            serv.setPreco(Float.valueOf(textFieldPreco.getText()));

            List<Funcionario> cadApto = new ArrayList<>();
            cadApto.add((Funcionario) comboBoxAptoCadastro.getSelectionModel().getSelectedItem());
            serv.setAptos(cadApto);

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

        if (textFieldNome.getText() == null || textFieldNome.getText().length() == 0)
            errorMessage += "Nome inválido!\n";
        try {
            if (Float.parseFloat(textFieldPreco.getText()) <= 0 || textFieldPreco.getText().length() == 0)
                errorMessage += "Preço inválido!\n";
        } catch (NumberFormatException e) {
            errorMessage += "Somente números no campo Preço!\n";
        }
        if (comboBoxAptoCadastro.getSelectionModel().getSelectedItem() == null
                && comboBoxAptoCadastro.isVisible())
            errorMessage += "Você deve escolher um funcionário apto!\n";

        if (errorMessage.length() == 0) {
            return true;
        } else {
            alerta(errorMessage);
            return false;
        }
    }

    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }

}

