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

    /**
     * Construtor para instâncias onde os campos não estão todos disponíveis
     * ao mesmo tempo, utilizando os métodos sets para manipular o objeto
     */
    public Funcionario() {
    }

    /**
     * Retorna o field
     * @return o field
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Seta o field
     * @param empresa o field
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * Retorna o field
     * @return o field
     */
    public int getId() {
        return id;
    }

    /**
     * Seta o field
     * @param id o field
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o field
     * @return o field
     */
    public double getValorHora() {
        return valorHora;
    }

    /**
     * Seta o field
     * @param valorHora o field
     */
    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

    /**
     * Retorna o field
     * @return o field
     */
    public Cargo getCargo() {
        return cargo;
    }

    /**
     * Seta o field
     * @param cargo o field
     */
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
