package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Cliente;

import java.sql.Connection;
import java.util.List;

public class ClienteDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Cliente cliente) {
        return false;
    }

    public boolean alterar(Cliente cliente) {
        return false;
    }

    public boolean remover(Cliente cliente) {
        return false;
    }

    public Cliente buscar(Cliente cliente) {
        return null;
    }

    public List<Cliente> listar() {
        return null;
    }
}
