package Empresa.Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;

import Empresa.Model.dao.ServicoPrestadoDAO;
import Empresa.Model.database.Database;
import Empresa.Model.database.DatabaseFactory;
import Empresa.Model.domain.Cargo;
import Empresa.Model.domain.Endereco;
import Empresa.Model.domain.ServicoPrestado;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import Empresa.Model.domain.Funcionario;

public class FuncionarioDialogController implements Initializable {
    @FXML
    private GridPane gridFolhaPgto;
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
    //funcionario
    @FXML
    private TextField textFieldCargo;
    @FXML
    private TextField textFieldValorHora;
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
    private Funcionario func;
    private Endereco end = new Endereco();

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void folhaPgto(ObservableList<Funcionario> list) throws SQLException {
        ServicoPrestadoDAO servicoPrestadoDAO = new ServicoPrestadoDAO();
        servicoPrestadoDAO.setConnection(connection);
        List<ServicoPrestado> servicoPrestadoList = servicoPrestadoDAO.listar();

        int row = 1;
        for (Funcionario f : list) {

            ComboBox mes = new ComboBox();
            mes.setId(String.valueOf(f.getId()));
            mes.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
            gridFolhaPgto.add(mes, 0, row);

//            mes.valueProperty().addListener(new ChangeListener() {
//                @Override
//                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                    String idAtual = mes.getId();
//                    int servPrestados = 0;
//                    double totalHoras = 0;
//                    double salario;
//                    for (ServicoPrestado sp : servicoPrestadoList) {
//                        if (sp.getData().getMonth() == (int) newValue) {
//                            if (f.getId() == sp.getFunc().getId()) {
//                                servPrestados++;
//                                totalHoras += (double) (sp.getHrFinal().getTime() - sp.getHrInicial().getTime()) / 3600000;
//                            }
//                        }
//                        salario = f.getValorHora() * totalHoras;
//                    }
//                }
//            });

            int servPrestados = 0;
            double totalHoras = 0;
            double salario;
            for (ServicoPrestado sp : servicoPrestadoList) {
                if (f.getId() == sp.getFunc().getId()) {
                    servPrestados++;
                    totalHoras += (double) (sp.getHrFinal().getTime() - sp.getHrInicial().getTime()) / 3600000;
                }
            }

            salario = f.getValorHora() * totalHoras;

            Label nome = new Label(f.getNome());
            gridFolhaPgto.add(nome, 1, row);

            Label sal = new Label("R$ " + String.valueOf(salario));
            sal.setId("sal" + String.valueOf(f.getId()));
            gridFolhaPgto.add(sal, 2, row);

            Label servPrestadosLabel = new Label(String.valueOf(servPrestados));
            gridFolhaPgto.add(servPrestadosLabel, 3, row);

            Label valorHora = new Label(String.valueOf(f.getValorHora()));
            gridFolhaPgto.add(valorHora, 4, row);

            row++;
        }
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

    public void setFunc(Funcionario func) {
        this.func = func;
        this.textFieldNome.setText(func.getNome());
        this.textFieldCPF.setText(func.getCPF());

        if (func.getEndereco() != null) {
            this.textFieldIdEndereco.setText(String.valueOf(func.getEndereco().getId()));
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

            if (func.getEndereco().getComplemento().equals("N/I")) {
                this.textFieldComplemento.setText("");
            } else {
                this.textFieldComplemento.setText(func.getEndereco().getComplemento());
            }
            if (func.getEndereco().getCEP().equals("N/I")) {
                this.textFieldCEP.setText("");
            } else {
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
        if ((textFieldCelular.getText() == null || textFieldCelular.getText().length() == 0)
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

    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }

    /**
     * Botão fechar da folha de pagamentos
     *
     * @param actionEvent
     */
    public void handleButtonFechar(ActionEvent actionEvent) {
        dialogStage.close();
    }
}
