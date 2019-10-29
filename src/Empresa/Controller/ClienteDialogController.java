package Empresa.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Empresa.Model.domain.Cargo;
import Empresa.Model.domain.Cliente;
import Empresa.Model.domain.Endereco;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ClienteDialogController implements Initializable {
    //campospessoa
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
    private TextField textFieldCelular;
    @FXML
    private TextField textFieldResidencial;
    //email
    @FXML
    private TextField textFieldIdEndereco;
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

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Cliente cli;
    private Endereco end = new Endereco();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Cliente getCliente() {
        return this.cli;
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
        this.textFieldNome.setText(cli.getNome());
        this.textFieldCPF.setText(cli.getCPF());

        if (cli.getEndereco() != null) {
            this.textFieldIdEndereco.setText(String.valueOf(cli.getEndereco().getId()));
            this.textFieldRG.setText(cli.getRG());
            this.textFieldEmail.setText(cli.getEmail());

            this.textFieldLogradouro.setText(cli.getEndereco().getLogradouro());
            this.textFieldNumero.setText(String.valueOf(cli.getEndereco().getNumero()));

            this.textFieldBairro.setText(cli.getEndereco().getBairro());
            this.textFieldCidade.setText(cli.getEndereco().getCidade());
            this.textFieldEstado.setText(cli.getEndereco().getEstado());
            this.textFieldCelular.setText(cli.getCelular());
            this.textFieldResidencial.setText(cli.getResidencial());

            if (cli.getEndereco().getComplemento().equals("N/I")) {
                this.textFieldComplemento.setText("");
            } else {
                this.textFieldComplemento.setText(cli.getEndereco().getComplemento());
            }
            if (cli.getEndereco().getCEP().equals("N/I")) {
                this.textFieldCEP.setText("");
            } else {
                this.textFieldCEP.setText(cli.getEndereco().getCEP());
            }
        }

    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            cli.setNome(textFieldNome.getText());
            cli.setRG(textFieldRG.getText());
            cli.setCPF(textFieldCPF.getText());
            cli.setEmail(textFieldEmail.getText());
            cli.setCelular(textFieldCelular.getText());
            cli.setResidencial(textFieldResidencial.getText());
            if (this.textFieldIdEndereco.getText().length() > 0) {
                end.setId(Integer.parseInt(this.textFieldIdEndereco.getText()));
            }
            end.setLogradouro(textFieldLogradouro.getText());
            end.setNumero(Integer.parseInt(textFieldNumero.getText()));
            end.setBairro(textFieldBairro.getText());
            end.setCidade(textFieldCidade.getText());
            end.setEstado(textFieldEstado.getText());
            if (textFieldComplemento.getText().length() > 0)
                end.setComplemento(textFieldComplemento.getText());
            if (textFieldCEP.getText().length() > 0)
                end.setCEP(textFieldCEP.getText());

            cli.setEndereco(end);

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
        if ((textFieldCelular.getText() == null || textFieldCelular.getText().length() == 0)
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

    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }
}
