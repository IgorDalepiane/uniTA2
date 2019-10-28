package Empresa.Controller;

import Empresa.Main;
import Empresa.Model.Session;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.dao.EmpresaDAO;
import Empresa.Model.domain.Empresa;
import com.lowagie.text.Anchor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class FuncionarioController implements Initializable {
    public TableView tableView;
    public TableColumn tableColumnCPF;
    public TableColumn tableColumnNome;

    private Empresa empresa = Session.get();

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        empresaDAO.setConnection(connection);
    }

    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }

    public void handleBtnInserir(ActionEvent actionEvent) {
    }

    public void handleBtnAlterar(ActionEvent actionEvent) {
    }

    public void handleBtnRemover(ActionEvent actionEvent) {
    }
}
