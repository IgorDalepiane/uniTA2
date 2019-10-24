package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;

import java.sql.Connection;
import java.util.List;

public class ServicoDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ServicoDAO serv) {
        return false;
    }

    public boolean alterar(ServicoDAO serv) {
        return false;
    }

    public boolean remover(ServicoDAO serv) {
        return false;
    }

    public ServicoDAO buscar(ServicoDAO serv) {
        return null;
    }

    public List<ServicoDAO> listar() {
        return null;
    }
}