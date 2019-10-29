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
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final EnderecoDAO enderecoDAO = new EnderecoDAO();
    private final PessoaDAO pessoaDAO = new PessoaDAO();
    private final CargoDAO cargoDAO = new CargoDAO();

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
            labelCel.setText(funcionario.getCelular());
            labelFix.setText(funcionario.getResidencial());
            labelValorHora.setText("R$ " + String.valueOf(funcionario.getValorHora()));
            labelCargo.setText(funcionario.getCargo().getCargoText());
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

    public void handleBtnInserir(ActionEvent actionEvent) throws IOException {
        Funcionario funcionario = new Funcionario();
        boolean buttonConfirmarClicked = mostraCadastroFunc(funcionario);
        if (buttonConfirmarClicked) {

            //Conexao com as DAOs Utilizadas
            enderecoDAO.setConnection(connection);
            pessoaDAO.setConnection(connection);
            cargoDAO.setConnection(connection);

            //Insere endereço no Banco
            enderecoDAO.inserir(funcionario.getEndereco());

            //Atualiza o idEnd com o ultimo endereço adicionado
            funcionario.setEndereco(enderecoDAO.buscarUltimoEnd());

            //Adiciona a pessoa
            pessoaDAO.inserir(funcionario);

            //Atualiza o idPessoa de funcionario com a ultima pessoa adicionada
            funcionario.setId(pessoaDAO.buscarUltimaPess().getId());


            //Verifica se existe um cargo com este nome
            Cargo cargoBD = cargoDAO.buscarPeloNome(funcionario.getCargo().getCargoText());

            //Se ja existir um cargo com este nome, apenas seta com o mesmo
            if (cargoBD != null) {
                funcionario.setCargo(cargoBD);
            } else {
                //Caso nao, insere o novo cargo no banco
                cargoDAO.inserir(funcionario.getCargo());
                //E atualiza o idCargo de funcionario com o ultimo adicionado
                funcionario.setCargo(cargoDAO.buscarUltimoCargo());
            }

            //Adiciona o funcionario
            funcionarioDAO.inserir(funcionario);
            //Atualiza a tabela
            populaTabela();
        }
    }

    public void handleBtnAlterar(ActionEvent actionEvent) throws IOException {
        Funcionario funcionario = (Funcionario) tableView.getSelectionModel().getSelectedItem();//Obtendo cliente selecionado
        if (funcionario != null) {
            boolean buttonConfirmarClicked = mostraCadastroFunc(funcionario);
            if (buttonConfirmarClicked) {
                enderecoDAO.setConnection(connection);
                pessoaDAO.setConnection(connection);
                cargoDAO.setConnection(connection);

                //Insere endereço no Banco
                enderecoDAO.alterar(funcionario.getEndereco());

                //Adiciona a pessoa
                pessoaDAO.alterar(funcionario);


                //Verifica se existe um cargo com este nome
                Cargo cargoBD = cargoDAO.buscarPeloNome(funcionario.getCargo().getCargoText());

                //Se ja existir um cargo com este nome, apenas seta com o mesmo
                if (cargoBD != null) {
                    funcionario.setCargo(cargoBD);
                } else {
                    //Caso nao, insere o novo cargo no banco
                    cargoDAO.inserir(funcionario.getCargo());
                    //E atualiza o idCargo de funcionario com o ultimo adicionado
                    funcionario.setCargo(cargoDAO.buscarUltimoCargo());
                }

                //Adiciona o funcionario
                funcionarioDAO.alterar(funcionario);
                //Atualiza a tabela
                populaTabela();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela!");
            alert.show();
        }
    }

    public void handleBtnRemover(ActionEvent actionEvent) {
        Funcionario funcionario = (Funcionario) tableView.getSelectionModel().getSelectedItem();//Obtendo cliente selecionado
        if (funcionario != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de exclusão");
            alert.setHeaderText("Deseja excluir este funcionário?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                enderecoDAO.setConnection(connection);
                pessoaDAO.setConnection(connection);
                cargoDAO.setConnection(connection);

                enderecoDAO.remover(funcionario.getEndereco());

                //Adiciona a pessoa
                pessoaDAO.remover(funcionario);

                //Adiciona o funcionario
                funcionarioDAO.remover(funcionario);
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

    public boolean mostraCadastroFunc(Funcionario funcionario) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FuncionarioDialogController.class.getResource("../View/funcionarioDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Funcionários");

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o funcionario no Controller.
        FuncionarioDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setFunc(funcionario);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }
}
