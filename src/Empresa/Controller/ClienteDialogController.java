package Empresa.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Empresa.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Empresa.Model.domain.Funcionario;

public class ClienteDialogController implements Initializable {
    private static Scene scene = null;
    //pessoa
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldRG;
    @FXML
    private TextField textFieldCPF;
    @FXML
    private TextField textFieldEmail;
    //contato
    @FXML
    private Label labelCelular;
    @FXML
    private Label labelResidencial;
    @FXML
    private TextField textFieldCelular;
    @FXML
    private TextField textFieldResidencial;
    //email
    @FXML
    private TextField textFieldLogradouro;
    @FXML
    private TextField textFieldNumero;
    @FXML
    private TextField textFieldComplemento;
    @FXML
    private TextField textFieldBairro;
    @FXML
    private TextField textFieldCidade;
    @FXML
    private TextField textFieldEstado;
    @FXML
    private TextField textFieldCEP;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;

    private static Stage dialogStage = new Stage();
    private boolean btnConfirmarClicked = false;
    private Funcionario func;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public static void showView() throws IOException {
        Parent root = FXMLLoader.load(ClienteDialogController.class.getResource("../View/clienteDialog.fxml"));
        if (scene == null)
            scene = new Scene(root);
        dialogStage.setTitle("Novo Cliente");
        dialogStage.setScene(scene);
        dialogStage.show();
        Main.center();
    }

    public boolean isBtnConfirmarClicked() {
        return btnConfirmarClicked;
    }

    public void setBtnConfirmarClicked(boolean btnConfirmarClicked) {
        this.btnConfirmarClicked = btnConfirmarClicked;
    }

    public Funcionario getFunc() {
        return func;
    }

    public void setFunc(Funcionario func) {
        this.func = func;
        this.textFieldNome.setText(func.getNome());
        this.textFieldCPF.setText(func.getCPF());
        //this.textFieldCelular.setText(f.getTelefone());
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            //TODO
            func.setNome(textFieldNome.getText());
            func.setCPF(textFieldCPF.getText());
            //funcionario.setTelefone(textFieldCelular.getText());

            btnConfirmarClicked = true;
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
        if (textFieldRG.getText() == null || textFieldRG.getText().length() == 0)
            errorMessage += "RG inválido!\n";
        if (textFieldCPF.getText() == null || textFieldCPF.getText().length() == 0)
            errorMessage += "CPF inválido!\n";
        if (textFieldEmail.getText() == null || textFieldEmail.getText().length() == 0)
            errorMessage += "Email inválido!\n";
        if (textFieldCelular.getText() == null || textFieldCelular.getText().length() == 0)
            errorMessage += "Celular inválido!\n";
        if (textFieldResidencial.getText() == null || textFieldResidencial.getText().length() == 0)
            errorMessage += "Residencial inválido!\n";
        if((textFieldCelular.getText() == null || textFieldCelular.getText().length() == 0)
                && (textFieldResidencial.getText() == null || textFieldResidencial.getText().length() == 0))
            errorMessage += "Deve existir pelo menos um número de telefone!\n";
        if (textFieldLogradouro.getText() == null || textFieldLogradouro.getText().length() == 0)
            errorMessage += "Logradouro inválido!\n";
        if (textFieldNumero.getText() == null || textFieldNumero.getText().length() == 0)
            errorMessage += "Número inválido!\n";
        if (textFieldBairro.getText() == null || textFieldBairro.getText().length() == 0)
            errorMessage += "Bairro inválido!\n";
        if (textFieldCidade.getText() == null || textFieldCidade.getText().length() == 0)
            errorMessage += "Cidade inválida!\n";
        if (textFieldEstado.getText() == null || textFieldEstado.getText().length() == 0)
            errorMessage += "Estado inválido!\n";

        if (errorMessage.length() == 0) {
            return true;
        } else {
            alerta(errorMessage);
            return false;
        }
    }

    private void alerta(String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }
}
