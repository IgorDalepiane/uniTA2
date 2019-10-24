package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;

import java.sql.Connection;
import java.util.List;

public class Serv_ProdDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Serv_ProdDAO servprod) {
        return false;
    }

    public boolean alterar(Serv_ProdDAO servprod) {
        return false;
    }

    public boolean remover(Serv_ProdDAO servprod) {
        return false;
    }

    public Serv_ProdDAO buscar(Serv_ProdDAO servprod) {
        return null;
    }

    public List<Serv_ProdDAO> listar() {
        return null;
    }
}
