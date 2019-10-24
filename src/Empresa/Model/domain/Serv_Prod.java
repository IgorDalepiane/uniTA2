package Empresa.Model.domain;

import java.io.Serializable;
import java.util.Date;

public class Serv_Prod implements Serializable {
    private Produto prod;
    private Empresa emp;
    private Cliente cli;
    private Date data;
    private int qnt;

    public Serv_Prod(Produto prod, Empresa emp, Cliente cli, Date data, int qnt) {
        this.prod = prod;
        this.emp = emp;
        this.cli = cli;
        this.data = data;
        this.qnt = qnt;
    }

    public Serv_Prod() {
    }

    public Produto getProd() {
        return prod;
    }

    public void setProd(Produto prod) {
        this.prod = prod;
    }

    public Empresa getEmp() {
        return emp;
    }

    public void setEmp(Empresa emp) {
        this.emp = emp;
    }

    public Cliente getCli() {
        return cli;
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }
}
