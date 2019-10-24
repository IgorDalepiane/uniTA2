package Empresa.Model.domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class ServicoPrestado implements Serializable {
    private int id;
    private Servico servico;
    private Funcionario func;
    private Empresa empresa;
    private Cliente cliente;
    private Date data;
    private Time hrInicial;
    private Time hrFinal;
    private List<Produto> prodUtiliz;

    public ServicoPrestado(int id, Servico servico, Funcionario func, Empresa empresa, Cliente cliente, Date data, Time hrInicial, Time hrFinal) {
        this.id = id;
        this.servico = servico;
        this.func = func;
        this.empresa = empresa;
        this.cliente = cliente;
        this.data = data;
        this.hrInicial = hrInicial;
        this.hrFinal = hrFinal;
    }

    public ServicoPrestado() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Funcionario getFunc() {
        return func;
    }

    public void setFunc(Funcionario func) {
        this.func = func;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHrInicial() {
        return hrInicial;
    }

    public void setHrInicial(Time hrInicial) {
        this.hrInicial = hrInicial;
    }

    public Time getHrFinal() {
        return hrFinal;
    }

    public void setHrFinal(Time hrFinal) {
        this.hrFinal = hrFinal;
    }

    public List<Produto> getProdUtiliz() {
        return prodUtiliz;
    }

    public void setProdUtiliz(List<Produto> prodUtiliz) {
        this.prodUtiliz = prodUtiliz;
    }
}
