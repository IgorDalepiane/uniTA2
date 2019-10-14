package empresa.Pessoa.Funcionario;

import empresa.Contato.Contato;
import empresa.Endereco.Endereco;
import empresa.Pessoa.Pessoa;

public class Funcionario extends Pessoa {
    private double valorHora;
    private String cargo;

    public Funcionario(String nome, String CPF, String RG, Endereco endereco, Contato contato, double valorHora, String cargo) {
        super(nome, CPF, RG, endereco, contato);
        this.valorHora = valorHora;
        this.cargo = cargo;
    }
}
