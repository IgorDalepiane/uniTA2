package Empresa.Controller;

import Empresa.Main;
import Empresa.Model.Session;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.dao.EmpresaDAO;
import Empresa.Model.domain.Empresa;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class PagInicialController implements Initializable {
    //scene individual do controller, 1 ctrl = 1 view
    private static Scene scene;
    private Empresa empresa = Session.get();
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        empresaDAO.setConnection(connection);
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


    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }
}
