package Empresa.Controller;

import Empresa.Model.Session;
import Empresa.Model.dao.*;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.*;
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

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ServicoDAO servicoDAO = new ServicoDAO();
    private final AptidaoDAO aptidaoDAO = new AptidaoDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        servicoDAO.setConnection(connection);
        aptidaoDAO.setConnection(connection);
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
            for (Funcionario f : servico.getAptos()) {
                aptosNomes += f.getNome() + ", ";
            }
            if (aptosNomes.length() > 0) {
                String aptosNomes1 = aptosNomes.substring(0, aptosNomes.length() - 1);
                String aptosNomes2 = aptosNomes1.substring(0, aptosNomes1.length() - 1);
                labelAptos.setText(aptosNomes2);
            } else {
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
            //Conexao com as DAOs Utilizadas
            servicoDAO.setConnection(connection);

            //Insere o servico no banco
            servicoDAO.inserir(servico);

            //Atualiza a tabela
            populaTabela();
        }
    }

    public void handleBtnAlterar(ActionEvent actionEvent) throws IOException {
        Servico servico = (Servico) tableView.getSelectionModel().getSelectedItem();//Obtendo cliente selecionado
        if (servico != null) {
            boolean buttonConfirmarClicked = mostraCadastroServ(servico);
            if (buttonConfirmarClicked) {
                servicoDAO.setConnection(connection);

                //Insere endereço no Banco
                servicoDAO.alterar(servico);

                //Atualiza a tabela
                populaTabela();
            }
        } else {
            alerta("Por favor, escolha um cliente na Tabela!");
        }
    }

    public void handleBtnRemover(ActionEvent actionEvent) {
        Servico servico = (Servico) tableView.getSelectionModel().getSelectedItem();
        if (servico != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de exclusão");
            alert.setHeaderText("Deseja excluir este serviço?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                servicoDAO.setConnection(connection);

                servicoDAO.remover(servico);

                //Atualiza a tabela
                populaTabela();
            } else {
                alert.close();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela!");
            alert.show();
        }
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

    public void handleBtnInserirApto(ActionEvent actionEvent) throws IOException {
        Servico servico = (Servico) tableView.getSelectionModel().getSelectedItem();
        if (servico != null) {
            Apto apto = new Apto();
            apto.setServico(servico);

            boolean buttonConfirmarClicked = mostraAptoServ(apto);
            if (buttonConfirmarClicked) {
                try {
                    aptidaoDAO.setConnection(connection);
                    //Insere o servico no banco
                    aptidaoDAO.inserir(apto);
                } catch (MySQLIntegrityConstraintViolationException e) {
                    alerta(e.getMessage());
                }

                //Atualiza a tabela
                populaTabela();
            }
        } else {
            alerta("Por favor, escolha um serviço na Tabela!");
        }
    }

    public boolean mostraAptoServ(Apto apto) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ServicoAptoDialogController.class.getResource("../View/servicoAptoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Funcionário Apto");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o funcionario no Controller.
        ServicoAptoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setApto(apto);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }

    public void handleBtnRemoverApto(ActionEvent actionEvent) throws IOException {
        Servico servico = (Servico) tableView.getSelectionModel().getSelectedItem();
        if (servico != null) {
            Apto apto = new Apto();
            apto.setServico(servico);

            boolean buttonConfirmarClicked = mostraAptoServ(apto);
            if (buttonConfirmarClicked) {
                aptidaoDAO.setConnection(connection);
                //Remove o servico do banco
                aptidaoDAO.remover(apto);
                //Atualiza a tabela
                populaTabela();
            }
        } else {
            alerta("Por favor, escolha um serviço na Tabela!");
        }
    }
}
