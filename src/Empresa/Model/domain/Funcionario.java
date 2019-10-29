package Empresa.Model.domain;

import java.io.Serializable;

public class Funcionario extends Pessoa implements Serializable {
    private int id;
    private double valorHora;
    private Cargo cargo;
    private Empresa empresa;

    public Funcionario(int id, String nome, String CPF, String RG, double valorHora, Cargo cargo) {
        super(id,nome, CPF, RG);
        this.valorHora = valorHora;
        this.cargo = cargo;
    }

    public Funcionario() {
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
