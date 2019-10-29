package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.Session;
import Empresa.Model.domain.Empresa;
import Empresa.Model.domain.Estoque;
import Empresa.Model.domain.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoDAO implements InterfaceDAO {
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
     * @param prod o registro a ser inserido
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean inserir(Produto prod) {
        String sql = "insert into produto (nome, descricao, preco) values (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, prod.getNome());
            stmt.setString(2, prod.getDescricao());
            stmt.setFloat(3, prod.getPreco());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Altera um registro no banco de dados
     * @param prod o registro a ser alterado
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean alterar(Produto prod) {
        String sql = "update produto set nome=?, descricao=?, preco=? where id = " + prod.getId();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, prod.getNome());
            stmt.setString(2, prod.getDescricao());
            stmt.setFloat(3, prod.getPreco());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Remove o registro do banco de dados
     * @param prod o registro a ser removido
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean remover(Produto prod) {
        String sql = "DELETE FROM produto WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, prod.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Busca um registro no banco de dados
     * @param prod o registro (incompleto) a ser buscado
     * @return o registro com todos os campos do banco de dados
     */
    public Produto buscar(Produto prod) {
        String sql = "select * from produto where id = ?";
        Produto retorno = new Produto();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, prod.getId());
            ResultSet resultado = stmt.executeQuery();

            //instancia os resultados do banco em Java
            if(resultado.next()) {
                retorno.setId(resultado.getInt("id"));
                retorno.setNome(resultado.getString("nome"));
                retorno.setDescricao(resultado.getString("descricao"));
                retorno.setPreco(resultado.getFloat("preco"));
            }
            return retorno;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    /**
     * Busca o último registro inserido pelo método inserir() no banco de dados
     * @return o registro
     */
    public Produto buscarUltimo() {
        String sql = "select max(id), nome, descricao, preco from produto";
        Produto retorno = new Produto();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            //instancia os resultados do banco em Java
            Produto prodBanco = new Produto();
            if (resultado.next()) {
                prodBanco.setId(resultado.getInt("max(id)"));
                prodBanco.setNome(resultado.getString("nome"));
                prodBanco.setDescricao(resultado.getString("descricao"));
                prodBanco.setPreco(resultado.getFloat("preco"));
            }
            //estoque precisa de empresa e produto
            retorno = prodBanco;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }
}
