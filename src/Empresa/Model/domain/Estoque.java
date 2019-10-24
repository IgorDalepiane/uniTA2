package Empresa.Model.domain;

import java.io.Serializable;

public class Estoque implements Serializable {
    private Empresa empresa;
    private Produto prod;
    private int quant;

    public Estoque(Empresa empresa, Produto prod, int quant) {
        this.empresa = empresa;
        this.prod = prod;
        this.quant = quant;
    }

    public Estoque() {
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Produto getProd() {
        return prod;
    }

    public void setProd(Produto prod) {
        this.prod = prod;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }
}
