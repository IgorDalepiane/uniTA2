package Empresa.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Empresa.Model.domain.Cargo;
import Empresa.Model.domain.Endereco;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Empresa.Model.domain.Funcionario;

public class FuncionarioDialogController implements Initializable {
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
    //funcionario
    @FXML
    private TextField textFieldCargo;
    @FXML
    private TextField textFieldValorHora;
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

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Funcionario func;
    private Endereco end= new Endereco();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Funcionario getFuncionario() {
        return this.func;
    }
    public Funcionario getFunc() {
        return func;
    }

    public void setFunc(Funcionario func) {
        this.func = func;
        this.textFieldNome.setText(func.getNome());
        this.textFieldCPF.setText(func.getCPF());

        if (func.getEndereco()!=null) {
            this.textFieldValorHora.setText(String.valueOf(func.getValorHora()));
            this.textFieldCargo.setText(func.getCargo().getCargoText());
            this.textFieldRG.setText(func.getRG());
            this.textFieldEmail.setText(func.getEmail());

            this.textFieldLogradouro.setText(func.getEndereco().getLogradouro());
            this.textFieldNumero.setText(String.valueOf(func.getEndereco().getNumero()));

            this.textFieldBairro.setText(func.getEndereco().getBairro());
            this.textFieldCidade.setText(func.getEndereco().getCidade());
            this.textFieldEstado.setText(func.getEndereco().getEstado());
           this.textFieldCelular.setText(func.getCelular());
            this.textFieldResidencial.setText(func.getResidencial());

            if (func.getEndereco().getComplemento().equals("N/I")){
                this.textFieldComplemento.setText("");
            }else{
                this.textFieldComplemento.setText(func.getEndereco().getComplemento());
            }
            if (func.getEndereco().getCEP().equals("N/I")){
                this.textFieldCEP.setText("");
            }else{
                this.textFieldCEP.setText(func.getEndereco().getCEP());
            }
        }

    }
    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }
    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            func.setNome(textFieldNome.getText());
            func.setRG(textFieldRG.getText());
            func.setCPF(textFieldCPF.getText());
            func.setEmail(textFieldEmail.getText());
            func.setCelular(textFieldCelular.getText());
            func.setResidencial(textFieldResidencial.getText());
            Cargo cargo = new Cargo();
            cargo.setCargo(textFieldCargo.getText());
            func.setCargo(cargo);
            func.setValorHora(Double.parseDouble(textFieldValorHora.getText()));

            end.setLogradouro(textFieldLogradouro.getText());
            end.setNumero(Integer.parseInt(textFieldNumero.getText()));
            end.setBairro(textFieldBairro.getText());
            end.setCidade(textFieldCidade.getText());
            end.setEstado(textFieldEstado.getText());
            if (textFieldComplemento.getText().length()>0)
                end.setComplemento(textFieldComplemento.getText());
            if (textFieldCEP.getText().length()>0)
                end.setCEP(textFieldCEP.getText());

            func.setEndereco(end);

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
        if (textFieldCargo.getText() == null || textFieldCargo.getText().length() == 0)
            errorMessage += "Cargo inválido!\n";
        if (textFieldValorHora.getText() == null || textFieldValorHora.getText().length() == 0)
            errorMessage += "Valor hora inválido!\n";
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
