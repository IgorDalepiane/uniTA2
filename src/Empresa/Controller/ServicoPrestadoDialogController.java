package Empresa.Controller;

import Empresa.Model.dao.AptidaoDAO;
import Empresa.Model.dao.ClienteDAO;
import Empresa.Model.dao.FuncionarioDAO;
import Empresa.Model.dao.ServicoDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.*;
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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ServicoPrestadoDialogController implements Initializable {

    //pessoa
    @FXML
    private ComboBox comboBoxServico;
    @FXML
    private ComboBox comboBoxFuncionario;
    @FXML
    private ComboBox comboBoxCliente;
    @FXML
    private TextField textFieldData;
    @FXML
    private TextField textFieldHrInicio;
    @FXML
    private TextField textFieldHrFim;

    private List<Servico> listServicos;
    private ObservableList<Servico> observableListServicos;

    private List<Funcionario> listFuncionarios;
    private ObservableList<Funcionario> observableListFuncionarios;

    private List<Cliente> listCliente;
    private ObservableList<Cliente> observableListCliente;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ServicoDAO servicoDAO = new ServicoDAO();
    private final AptidaoDAO aptidaoDAO = new AptidaoDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private ServicoPrestado servPrestado;
    //private Apto cadApto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        funcionarioDAO.setConnection(connection);
        servicoDAO.setConnection(connection);
        clienteDAO.setConnection(connection);
        aptidaoDAO.setConnection(connection);
    }


    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage, int s) {
        this.dialogStage = dialogStage;
    }

    public ServicoPrestado getServicoPrestado() {
        return this.servPrestado;
    }

    public void setServPres(ServicoPrestado servPres) throws SQLException {
        this.servPrestado=servPres;
        listServicos = servicoDAO.listar();
        observableListServicos = FXCollections.observableArrayList(listServicos);

        comboBoxServico.setItems(observableListServicos);

        comboBoxServico.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null) {
                try {
                    listFuncionarios = aptidaoDAO.buscarPorServico((Servico) newval);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                observableListFuncionarios = FXCollections.observableArrayList(listFuncionarios);
                comboBoxFuncionario.setItems(observableListFuncionarios);
            }
        });
        listCliente = clienteDAO.listar();
        observableListCliente = FXCollections.observableArrayList(listCliente);

        comboBoxCliente.setItems(observableListCliente);

        conversores();
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            servPrestado.setServico((Servico) comboBoxServico.getSelectionModel().getSelectedItem());
            servPrestado.setFunc((Funcionario) comboBoxFuncionario.getSelectionModel().getSelectedItem());
            servPrestado.setCliente((Cliente) comboBoxCliente.getSelectionModel().getSelectedItem());
            servPrestado.setData(textFieldData.getText());
            servPrestado.setHrInicial(Time.valueOf(textFieldHrInicio.getText()));
            servPrestado.setHrFinal(Time.valueOf(textFieldHrFim.getText()));

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

        if (comboBoxServico.getSelectionModel().getSelectedItem() == null
                && comboBoxServico.isVisible())
            errorMessage += "Você deve escolher um serviço!\n";
        if (comboBoxFuncionario.getSelectionModel().getSelectedItem() == null
                && comboBoxFuncionario.isVisible())
            errorMessage += "Você deve escolher um funcionário!\n";
        if (comboBoxCliente.getSelectionModel().getSelectedItem() == null
                && comboBoxCliente.isVisible())
            errorMessage += "Você deve escolher um cliente!\n";

        if (textFieldData.getText() == null || textFieldData.getText().length() < 10)
            errorMessage += "Data inválida!\n";
        if (textFieldHrInicio.getText() == null || textFieldHrInicio.getText().length() == 0)
            errorMessage += "Hora de inicio inválida!\n";
        if (textFieldHrFim.getText() == null || textFieldHrFim.getText().length() == 0)
            errorMessage += "Hora de fim inválida!\n";
//            if(Time.valueOf(textFieldHrInicio.getText()).after(Time.valueOf(textFieldHrFim.getText()))){
//                errorMessage += "Hora de fim é maior do que a inicial!\n";
//            }
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
    private void conversores(){
        comboBoxServico.setConverter(new StringConverter<Servico>() {
            @Override
            public String toString(Servico object) {
                if (object != null)
                    return object.getNome();
                return null;
            }
            @Override
            public Servico fromString(String string) {
                return null;
            }
        });

        comboBoxFuncionario.setConverter(new StringConverter<Funcionario>() {
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

        comboBoxCliente.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente object) {
                if (object != null)
                    return object.getNome();
                return null;
            }
            @Override
            public Cliente fromString(String string) {
                return null;
            }
        });

    }


}
