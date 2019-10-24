package Empresa.Model.domain;

import java.io.Serializable;

public class Apto implements Serializable {
    private Servico servico;
    private Funcionario funcionario;

    public Apto(Servico servico, Funcionario funcionario) {
        this.servico = servico;
        this.funcionario = funcionario;
    }
    public Apto() {
    }
    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }


}
