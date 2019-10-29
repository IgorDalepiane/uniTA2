package Empresa.Controller;

import Empresa.Model.domain.Cargo;
import Empresa.Model.domain.Endereco;
import Empresa.Model.domain.Funcionario;
import Empresa.Model.domain.Servico;
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


public class ServicoDialogController implements Initializable{
        private static Scene scene = null;
        //pessoa
        @FXML
        private TextField textFieldNome;
        @FXML
        private TextField textFieldDescricao;
        @FXML
        private TextField textFieldPreco;
        @FXML
        private TextField textFieldAptos;
        //contato
        @FXML
        private Button btnConfirmar;
        @FXML
        private Button btnCancelar;

        private Stage dialogStage;
        private boolean buttonConfirmarClicked = false;
        private Servico serv;
        @Override
        public void initialize(URL url, ResourceBundle rb) {
        }


        public Stage getDialogStage() {
            return dialogStage;
        }

        public void setDialogStage(Stage dialogStage) {
            this.dialogStage = dialogStage;
        }

        public Servico getServico() {
            return this.serv;
        }

        public void setServ(Servico serv) {
            this.serv = serv;
            this.textFieldNome.setText(serv.getNome());
            this.textFieldDescricao.setText(serv.getDescricao());
            this.textFieldPreco.setText(String.valueOf(serv.getPreco()));

        }
        public boolean isButtonConfirmarClicked() {
            return buttonConfirmarClicked;
        }
        @FXML
        public void handleButtonConfirmar() {
            if (validarEntradaDeDados()) {
                serv.setNome(textFieldNome.getText());
                serv.setDescricao(textFieldDescricao.getText());
                serv.setPreco(Float.valueOf(textFieldPreco.getText()));

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
            if (textFieldDescricao.getText() == null || textFieldDescricao.getText().length() == 0)
                errorMessage += "Descrição inválida!\n";
            if (textFieldPreco.getText() == null || textFieldPreco.getText().length() == 0)
                errorMessage += "CPF inválido!\n";

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

