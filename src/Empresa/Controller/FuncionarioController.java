package Empresa.Controller;

import Empresa.Main;
import Empresa.Model.Session;
import Empresa.Model.dao.FuncionarioDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.dao.EmpresaDAO;
import Empresa.Model.domain.Celular;
import Empresa.Model.domain.Empresa;
import Empresa.Model.domain.Endereco;
import Empresa.Model.domain.Funcionario;
import com.lowagie.text.Anchor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class FuncionarioController implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn tableColumnCPF;
    @FXML
    private TableColumn tableColumnNome;

    @FXML
    private Label labelID;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelRG;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelEndereco;
    @FXML
    private Label labelCel;
    @FXML
    private Label labelFix;
    @FXML
    private Label labelValorHora;
    @FXML
    private Label labelCargo;

    private List<Funcionario> listFuncionario;
    private ObservableList<Funcionario> observableListFuncionario;

    private Empresa empresa = Session.get();

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        funcionarioDAO.setConnection(connection);
        populaTabela();
        selecionarItemTableView(null);

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView((Funcionario) newValue));
    }

    public void populaTabela() {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("CPF"));

        listFuncionario = funcionarioDAO.listar();

        observableListFuncionario = FXCollections.observableArrayList(listFuncionario);
        tableView.setItems(observableListFuncionario);
    }

    public void selecionarItemTableView(Funcionario funcionario) {
        if (funcionario != null) {
            labelID.setText(String.valueOf(funcionario.getId()));
            labelNome.setText(funcionario.getNome());
            labelRG.setText(funcionario.getCPF());
            labelEmail.setText(funcionario.getEmail());
            Endereco end = funcionario.getEndereco();
            labelEndereco.setText(end.getEstado() + ", " + end.getCidade() + ", " + end.getBairro() + ", " + end.getLogradouro() + ", " + end.getNumero() + ", " + end.getComplemento() + ", " + end.getCEP());
            List<Celular> celulares = funcionario.getCelulares();
            String celularesStr = "";
            String fixosStr = "";
            for (Celular cel : celulares) {
                    if (cel.isFixo()) {
                        fixosStr += cel.getNum() + " ";
                    } else {
                        celularesStr += cel.getNum() + " ";
                    }
            }
            labelCel.setText(celularesStr);
            labelFix.setText(fixosStr);
            labelValorHora.setText("R$ " + String.valueOf(funcionario.getValorHora()));
            labelCargo.setText(funcionario.getCargo());
        } else {
            labelID.setText("-");
            labelNome.setText("-");
            labelRG.setText("-");
            labelEmail.setText("-");
            labelEndereco.setText("-");
            labelCel.setText("-");
            labelFix.setText("-");
            labelValorHora.setText("-");
            labelCargo.setText("-");
        }
    }


    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }

    public void handleBtnInserir(ActionEvent actionEvent) {
    }

    public void handleBtnAlterar(ActionEvent actionEvent) {
    }

    public void handleBtnRemover(ActionEvent actionEvent) {
    }
}
