package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Endereco;

import java.sql.Connection;
import java.util.List;

public class EnderecoDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Endereco end) {
        return false;
    }

    public boolean alterar(Endereco end) {
        return false;
    }

    public boolean remover(Endereco end) {
        return false;
    }

    public Endereco buscar(Endereco end) {
        return null;
    }

    public List<Endereco> listar() {
        return null;
    }
}
