package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Produto;

import java.sql.Connection;
import java.util.List;

public class ProdutoDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Produto prod) {
        return false;
    }

    public boolean alterar(Produto prod) {
        return false;
    }

    public boolean remover(Produto prod) {
        return false;
    }

    public Produto buscar(Produto prod) {
        return null;
    }

    public List<Produto> listar() {
        return null;
    }
}
