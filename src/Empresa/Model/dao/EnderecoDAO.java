package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Endereco;
import Empresa.Model.domain.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnderecoDAO implements InterfaceDAO {
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
     * Insere os registros no banco de dados
     * @param end o Endereço a ser registrado
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean inserir(Endereco end) {
        String sql = "INSERT INTO endereco(logradouro, numero, complemento, bairro, cidade, estado, CEP) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, end.getLogradouro());
            stmt.setInt(2, end.getNumero());
            stmt.setString(3, end.getComplemento());
            stmt.setString(4, end.getBairro());
            stmt.setString(5, end.getCidade());
            stmt.setString(6, end.getEstado());
            stmt.setString(7, end.getCEP());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Altera algum registro do banco de dados
     * @param end o objeto a ser alterado
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean alterar(Endereco end) {
        String sql = "UPDATE endereco SET logradouro=?, numero=?, complemento=?, bairro=?,cidade=?,estado=?,CEP=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, end.getLogradouro());
            stmt.setInt(2, end.getNumero());
            stmt.setString(3, end.getComplemento());
            stmt.setString(4, end.getBairro());
            stmt.setString(5, end.getCidade());
            stmt.setString(6, end.getEstado());
            stmt.setString(7, end.getCEP());
            stmt.setInt(8, end.getId());

            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Remove algum registro do banco de dados
     * @param end o registro a ser removido
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean remover(Endereco end) {
        String sql = "DELETE FROM endereco WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, end.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Busca somente um registro no banco de dados
     * @param end o objeto (incompleto) a ser buscado
     * @return o objeto com todos os campos do banco de dados
     */
    public Endereco buscar(Endereco end) {
        String sql = "SELECT * FROM endereco WHERE id=?";
        Endereco retorno = new Endereco();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, end.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Endereco endereco = new Endereco();
                endereco.setId(resultado.getInt("id"));
                endereco.setLogradouro(resultado.getString("logradouro"));
                endereco.setNumero(resultado.getInt("numero"));
                if (!(resultado.getString("complemento") == null)){
                    endereco.setComplemento(resultado.getString("complemento"));
                }else{
                    endereco.setComplemento("N/I");
                }
                endereco.setBairro(resultado.getString("bairro"));
                endereco.setCidade(resultado.getString("cidade"));
                endereco.setEstado(resultado.getString("estado"));
                if (!(resultado.getString("CEP") == null)){
                    endereco.setCEP(resultado.getString("CEP"));
                }else{
                    endereco.setCEP("N/I");
                }

                retorno = endereco;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

//    public List<Endereco> listar() {
//        return null;
//    }

    /**
     * Busca o id do último registro adicionado ao banco de dados pelo método inserir()
     * @return o registro
     */
    public Endereco buscarUltimoEnd() {
        String sql = "SELECT max(id) FROM endereco";
        Endereco retorno = new Endereco();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                retorno.setId(resultado.getInt("max(id)"));
                return buscar(retorno);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
