package Empresa.Controller;

import Empresa.Model.dao.FuncionarioDAO;
import Empresa.Model.dao.ServicoDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.Cargo;
import Empresa.Model.domain.Endereco;
import Empresa.Model.domain.Funcionario;
import Empresa.Model.domain.Servico;
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
import java.util.List;
import java.util.ResourceBundle;


public class ServicoDialogController implements Initializable {
    private static Scene scene = null;
    //pessoa
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldDescricao;
    @FXML
    private TextField textFieldPreco;
    @FXML
    private ComboBox comboBoxAptos;
    //contato
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;

    private List<Funcionario> listFuncionarios;
    private ObservableList<Funcionario> observableListFuncionarios;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Servico serv;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Servico getServico() {
        return this.serv;
    }

    public void setServ(Servico serv) {
        this.serv = serv;
        this.textFieldNome.setText(serv.getNome());
        this.textFieldDescricao.setText(serv.getDescricao());
        this.textFieldPreco.setText(serv.getPreco() == null ? "" : String.valueOf(serv.getPreco()));
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
        if (textFieldDescricao.getText() == null || textFieldDescricao.getText().length() == 0)
            errorMessage += "Descrição inválida!\n";
        try {
            if (Float.parseFloat(textFieldPreco.getText()) <= 0 || textFieldPreco.getText().length() == 0)
                errorMessage += "Preço inválido!\n";
        } catch (NumberFormatException e) {
            alerta("Somente números no campo Preço!");
        }

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

