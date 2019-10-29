package Empresa.Controller;

import Empresa.Model.Session;
import Empresa.Model.dao.*;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.*;
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
import java.util.ResourceBundle;

public class ServicoController implements Initializable {

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn tableColumnNome;
    @FXML
    private TableColumn tableColumnPreco;

    @FXML
    private Label labelID;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelDescricao;
    @FXML
    private Label labelPreco;
    @FXML
    private Label labelAptos;

    private List<Servico> listServico;
    private ObservableList<Servico> observableListServico;

    private Empresa empresa = Session.get();

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ServicoDAO servicoDAO = new ServicoDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        servicoDAO.setConnection(connection);
        populaTabela();
        selecionarItemTableView(null);

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView((Servico) newValue));
    }

    public void populaTabela() {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        listServico = servicoDAO.listar();

        observableListServico = FXCollections.observableArrayList(listServico);
        tableView.setItems(observableListServico);

    }

    public void selecionarItemTableView(Servico servico) {
        if (servico != null) {
            labelID.setText(String.valueOf(servico.getId()));
            labelNome.setText(servico.getNome());
            labelDescricao.setText(servico.getDescricao());
            labelPreco.setText(String.valueOf(servico.getPreco()));
            String aptosNomes = "";
            for (Funcionario f:servico.getAptos()) {
                aptosNomes+=f.getNome()+", ";
            }
            if (aptosNomes.length()>0) {
                String aptosNomes1 = aptosNomes.substring(0, aptosNomes.length() - 1);
                String aptosNomes2 = aptosNomes1.substring(0, aptosNomes1.length() - 1);
                labelAptos.setText(aptosNomes2);
            }else{
                labelAptos.setText(aptosNomes);
            }

        } else {
            labelID.setText("");
            labelNome.setText("");
            labelDescricao.setText("");
            labelPreco.setText("");
            labelAptos.setText("");
        }
    }


    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }

    public void handleBtnInserir(ActionEvent actionEvent) throws IOException {
        Servico servico = new Servico();
        boolean buttonConfirmarClicked = mostraCadastroServ(servico);
        if (buttonConfirmarClicked) {

        }
    }

    public void handleBtnAlterar(ActionEvent actionEvent) throws IOException {

    }

    public void handleBtnRemover(ActionEvent actionEvent) {

    }

    public boolean mostraCadastroServ(Servico servico) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ServicoDialogController.class.getResource("../View/servicoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Serviço");

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o funcionario no Controller.
        ServicoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setServ(servico);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }
}
