package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Apto;

import java.sql.Connection;
import java.util.List;

public class AptidaoDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Apto apto) {
        return false;
    }

    public boolean alterar(Apto apto) {
        return false;
    }

    public boolean remover(Apto apto) {
        return false;
    }

    public Apto buscar(Apto apto) {
        return null;
    }

    public List<Apto> listar() {
        return null;
    }


}
