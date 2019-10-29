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
    private Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

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
     * Remove o produto da tabela Estoque e da tabela Produto
     *
     * @param es
     * @return
     */
    public boolean remover(Estoque es) {
        String sql1 = "delete from estoque where idProd = " + es.getProd().getId();
        String sql2 = "delete from produto where id = " + es.getProd().getId();
        Estoque retorno;
        try {
            Statement stmt = connection.createStatement();
            stmt.addBatch(sql1);
            stmt.addBatch(sql2);

            stmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca um produto no banco de dados
     *
     * @param prod
     * @return
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
