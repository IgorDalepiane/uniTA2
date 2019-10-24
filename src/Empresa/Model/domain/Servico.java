package Empresa.Model.domain;

import java.io.Serializable;
import java.util.List;

public class Servico implements Serializable {
    private int id;
    private String nome;
    private String descricao;
    private Float preco;
    private Empresa empresa;
    private List<Funcionario> aptos;

    public Servico(int id, String nome, Float preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public List<Funcionario> getAptos() {
        return aptos;
    }

    public void setAptos(List<Funcionario> aptos) {
        this.aptos = aptos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Servico() {
    }
}
