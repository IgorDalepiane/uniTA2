package Empresa.Controller;

import Empresa.Model.Session;
import Empresa.Model.dao.EmpresaDAO;
import Empresa.Model.dao.EstoqueDAO;
import Empresa.Model.dao.ProdutoDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.*;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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
    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        estoqueDAO.setConnection(connection);
        try {
            populaTabela();
        } catch (SQLException e) {
            alerta(e.getMessage());
        }
        selecionarItemTableView(null);

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView((Estoque) newValue));
    }

    public void populaTabela() throws SQLException {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("prodNome"));
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("prodId"));

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

    public void handleBtnInserir(ActionEvent actionEvent) {
        try {
            Estoque estoque = new Estoque();
            boolean buttonConfirmarClicked = mostraCadastroEst(estoque);
            if (buttonConfirmarClicked) {

                //Conexao com as DAOs Utilizadas
                empresaDAO.setConnection(connection);
                produtoDAO.setConnection(connection);
                estoqueDAO.setConnection(connection);

                //Insere o produto no banco
                produtoDAO.inserir(estoque.getProd());
                //pega o produto que acabou de ser inserido (agora tem o id)
                estoque.setProd(produtoDAO.buscarUltimo());

                estoqueDAO.inserir(estoque);
                //Atualiza a tabela
                populaTabela();
            }
        } catch (SQLException e) {
            alerta(e.getMessage());
        }
    }

    public boolean mostraCadastroEst(Estoque es) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FuncionarioDialogController.class.getResource("../View/estoqueDialog.fxml"));
            AnchorPane page = loader.load();

            // Criando um Estágio de Diálogo (Stage Dialog)
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Produto");

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Setando o produto no Controller.
            EstoqueDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEst(es);

            // Mostra o Dialog e espera até que o usuário o feche
            dialogStage.showAndWait();

            return controller.isButtonConfirmarClicked();
        } catch (IOException e) {
            alerta(e.getMessage());
            return false;
        }
    }

    public void handleBtnAlterar(ActionEvent actionEvent) {
        try {
            Estoque esTable = (Estoque) tableView.getSelectionModel().getSelectedItem();
            int prodId = esTable.getProdId();
            if (esTable != null) {
                boolean buttonConfirmarClicked = mostraCadastroEst(esTable);
                if (buttonConfirmarClicked) {
                    //Conexao com as DAOs Utilizadas
                    empresaDAO.setConnection(connection);
                    produtoDAO.setConnection(connection);
                    estoqueDAO.setConnection(connection);

                    //Altera o produto no banco
                    Produto newProd = esTable.getProd();
                    newProd.setId(prodId);
                    produtoDAO.alterar(newProd);
                    estoqueDAO.alterar(esTable);

                    //Atualiza a tabela
                    populaTabela();
                }
            } else {
                alerta("Por favor, escolha um cliente na Tabela!");
            }
        } catch (SQLException e) {
            alerta(e.getMessage());
        }
    }

    public void handleBtnRemover(ActionEvent actionEvent) {
        try {
            Estoque esTable = (Estoque) tableView.getSelectionModel().getSelectedItem();
            int idProd = esTable.getProdId();
            if (esTable != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmação de exclusão");
                alert.setHeaderText("Deseja excluir este produto?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    estoqueDAO.setConnection(connection);
                    empresaDAO.setConnection(connection);
                    produtoDAO.setConnection(connection);

                    esTable.setProdId(idProd);
                    estoqueDAO.remover(esTable);

                    populaTabela();
                } else {
                    alert.close();
                }
            } else {
                alerta("Por favor, escolha um produto na Tabela");
            }
        } catch (SQLException e) {
            alerta(e.getMessage());
        }
    }
}
