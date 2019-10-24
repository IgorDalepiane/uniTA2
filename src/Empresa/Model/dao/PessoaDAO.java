package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Pessoa;

import java.sql.Connection;
import java.util.List;

public class PessoaDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Pessoa pess) {
        return false;
    }

    public boolean alterar(Pessoa pess) {
        return false;
    }

    public boolean remover(Pessoa pess) {
        return false;
    }

    public Pessoa buscar(Pessoa pess) {
        return null;
    }

    public List<Pessoa> listar() {
        return null;
    }
}
