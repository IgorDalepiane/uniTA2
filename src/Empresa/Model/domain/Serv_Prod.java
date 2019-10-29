package Empresa.Model.domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Serv_Prod implements Serializable {
    private Produto prod;
    private Empresa emp;
    private Cliente cli;
    private String data;
    private Time hrInicio;
    private int qnt;

    public Serv_Prod(Produto prod, Empresa emp, Cliente cli, String data, int qnt) {
        this.prod = prod;
        this.emp = emp;
        this.cli = cli;
        this.data = data;
        this.qnt = qnt;
    }

    public Time getHrInicio() {
        return hrInicio;
    }

    public void setHrInicio(Time hrInicio) {
        this.hrInicio = hrInicio;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }
}
