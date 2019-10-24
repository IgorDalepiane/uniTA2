package Empresa.Model.domain;

import java.io.Serializable;
import java.util.List;

public class Celular implements Serializable {
    private int id;
    private String numFixo;
    private List<String> numsCel;

    public Celular(int id) {
        this.id = id;
    }

    public Celular() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


}
