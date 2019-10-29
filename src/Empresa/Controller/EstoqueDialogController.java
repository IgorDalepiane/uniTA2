package Empresa.Controller;

import Empresa.Model.Session;
import Empresa.Model.domain.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EstoqueDialogController implements Initializable {
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldDescricao;
    @FXML
    private TextField textFieldPreco;
    @FXML
    private TextField textFieldQuantidade;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Estoque est;
    private Produto prod = new Produto();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Estoque getEst() {
        return est;
    }

    public void setEst(Estoque est) {
        this.est = est;
        if (est.getProd() != null) {
            this.textFieldNome.setText(est.getProd().getNome());
            this.textFieldDescricao.setText(est.getProd().getDescricao());
            this.textFieldPreco.setText(String.valueOf(est.getProd().getPreco()));
        }
        this.textFieldQuantidade.setText(String.valueOf(est.getQuant()));
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            prod.setNome(textFieldNome.getText());
            prod.setDescricao(textFieldDescricao.getText());
            prod.setPreco(Float.parseFloat(textFieldPreco.getText()));

            est.setProd(prod);
            est.setEmpresa(Session.get());
            est.setQuant(Integer.parseInt(textFieldQuantidade.getText()));

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleButtonCancelar() {
        dialogStage.close();
    }

    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldNome.getText() == null || textFieldNome.getText().length() == 0)
            errorMessage += "Nome inválido!\n";
        if (textFieldPreco.getText() == null || textFieldPreco.getText().length() == 0)
            errorMessage += "Preço inválido!\n";
        if (textFieldQuantidade.getText() == null || textFieldQuantidade.getText().length() == 0)
            errorMessage += "Quantidade inválida!\n";

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
