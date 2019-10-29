package Empresa.Model.dao;

import Empresa.Exception.CadastroException;
import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Endereco;
import Empresa.Model.domain.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoaDAO implements InterfaceDAO {
    //driver de conexão com o banco de dados
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
     * @param pess o objeto a ser inserido como registro
     * @return true se a operação foi concluída, false se não
     */
    public boolean inserir(Pessoa pess) throws SQLException, CadastroException {
        if (!existeCPF(pess)) {
            String sql = "INSERT INTO pessoa(nome, email, RG, CPF,idEnd,celular,residencial) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, pess.getNome());
            stmt.setString(2, pess.getEmail());
            stmt.setString(3, pess.getRG());
            stmt.setString(4, pess.getCPF());
            stmt.setInt(5, pess.getEndereco().getId());
            stmt.setString(6, pess.getCelular());
            stmt.setString(7, pess.getResidencial());
            stmt.execute();
            return true;
        }
        return false;
    }

    /**
     * Altera um registro no banco de dados
     *
     * @param pess o registro a ser alterado
     * @return true se a operação foi concluída, false se não
     */
    public boolean alterar(Pessoa pess) throws SQLException, CadastroException {
        if (!existeCPF(pess)) {
            String sql = "UPDATE pessoa SET nome=?, email=?, RG=?, CPF=?,celular=?,residencial=? WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, pess.getNome());
            stmt.setString(2, pess.getEmail());
            stmt.setString(3, pess.getRG());
            stmt.setString(4, pess.getCPF());
            stmt.setString(5, pess.getCelular());
            stmt.setString(6, pess.getResidencial());
            stmt.setInt(7, pess.getId());

            return stmt.execute();
        }
        return false;
    }

    /**
     * Remove um registro do banco de dados
     *
     * @param pess o objeto a ser removido do banco
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean remover(Pessoa pess) throws SQLException {
        String sql = "DELETE FROM pessoa WHERE id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, pess.getId());
        return stmt.execute();
    }

    /**
     * Busca um registro no banco de dados
     *
     * @param pess o registro (incompleto) a ser buscado no banco de dados
     * @return o registro completo, de acordo com o que está armazenado no bd
     */
    public Pessoa buscar(Pessoa pess) throws SQLException {
        String sql = "SELECT * FROM pessoa WHERE id=?";
        Pessoa retorno = new Pessoa();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, pess.getId());
        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            Pessoa pessoa = new Pessoa();
            Endereco endereco = new Endereco();
            pessoa.setId(resultado.getInt("id"));
            pessoa.setRG(resultado.getString("RG"));
            pessoa.setCPF(resultado.getString("CPF"));
            pessoa.setNome(resultado.getString("nome"));
            pessoa.setEmail(resultado.getString("email"));
            pessoa.setCelular(resultado.getString("celular"));
            pessoa.setResidencial(resultado.getString("residencial"));

            endereco.setId(resultado.getInt("idEnd"));
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            enderecoDAO.setConnection(connection);
            endereco = enderecoDAO.buscar(endereco);
            pessoa.setEndereco(endereco);

            retorno = pessoa;
        }
        return retorno;
    }

    /**
     * Busca um registro no banco de dados
     *
     * @param pess o registro (incompleto) a ser buscado no banco de dados
     * @return o registro completo, de acordo com o que está armazenado no bd
     */
    public boolean existeCPF(Pessoa pess) throws CadastroException, SQLException {
        String sql = "SELECT * FROM pessoa WHERE CPF=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, pess.getCPF());
        ResultSet resultSet = stmt.executeQuery();
        int i = 0;
        while (resultSet.next()) {
            i++;
            if (i > 1) {
                throw new CadastroException("Este CPF já está cadastrado");
            }
        }
        return false;
    }
//    public List<Pessoa> listar() {
//        return null;
//    }

    /**
     * Busca o último registro inserido no bd através do método inserir()
     *
     * @return o registro
     */
    public Pessoa buscarUltimaPess() throws SQLException {
        String sql = "SELECT max(id) FROM pessoa";
        Pessoa retorno = new Pessoa();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            retorno.setId(resultado.getInt("max(id)"));
            return buscar(retorno);
        }
        return retorno;
    }
}
