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

    public boolean inserir(Estoque es) {
//        String sql = "insert into produto values (NULL, 'Pão de queijo', 'muito bom', '8')";
//        try {
//            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ResultSet ids = stmt.getGeneratedKeys();
//            if(ids.next()) {
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        return false;
    }

    public boolean alterar(Produto prod) {
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
            Produto prodBanco = new Produto();
            prodBanco.setId(resultado.getInt("id"));
            prodBanco.setNome(resultado.getString("nome"));
            prodBanco.setDescricao(resultado.getString("descricao"));
            prodBanco.setPreco(resultado.getFloat("preco"));

            //estoque precisa de empresa e produto
            retorno = prodBanco;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }
}
