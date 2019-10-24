package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Celular;

import java.sql.Connection;
import java.util.List;

public class CelularDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Celular celular) {
        return false;
    }

    public boolean alterar(Celular celular) {
        return false;
    }

    public boolean remover(Celular celular) {
        return false;
    }

    public Celular buscar(Celular celular) {
        return null;
    }

    public List<Celular> listar() {
        return null;
    }


}
