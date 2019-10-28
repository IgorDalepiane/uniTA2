package Empresa.Model.domain;

import java.io.Serializable;
import java.util.List;

public class Pessoa implements Serializable {
    private int id;
    private String nome;
    private String CPF;
    private String RG;
    private String email;
    private Endereco endereco;
    private List<Celular> celulares;

    public Pessoa(int id, String nome, String CPF, String RG) {
        this.nome = nome;
        this.CPF = CPF;
        this.RG = RG;
    }

    public Pessoa() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Celular> getCelulares() {
        return celulares;
    }

    public void setCelulares(List<Celular> celulares) {
        this.celulares = celulares;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
