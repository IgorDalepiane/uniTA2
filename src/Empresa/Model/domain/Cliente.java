package Empresa.Model.domain;


import java.io.Serializable;

public class Cliente extends Pessoa implements Serializable {
    private int id;

    public Cliente(int id,String nome, String CPF, String RG) {
        super(id,nome, CPF, RG);
    }
    public Cliente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
