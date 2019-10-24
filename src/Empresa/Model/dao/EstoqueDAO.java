package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Estoque;

import java.sql.Connection;
import java.util.List;

public class EstoqueDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Estoque est) {
        return false;
    }

    public boolean alterar(Estoque est) {
        return false;
    }

    public boolean remover(Estoque est) {
        return false;
    }

    public Estoque buscar(Estoque est) {
        return null;
    }

    public List<Estoque> listar() {
        return null;
    }
}