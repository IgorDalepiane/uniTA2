package Empresa.Pessoa.Cliente;

import Empresa.Contato.Contato;
import Empresa.Endereco.Endereco;
import Empresa.Pessoa.Pessoa;

public class Cliente extends Pessoa {
    public Cliente(String nome, String CPF, String RG, Endereco endereco, Contato contato) {
        super(nome, CPF, RG, endereco, contato);
    }
}
