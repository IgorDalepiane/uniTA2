package Empresa.Controller;

import Empresa.Model.dao.*;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ServicoPresProdDialogController implements Initializable {

    @FXML
    private ComboBox comboBoxEstoque;

    @FXML
    private TextField textFieldQuant;

    private List<Estoque> listEstoque;
    private ObservableList<Estoque> observableListEstoque;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final Serv_ProdDAO servProdDAO = new Serv_ProdDAO();
    private final EstoqueDAO estoqueDAO = new EstoqueDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Serv_Prod servProd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoDAO.setConnection(connection);
        servProdDAO.setConnection(connection);
        estoqueDAO.setConnection(connection);
    }


    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setServProd(Serv_Prod servProd) throws SQLException {
        this.servProd=servProd;
        listEstoque = estoqueDAO.listar();
        observableListEstoque = FXCollections.observableArrayList(listEstoque);

        comboBoxEstoque.setItems(observableListEstoque);
        comboBoxEstoque.setConverter(new StringConverter<Estoque>() {
            @Override
            public String toString(Estoque object) {
                if (object != null)
                    return object.getProdNome();
                return null;
            }
            @Override
            public Estoque fromString(String string) {
                return null;
            }
        });
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() throws SQLException {
        if (validarEntradaDeDados()) {
            Estoque est =(Estoque)comboBoxEstoque.getSelectionModel().getSelectedItem();
            servProd.setProd(est.getProd());
            servProd.setQnt(Integer.parseInt(textFieldQuant.getText()));
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleButtonCancelar() {
        dialogStage.close();
    }

    private boolean validarEntradaDeDados() throws SQLException {
        String errorMessage = "";
        if (comboBoxEstoque.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione algum produto!\n";
        }
        if (textFieldQuant.getText() == null || textFieldQuant.getText().length() ==0)
            errorMessage += "Quantidade Invalida!\n";
        //if (Integer.valueOf(textFieldQuant.getText())>estoqueDAO.buscar((Estoque)comboBoxEstoque.getSelectionModel().getSelectedItem()).getQuant()){
        //    errorMessage += "Quantidade ultrapassa o estoque!\n";
        //}

        if (errorMessage.length() == 0) {
            return true;
        } else {
            alerta(errorMessage);
            return false;
        }
    }

    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }
}
