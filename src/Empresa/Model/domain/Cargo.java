package Empresa.Model.domain;

import java.io.Serializable;

public class Cargo implements Serializable {
    private int id;
    private String cargo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCargoText() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
