package Empresa.Controller;

import Empresa.Model.Session;
import Empresa.Model.dao.ClienteDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.dao.EmpresaDAO;
import Empresa.Model.domain.Cliente;
import Empresa.Model.domain.Empresa;
import Empresa.Model.domain.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class ClienteController implements Initializable {
    public TableView tableView;
    public TableColumn tableColumnCPF;
    public TableColumn tableColumnNome;

    private List<Cliente> listCliente;
    private ObservableList<Cliente> observableListCliente;

    private Empresa empresa = Session.get();

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clienteDAO.setConnection(connection);
        populaTabela();

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView((Funcionario) newValue));
    }

    private void populaTabela() {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("CPF"));

        listCliente = clienteDAO.listar();

        observableListCliente = FXCollections.observableArrayList(listCliente);
        tableView.setItems(observableListCliente);
    }

    private void selecionarItemTableView(Funcionario newValue) {

    }

    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }

    public void handleBtnInserir(ActionEvent actionEvent) throws IOException {
        ClienteDialogController.showView();
    }

    public void handleBtnAlterar(ActionEvent actionEvent) {
    }

    public void handleBtnRemover(ActionEvent actionEvent) {
    }
}
