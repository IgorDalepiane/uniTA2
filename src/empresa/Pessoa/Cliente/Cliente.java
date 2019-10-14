package empresa.Pessoa.Cliente;

import empresa.Contato.Contato;
import empresa.Endereco.Endereco;
import empresa.Pessoa.Pessoa;

public class Cliente extends Pessoa {
    public Cliente(String nome, String CPF, String RG, Endereco endereco, Contato contato) {
        super(nome, CPF, RG, endereco, contato);
    }
}
