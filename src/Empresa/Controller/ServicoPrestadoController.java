package Empresa.Controller;

import Empresa.Model.dao.AptidaoDAO;
import Empresa.Model.dao.ServicoDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.Apto;
import Empresa.Model.domain.Funcionario;
import Empresa.Model.domain.Servico;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServicoPrestadoController implements Initializable {

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn tableColumnServ;
    @FXML
    private TableColumn tableColumnCli;

    @FXML
    private Label labelServico;
    @FXML
    private Label labelFuncionario;
    @FXML
    private Label labelCliente;
    @FXML
    private Label labelData;
    @FXML
    private Label labelHrInicio;
    @FXML
    private Label labelHrFim;
    @FXML
    private Label produtos;



    private List<Servico> listServicoPrestado;
    private ObservableList<Servico> observableListServicoPrestado;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ServicoDAO servicoDAO = new ServicoDAO();
    private final AptidaoDAO aptidaoDAO = new AptidaoDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        servicoDAO.setConnection(connection);
//        aptidaoDAO.setConnection(connection);
//        populaTabela();
//        selecionarItemTableView(null);
//
//        tableView.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue) -> selecionarItemTableView((Servico) newValue));
    }

    public void populaTabela() {
//        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
//        tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
//
//        listServico = servicoDAO.listar();
//
//        observableListServico = FXCollections.observableArrayList(listServico);
//        tableView.setItems(observableListServico);

    }

    public void selecionarItemTableView(Servico servico) {

    }

    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }

    public void handleBtnInserir(ActionEvent actionEvent) throws IOException, MySQLIntegrityConstraintViolationException {

    }

    public void handleBtnAlterar(ActionEvent actionEvent) throws IOException {

    }

    public void handleBtnRemover(ActionEvent actionEvent) {

    }

//    public boolean mostraCadastroServ(Servico servico, int state) throws IOException {
//
//
//    }

}

