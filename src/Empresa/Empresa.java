package Empresa;

import Empresa.Pessoa.Pessoa;
import Empresa.Pessoa.PessoaController;
import Empresa.Servicos.ServicoController;
import Empresa.Servicos.ServicoPrestadoController;

public class Empresa {
    private int id;
    private String nome;
    private String password;
    private ServicoController servicos;
    private PessoaController clientes;
    private PessoaController funcionarios;
    private ServicoPrestadoController servicosPrestados;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    // c/ id
    public Empresa(int id, String nome, ServicoController servicos, PessoaController clientes, PessoaController funcionarios, ServicoPrestadoController servicosPrestados) {
        this.id = id;
        this.nome = nome;
        this.servicos = servicos;
        this.clientes = clientes;
        this.funcionarios = funcionarios;
        this.servicosPrestados = servicosPrestados;
    }
    // s/ id
    public Empresa(String nome, ServicoController servicos, PessoaController clientes, PessoaController funcionarios, ServicoPrestadoController servicosPrestados) {
        this.nome = nome;
        this.servicos = servicos;
        this.clientes = clientes;
        this.funcionarios = funcionarios;
        this.servicosPrestados = servicosPrestados;
    }
}
