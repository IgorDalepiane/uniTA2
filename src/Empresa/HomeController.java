package Empresa;

import Empresa.Pessoa.PessoaController;
import Empresa.Servicos.ServicoController;
import Empresa.Servicos.ServicoPrestadoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController {
    @FXML private ComboBox<Empresa> empresaComboBox;

    public void setDataComboBox() {
        empresaComboBox.getItems().addAll(
                new Empresa(0, "Empresa do Jão", new ServicoController(), new PessoaController(), new PessoaController(), new ServicoPrestadoController()),
                new Empresa(1, "Empresa de sabão", new ServicoController(), new PessoaController(), new PessoaController(), new ServicoPrestadoController())
        );
        empresaComboBox.setConverter(new StringConverter<Empresa>() {
            @Override
            public String toString(Empresa object) {
                if(object != null)
                    return object.getNome();
                return null;
            }

            @Override
            public Empresa fromString(String string) {
                return empresaComboBox.getItems()
                        .stream()
                        .filter(e -> e.getNome().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
        empresaComboBox.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
                System.out.println(newval.getNome() + ". ID: " + newval.getId());
        });
    }

    public void handleLogin(ActionEvent actionEvent) {
        
    }

    public void handleNewacc(ActionEvent actionEvent) {

    }

    public void handleForgotPw(ActionEvent actionEvent) {

    }
}
