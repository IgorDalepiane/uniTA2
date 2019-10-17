package Empresa.Pessoa;

import Empresa.Contato.Contato;
import Empresa.Contato.ContatoController;
import Empresa.Endereco.Endereco;
import Empresa.Endereco.EnderecoController;

public abstract class Pessoa {
    private String nome;
    private String CPF;
    private String RG;
    private EnderecoController endController;
    private ContatoController contatoController;

    public Pessoa(String nome, String CPF, String RG, Endereco endereco, Contato contato) {
        this.nome = nome;
        this.CPF = CPF;
        this.RG = RG;
        this.endController = new EnderecoController(endereco);
        this.contatoController = new ContatoController(contato);
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
}
