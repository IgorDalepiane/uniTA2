package Empresa.Pessoa.Funcionario;

import Empresa.Contato.Contato;
import Empresa.Endereco.Endereco;
import Empresa.Pessoa.Pessoa;

public class Funcionario extends Pessoa {
    private double valorHora;
    private String cargo;

    public Funcionario(String nome, String CPF, String RG, Endereco endereco, Contato contato, double valorHora, String cargo) {
        super(nome, CPF, RG, endereco, contato);
        this.valorHora = valorHora;
        this.cargo = cargo;
    }
}
