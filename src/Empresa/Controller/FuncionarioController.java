package Empresa.Controller;

import Empresa.Exception.CadastroException;
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
    //tabela lateral esquerda
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn tableColumnCPF;
    @FXML
    private TableColumn tableColumnNome;

    //itens da grid a direita
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

    //lista de funcionários
    private List<Funcionario> listFuncionario;
    //lista de funcionários para mostrar na tela
    private ObservableList<Funcionario> observableListFuncionario;

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
        try {
            populaTabela();
        } catch (SQLException e) {
            alerta(e.getMessage());
        }
        selecionarItemTableView(null);

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView((Funcionario) newValue));
    }

    /**
     * Método que popula a tabela a esquerda com informações
     * resumidas dos registros no banco de dados
     */
    public void populaTabela() throws SQLException {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("CPF"));

        listFuncionario = funcionarioDAO.listar();

        observableListFuncionario = FXCollections.observableArrayList(listFuncionario);
        tableView.setItems(observableListFuncionario);
    }

    /**
     * Mostra as informações detalhadas do registro selecionado na grid à direita
     *
     * @param funcionario o registro selecionado
     */
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

    /**
     * Reutilização de código, o método mostra um Alerta
     *
     * @param texto o corpo da mensagem
     */
    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }

    /**
     * Método que lida com o clique do botão de inserir
     * Chama o diálogo de inserção, verifica os dados e envia ao banco de dados
     *
     * @param actionEvent listener
     * @throws IOException pois carrega um arquivo fxml
     */
    public void handleBtnInserir(ActionEvent actionEvent) throws IOException, SQLException, CadastroException {
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
            try {
                pessoaDAO.inserir(funcionario);
            } catch (CadastroException e) {
                alerta(e.getMessage());
            }

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

    /**
     * Método que lida com o clique do botão de alterar
     * Chama o diálogo de alteração com os dados do registro atual,
     * verifica os dados alterados e envia ao banco de dados
     *
     * @param actionEvent listener
     * @throws IOException pois carrega um arquivo fxml
     */
    public void handleBtnAlterar(ActionEvent actionEvent) throws IOException, SQLException, CadastroException {
        Funcionario funcionario = (Funcionario) tableView.getSelectionModel().getSelectedItem();
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
            alerta("Por favor, escolha um cliente na Tabela!");
        }
    }

    /**
     * Método que lida com o clique do botão de remoção
     * Chama o diálogo de confirmação de exclusão e apaga
     * o registro que o usuário selecionou do banco de dados
     *
     * @param actionEvent listener
     * @throws IOException pois carrega um arquivo fxml
     */
    public void handleBtnRemover(ActionEvent actionEvent) throws SQLException {
        Funcionario funcionario = (Funcionario) tableView.getSelectionModel().getSelectedItem();
        if (funcionario != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de exclusão");
            alert.setHeaderText("Deseja excluir este funcionário?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                enderecoDAO.setConnection(connection);
                pessoaDAO.setConnection(connection);
                cargoDAO.setConnection(connection);

                enderecoDAO.remover(funcionario.getEndereco());
                pessoaDAO.remover(funcionario);
                funcionarioDAO.remover(funcionario);

                populaTabela();
            } else {
                alert.close();
            }
        } else {
            alerta("Por favor, escolha um cliente na Tabela!");
        }
    }

    /**
     * Mostra o diálogo de inserção/alteração de dados e espera
     * o usuário interagir com ele
     *
     * @param funcionario o objeto a ser setado após a manipulação dos campos pelo usuário
     * @return true se o botão de confirmar foi clicado, senão false
     * @throws IOException pois carrega um arquivo fxml
     */
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

    public void handleFolhaPgto(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FuncionarioDialogController.class.getResource("../View/funcionarioFolhaPgto.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Folha de Pagamentos");

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Popular os dados da grid
        FuncionarioDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.folhaPgto(observableListFuncionario);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();
    }
}
