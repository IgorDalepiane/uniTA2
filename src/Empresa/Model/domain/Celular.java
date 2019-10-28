package Empresa.Model.domain;

import java.io.Serializable;
import java.util.List;

public class Celular implements Serializable {
    private int id;
    private String num;
    private boolean isFixo;
    private int idPessoa;

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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public boolean isFixo() {
        return isFixo;
    }

    public void setIsFixo(boolean fixo) {
        isFixo = fixo;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }
}
