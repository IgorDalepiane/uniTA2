package Empresa.Controller;

import Empresa.Model.dao.AptidaoDAO;
import Empresa.Model.dao.Serv_ProdDAO;
import Empresa.Model.dao.ServicoDAO;
import Empresa.Model.dao.ServicoPrestadoDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.*;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
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

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
    private Label labelProdutos;

    private List<ServicoPrestado> listServicoPrestado;
    private ObservableList<ServicoPrestado> observableListServicoPrestado;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ServicoPrestadoDAO servicoPrestadoDAO = new ServicoPrestadoDAO();
    private final Serv_ProdDAO servProdDAO = new Serv_ProdDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        servicoPrestadoDAO.setConnection(connection);
        try {
            populaTabela();
        } catch (SQLException e) {
            alerta(e.getMessage());
        }
        selecionarItemTableView(null);

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView((ServicoPrestado) newValue));
    }

    public void populaTabela() throws SQLException {
        tableColumnServ.setCellValueFactory(new PropertyValueFactory<>("nomeServico"));
        tableColumnCli.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));

        listServicoPrestado = servicoPrestadoDAO.listar();

        observableListServicoPrestado = FXCollections.observableArrayList(listServicoPrestado);
        tableView.setItems(observableListServicoPrestado);
    }

    public void selecionarItemTableView(ServicoPrestado servicoPrestado) {
        if (servicoPrestado != null) {
            labelServico.setText(servicoPrestado.getNomeServico());
            labelFuncionario.setText(servicoPrestado.getFunc().getNome());
            labelCliente.setText(servicoPrestado.getNomeCliente());
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
            String dateString = format.format(Date.valueOf(servicoPrestado.getData()));
            labelData.setText(dateString);
            labelHrInicio.setText(String.valueOf(servicoPrestado.getHrInicial()));
            labelHrFim.setText(String.valueOf(servicoPrestado.getHrFinal()));
            String produtosNomes = "";
            for (Produto p : servicoPrestado.getProdUtiliz()) {
                produtosNomes += p.getNome() + ", ";
            }
            if (produtosNomes.length() > 0) {
                String produtosNomes1 = produtosNomes.substring(0, produtosNomes.length() - 1);
                String produtosNomes2 = produtosNomes1.substring(0, produtosNomes1.length() - 1);
                labelProdutos.setText(produtosNomes2);
            } else {
                labelProdutos.setText(produtosNomes);
            }

        } else {
            labelServico.setText("");
            labelFuncionario.setText("");
            labelCliente.setText("");
            labelData.setText("");
            labelHrInicio.setText("");
            labelHrFim.setText("");
            labelProdutos.setText("");
        }
    }

    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }

    public void handleBtnInserir(ActionEvent actionEvent) throws IOException, SQLException {
        ServicoPrestado servicoPrestado = new ServicoPrestado();
        boolean buttonConfirmarClicked = mostraCadastroServPres(servicoPrestado, 1);
        if (buttonConfirmarClicked) {
            //Conexao com as DAOs Utilizadas
            servicoPrestadoDAO.setConnection(connection);
            servProdDAO.setConnection(connection);
            //Insere o servico no banco
            try {
                servicoPrestadoDAO.inserir(servicoPrestado);
            } catch (MySQLIntegrityConstraintViolationException | ParseException e) {
                alerta("Servico já prestado.");
            }
            //Atualiza a tabela
            populaTabela();
        }
    }

    public void handleBtnAlterar(ActionEvent actionEvent) throws IOException {

    }

    public void handleBtnRemover(ActionEvent actionEvent) {

    }
    public void handleBtnAddProd(ActionEvent actionEvent) throws IOException, SQLException {
        ServicoPrestado servicoPrestado = (ServicoPrestado) tableView.getSelectionModel().getSelectedItem();
        if (servicoPrestado != null) {
            Serv_Prod serv_prod = new Serv_Prod();
            serv_prod.setCli(servicoPrestado.getCliente());
            serv_prod.setData(servicoPrestado.getData());
            serv_prod.setEmp(servicoPrestado.getEmpresa());
            serv_prod.setHrInicio(servicoPrestado.getHrInicial());

            boolean buttonConfirmarClicked = mostraProdUti(serv_prod);
            if (buttonConfirmarClicked) {
                try {
                    servProdDAO.setConnection(connection);
                    //Insere o servico no banco
                    servProdDAO.inserir(serv_prod);
                } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
                    alerta("Produto já adicionado");
                }

                //Atualiza a tabela
                populaTabela();
            }
        } else {
            alerta("Por favor, escolha um serviço na Tabela!");
        }
    }
    public boolean mostraProdUti(Serv_Prod serv_prod) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ServicoPresProdDialogController.class.getResource("../View/servicoPresProdDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Produto Utilizado");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o funcionario no Controller.
        ServicoPresProdDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setServProd(serv_prod);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
    public boolean mostraCadastroServPres(ServicoPrestado servicoPres, int state) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ServicoPrestadoDialogController.class.getResource("../View/servicoPrestadoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Serviço Prestado");

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o funcionario no Controller.
        ServicoPrestadoDialogController controller = loader.getController();
        //estados diferentes para cadastrar e alterar
        controller.setDialogStage(dialogStage, state);
        controller.setServPres(servicoPres);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

}

