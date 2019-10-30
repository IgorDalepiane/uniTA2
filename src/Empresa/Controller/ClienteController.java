package Empresa.Controller;

import Empresa.Exception.CadastroException;
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

public class ClienteController implements Initializable {
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

    //lista de clientes
    private List<Cliente> listClientes;
    //lista de funcionários para mostrar na tela
    private ObservableList<Cliente> observableListCliente;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final EnderecoDAO enderecoDAO = new EnderecoDAO();
    private final PessoaDAO pessoaDAO = new PessoaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clienteDAO.setConnection(connection);
        try {
            populaTabela();
        } catch (SQLException e) {
            alerta(e.getMessage());
        }
        selecionarItemTableView(null);

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView((Cliente) newValue));
    }

    /**
     * Método que popula a tabela a esquerda com informações
     * resumidas dos registros no banco de dados
     */
    public void populaTabela() throws SQLException {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("CPF"));

        listClientes = clienteDAO.listar();

        observableListCliente = FXCollections.observableArrayList(listClientes);
        tableView.setItems(observableListCliente);
    }

    /**
     * Mostra as informações detalhadas do registro selecionado na grid à direita
     *
     * @param cliente o registro selecionado
     */
    public void selecionarItemTableView(Cliente cliente) {
        if (cliente != null) {
            labelID.setText(String.valueOf(cliente.getId()));
            labelNome.setText(cliente.getNome());
            labelRG.setText(cliente.getCPF());
            labelEmail.setText(cliente.getEmail());
            Endereco end = cliente.getEndereco();
            labelEndereco.setText(end.getEstado() + ", " + end.getCidade() + ", " + end.getBairro() + ", " + end.getLogradouro() + ", " + end.getNumero() + ", " + end.getComplemento() + ", " + end.getCEP());
            labelCel.setText(cliente.getCelular());
            labelFix.setText(cliente.getResidencial());
        } else {
            labelID.setText("-");
            labelNome.setText("-");
            labelRG.setText("-");
            labelEmail.setText("-");
            labelEndereco.setText("-");
            labelCel.setText("-");
            labelFix.setText("-");
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
    public void handleBtnInserir(ActionEvent actionEvent) {
        try {
            Cliente cliente = new Cliente();
            boolean buttonConfirmarClicked = mostraCadastroCli(cliente);
            if (buttonConfirmarClicked) {
                //Conexao com as DAOs Utilizadas
                enderecoDAO.setConnection(connection);
                pessoaDAO.setConnection(connection);

                //Insere endereço no Banco
                enderecoDAO.inserir(cliente.getEndereco());

                //Atualiza o idEnd com o ultimo endereço adicionado
                cliente.setEndereco(enderecoDAO.buscarUltimoEnd());

                //Adiciona a pessoa
                pessoaDAO.inserir(cliente);
                cliente.setId(pessoaDAO.buscarUltimaPess().getId());

                //Adiciona o cliente
                clienteDAO.inserir(cliente);
                //Atualiza a tabela
                populaTabela();
            }
        } catch (CadastroException | SQLException e) {
            alerta(e.getMessage());
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
    public void handleBtnAlterar(ActionEvent actionEvent) {
        try {
            Cliente cliente = (Cliente) tableView.getSelectionModel().getSelectedItem();
            if (cliente != null) {
                boolean buttonConfirmarClicked = mostraCadastroCli(cliente);
                if (buttonConfirmarClicked) {
                    enderecoDAO.setConnection(connection);
                    pessoaDAO.setConnection(connection);

                    //Insere endereço no Banco
                    enderecoDAO.alterar(cliente.getEndereco());

                    //Adiciona a pessoa
                    pessoaDAO.alterar(cliente);

                    //Adiciona o funcionario
                    clienteDAO.alterar(cliente);
                    //Atualiza a tabela
                    populaTabela();
                }
            } else {
                alerta("Por favor, escolha um cliente na Tabela!");
            }
        } catch (SQLException | CadastroException e) {
            alerta(e.getMessage());
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
    public void handleBtnRemover(ActionEvent actionEvent) {
        try {
            Cliente cliente = (Cliente) tableView.getSelectionModel().getSelectedItem();
            if (cliente != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmação de exclusão");
                alert.setHeaderText("Deseja excluir este funcionário?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    enderecoDAO.setConnection(connection);
                    pessoaDAO.setConnection(connection);

                    enderecoDAO.remover(cliente.getEndereco());
                    pessoaDAO.remover(cliente);
                    clienteDAO.remover(cliente);

                    populaTabela();
                } else {
                    alert.close();
                }
            } else {
                alerta("Por favor, escolha um cliente na Tabela!");
            }
        } catch (SQLException e) {
            alerta(e.getMessage());
        }
    }

    /**
     * Mostra o diálogo de inserção/alteração de dados e espera
     * o usuário interagir com ele
     *
     * @param cliente o objeto a ser setado após a manipulação dos campos pelo usuário
     * @return true se o botão de confirmar foi clicado, senão false
     * @throws IOException pois carrega um arquivo fxml
     */
    public boolean mostraCadastroCli(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FuncionarioDialogController.class.getResource("../View/clienteDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Criando um Estágio de Diálogo (Stage Dialog)
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Clientes");

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Setando o funcionario no Controller.
            ClienteDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCli(cliente);

            // Mostra o Dialog e espera até que o usuário o feche
            dialogStage.showAndWait();

            return controller.isButtonConfirmarClicked();
        } catch (IOException e) {
            alerta(e.getMessage());
            return false;
        }
    }
}
