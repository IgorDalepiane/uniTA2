package Empresa.Model.dao;

import Empresa.Exception.CadastroException;
import Empresa.Model.InterfaceDAO;
import Empresa.Model.Session;
import Empresa.Model.domain.Cargo;
import Empresa.Model.domain.Cliente;
import Empresa.Model.domain.Funcionario;
import Empresa.Model.domain.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    /**
     * Insere um registro no banco de dados
     *
     * @param cli o objeto a ser inserido como registro
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean inserir(Cliente cli) throws SQLException, CadastroException {
        PessoaDAO pessoaDAO = new PessoaDAO();
        pessoaDAO.setConnection(connection);
        if (!pessoaDAO.existeCPF(cli)) {
            String sql = "INSERT INTO cliente (idPessoa, idEmp) VALUES(?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cli.getId());
            stmt.setInt(2, Session.get().getId());

            return stmt.execute();
        }
        return false;
    }

    /**
     * Altera um registro do banco de dados
     *
     * @param cli o registro a ser alterado
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean alterar(Cliente cli) throws SQLException, CadastroException {
        PessoaDAO pessoaDAO = new PessoaDAO();
        pessoaDAO.setConnection(connection);
        if (!pessoaDAO.existeCPF(cli)) {
            String sql = "UPDATE cliente SET idEmp=? WHERE idPessoa=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, Session.get().getId());
            stmt.setInt(2, cli.getId());

            return stmt.execute();
        }
        return false;
    }

    /**
     * Remove um registro do banco de dados
     *
     * @param cli o registro a ser removido
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean remover(Cliente cli) throws SQLException {
        String sql = "DELETE FROM cliente WHERE idPessoa=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, cli.getId());
        return stmt.execute();
    }

    /**
     * Busca um registro no banco de dados
     *
     * @param cliente o registro (incompleto) a ser buscado
     * @return o registro com todas as informações armazenadas no banco de dados
     */
    public Cliente buscar(Cliente cliente) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE idPessoa=?";
        Cliente retorno = new Cliente();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, cliente.getId());

        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            Pessoa pessoa;
            //parte da pessoa
            PessoaDAO pessoaDAO = new PessoaDAO();
            pessoaDAO.setConnection(connection);
            pessoa = pessoaDAO.buscar(cliente);

            //seta os dados de pessoa
            retorno.setId(pessoa.getId());
            retorno.setNome(pessoa.getNome());
            retorno.setCPF(pessoa.getCPF());
            retorno.setEmail(pessoa.getEmail());
            retorno.setRG(pessoa.getRG());
            retorno.setEndereco(pessoa.getEndereco());
            retorno.setCelular(pessoa.getCelular());
            retorno.setResidencial(pessoa.getResidencial());
        }
        return retorno;
    }

    /**
     * Lista todos os registros da tabela do banco de dados
     *
     * @return List com os registros
     */
    public List<Cliente> listar() throws SQLException {
        String sql = "SELECT * FROM cliente WHERE idEmp=" + Session.get().getId();
        List<Cliente> retorno = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            Cliente cliente = new Cliente();
            cliente.setId(resultado.getInt("idPessoa"));
            retorno.add(buscar(cliente));
        }
        return retorno;
    }
}
