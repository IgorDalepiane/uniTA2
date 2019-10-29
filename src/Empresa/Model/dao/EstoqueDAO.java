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
    private Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Estoque est) {
        String sql = "insert into estoque (idEmp, idProd, quantidade) values (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, Session.get().getId());
            stmt.setInt(2, est.getProd().getId());
            stmt.setInt(3, est.getQuant());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean alterar(Estoque est) {
        String sql = "update estoque set quantidade=? where idProd = " + est.getProd().getId();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, est.getQuant());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean remover(Estoque est) {
        String sql = "DELETE FROM estoque WHERE idEmp=? and idProd=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, Session.get().getId());
            stmt.setInt(2, est.getProdId());
            stmt.execute();

            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtoDAO.setConnection(connection);

            produtoDAO.remover(est.getProd());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Estoque buscar(Estoque est) {
        String sql = "select * from estoque where idProd = ? and idEmp = ?";
        Estoque retorno = null;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, est.getProd().getId());
            stmt.setInt(2, est.getEmpresa().getId());
            ResultSet resultado = stmt.executeQuery();

            //estoque precisa da empresa e do produto
            EmpresaDAO empresaDAO = new EmpresaDAO();
            empresaDAO.setConnection(connection);
            Empresa emp = empresaDAO.buscar(est.getEmpresa());

            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtoDAO.setConnection(connection);
            Produto prod = produtoDAO.buscar(est.getProd());

            Estoque estBanco = new Estoque();
            estBanco.setEmpresa(emp);
            estBanco.setProd(prod);
            estBanco.setQuant(resultado.getInt("quantidade"));

            //estoque precisa de empresa e produto
            retorno = estBanco;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public List<Estoque> listar() {
        String sql = "select * from estoque where idEmp = " + Session.get().getId();
        List<Estoque> retorno = new ArrayList<>();
        try {
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
        } catch (SQLException e) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, "Erro ao listar estoque: ", e);
        }
        return retorno;
    }
}