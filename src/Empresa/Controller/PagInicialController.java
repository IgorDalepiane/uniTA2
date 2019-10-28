package Empresa.Controller;

import Empresa.Main;
import Empresa.Model.Session;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.dao.EmpresaDAO;
import Empresa.Model.domain.Empresa;
import Empresa.Model.domain.Funcionario;
import com.lowagie.text.Anchor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class PagInicialController implements Initializable {
    //scene individual do controller, 1 ctrl = 1 view
    private static Scene scene;
    //anchor pane principal
    @FXML
    private AnchorPane anchor;

//    //Atributos para manipulação de Banco de Dados
//    private final Database database = DatabaseFactory.getDatabase("mysql");
//    private final Connection connection = database.conectar();
//    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Carrega o arquivo fxml e define a scene (View)
     *
     * @throws IOException
     */
    public static void showView() throws IOException {
        Parent root = FXMLLoader.load(PagInicialController.class.getResource("../View/pagInicial.fxml"));
        if (scene == null)
            scene = new Scene(root, 600, 400);
        Main.stage.setTitle("Administração da " + Session.get().getNome());
        Main.stage.setScene(scene);
        Main.center();
    }


    /**
     * Mostra o diálogo de erro ao usuário
     * @param texto o corpo da mensagem
     */
    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }

    /**
     * Handle do click no botão do funcionário, muda o AnchorPane da página
     * @param actionEvent
     * @throws IOException porque carrega o arquivo fxml
     */
    public void handleFuncionario(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(PagInicialController.class.getResource("../View/funcionario.fxml"));
        anchor.getChildren().setAll(root);
        System.out.println("a");
    }

    public void handleCliente(ActionEvent actionEvent) {

    }

    public void handleServico(ActionEvent actionEvent) {
    }

    public void handleServicoPrestado(ActionEvent actionEvent) {
    }

    public void handleProduto(ActionEvent actionEvent) {
    }
}
