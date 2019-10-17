package Empresa.Contato;

import java.util.List;

public class Contato {
    private String numFixo;
    private List<String> numsCel;
    private String email;

    public Contato(String numFixo, List<String> numsCel, String email) {
        this.numFixo = numFixo;
        this.numsCel = numsCel;
        this.email = email;
    }

    public String getNumFixo() {
        return numFixo;
    }

    public void setNumFixo(String numFixo) {
        this.numFixo = numFixo;
    }

    public List<String> getNumsCel() {
        return numsCel;
    }

    public void setNumsCel(List<String> numsCel) {
        this.numsCel = numsCel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
