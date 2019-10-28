package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.Session;
import Empresa.Model.domain.Funcionario;
import Empresa.Model.domain.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String sql = "SELECT * FROM funcionario INNER JOIN cargo WHERE funcionario.idCargo=cargo.id AND funcionario.idEmp="+Session.get().getId();
        List<Funcionario> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Pessoa pessoa;
                Funcionario funcionario = new Funcionario();
                //parte do funcionario
                funcionario.setId(resultado.getInt("idPessoa"));
                funcionario.setValorHora(resultado.getFloat("valorHora"));
                funcionario.setCargo(resultado.getString("cargo"));

                //parte da pessoa
                PessoaDAO pessoaDAO = new PessoaDAO();
                pessoaDAO.setConnection(connection);
                pessoa = pessoaDAO.buscar(funcionario);

                //seta os dados de pessoa
                funcionario.setNome(pessoa.getNome());
                funcionario.setCPF(pessoa.getCPF());
                funcionario.setEmail(pessoa.getEmail());
                funcionario.setRG(pessoa.getRG());
                funcionario.setEndereco(pessoa.getEndereco());
                funcionario.setCelulares(pessoa.getCelulares());
                retorno.add(funcionario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
