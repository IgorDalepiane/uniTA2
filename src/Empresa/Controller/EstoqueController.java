package Empresa.Controller;

import Empresa.Model.Session;
import Empresa.Model.dao.EstoqueDAO;
import Empresa.Model.dao.ProdutoDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class EstoqueController implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn tableColumnID;
    @FXML
    private TableColumn tableColumnNome;

    @FXML
    private Label labelID;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelDescricao;
    @FXML
    private Label labelPreco;
    @FXML
    private Label labelQuantidade;


    private List<Estoque> listEstoque;
    private ObservableList<Estoque> observableListEstoque;

    private Empresa empresa = Session.get();

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final EstoqueDAO estoqueDAO = new EstoqueDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        estoqueDAO.setConnection(connection);
        populaTabela();
        selecionarItemTableView(null);

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView((Estoque) newValue));
    }

    public void populaTabela() {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("idProd"));

        listEstoque = estoqueDAO.listar();

        observableListEstoque = FXCollections.observableArrayList(listEstoque);
        tableView.setItems(observableListEstoque);
    }

    public void selecionarItemTableView(Estoque es) {
        if (es != null) {
            labelID.setText(String.valueOf(es.getProd().getId()));
            labelNome.setText(es.getProd().getNome());
            labelDescricao.setText((es.getProd().getDescricao() != null) ? es.getProd().getDescricao() : "N/A");
            labelPreco.setText(String.valueOf(es.getProd().getPreco()));
            labelQuantidade.setText(String.valueOf(es.getQuant()));
        } else {
            labelID.setText("-");
            labelNome.setText("-");
            labelDescricao.setText("-");
            labelPreco.setText("-");
            labelQuantidade.setText("-");
        }
    }


    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }

    public void handleBtnInserir(ActionEvent actionEvent) throws IOException {
    }

    public void handleBtnAlterar(ActionEvent actionEvent) {
    }

    public void handleBtnRemover(ActionEvent actionEvent) {
    }
}
