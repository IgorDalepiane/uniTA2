package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Funcionario;

import java.sql.Connection;
import java.util.List;

public class FuncionarioDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Funcionario func) {
        return false;
    }

    public boolean alterar(Funcionario func) {
        return false;
    }

    public boolean remover(Funcionario func) {
        return false;
    }

    public Funcionario buscar(Funcionario func) {
        return null;
    }

    public List<Funcionario> listar() {
        return null;
    }
}
