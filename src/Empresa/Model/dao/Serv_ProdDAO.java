package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.Session;
import Empresa.Model.domain.Produto;
import Empresa.Model.domain.ServicoPrestado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Serv_ProdDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Serv_ProdDAO servprod) {
        return false;
    }

    public boolean alterar(Serv_ProdDAO servprod) {
        return false;
    }

    public boolean remover(Serv_ProdDAO servprod) {
        return false;
    }

    public List<Produto> buscarPorServicoPrestado(ServicoPrestado servprod) throws SQLException {
        String sql = "SELECT * FROM serv_prod WHERE idEmp="+ Session.get().getId()+" AND idCliente=? AND data = ? AND horaInicio= ?";
        List<Produto> retorno = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, servprod.getCliente().getId());
        stmt.setDate(2, (Date) servprod.getData());
        stmt.setTime(3, servprod.getHrInicial());
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            Produto prodRetorno = new Produto();
            prodRetorno.setId(resultado.getInt("idProd"));

            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtoDAO.setConnection(connection);
            Produto prodBD = produtoDAO.buscar(prodRetorno);

            retorno.add(prodBD);
        }
        return retorno;

    }

    public List<Serv_ProdDAO> listar() {
        return null;
    }
}
