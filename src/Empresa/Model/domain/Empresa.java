package Empresa.Model.domain;

import java.io.Serializable;
import java.util.List;

public class Empresa implements Serializable {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private List<Servico> servicos;
    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;
    private List<ServicoPrestado> servicosPrestados;
    private List<Produto> estoque;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Empresa(int id, String nome,String email,String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Empresa() {
    }

    public List<Produto> getEstoque() {
        return estoque;
    }

    public void setEstoque(List<Produto> estoque) {
        this.estoque = estoque;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<ServicoPrestado> getServicosPrestados() {
        return servicosPrestados;
    }

    public void setServicosPrestados(List<ServicoPrestado> servicosPrestados) {
        this.servicosPrestados = servicosPrestados;
    }
}
