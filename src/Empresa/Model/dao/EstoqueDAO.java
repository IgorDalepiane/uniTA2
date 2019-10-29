package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.Session;
import Empresa.Model.domain.Empresa;
import Empresa.Model.domain.Estoque;
import Empresa.Model.domain.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EstoqueDAO implements InterfaceDAO {
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
     * @param est o registro a ser inserido
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean inserir(Estoque est) throws SQLException {
        String sql = "insert into estoque (idEmp, idProd, quantidade) values (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, Session.get().getId());
        stmt.setInt(2, est.getProd().getId());
        stmt.setInt(3, est.getQuant());

        return stmt.execute();
    }

    /**
     * Altera um registro no banco de dados
     *
     * @param est o registro a ser alterado
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean alterar(Estoque est) throws SQLException {
        String sql = "update estoque set quantidade=? where idProd = " + est.getProd().getId();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, est.getQuant());

        return stmt.execute();
    }

    /**
     * Remove um registro do banco de dados
     *
     * @param est o registro a ser removido
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean remover(Estoque est) throws SQLException {
        String sql = "DELETE FROM estoque WHERE idEmp=? and idProd=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, Session.get().getId());
        stmt.setInt(2, est.getProdId());

        /*
        Como a tabela Produto e Estoque estão ligadas,
        ao remover da Estoque, é necessário remover também da Produto
         */
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.setConnection(connection);

        return stmt.execute() && produtoDAO.remover(est.getProd());
    }

    /**
     * Busca um registro no banco de dados
     *
     * @param est o registro (incompleto) a ser buscado
     * @return o registro com as informações armazenadas no banco
     */
    public Estoque buscar(Estoque est) throws SQLException {
        String sql = "select * from estoque where idProd = ? and idEmp = ?";
        Estoque retorno = new Estoque();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, est.getProd().getId());
        stmt.setInt(2, est.getEmpresa().getId());
        ResultSet resultado = stmt.executeQuery();

        //estoque precisa da empresa e do produto

        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.setConnection(connection);
        Produto prod = produtoDAO.buscar(est.getProd());

        retorno.setEmpresa(Session.get());
        retorno.setProd(prod);
        retorno.setQuant(resultado.getInt("quantidade"));

        return retorno;
    }

    /**
     * Lista os registros da tabela do banco de dados
     *
     * @return Uma List com todos os registros
     */
    public List<Estoque> listar() throws SQLException {
        String sql = "select * from estoque where idEmp = " + Session.get().getId();
        List<Estoque> retorno = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            //produtoDAO
            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtoDAO.setConnection(connection);
            Produto prod = new Produto();
            prod.setId(resultado.getInt("idProd"));

            Estoque est = new Estoque();

            est.setProd(produtoDAO.buscar(prod));
            est.setEmpresa(Session.get());
            est.setQuant(resultado.getInt("quantidade"));

            retorno.add(est);
        }
        return retorno;
    }
}