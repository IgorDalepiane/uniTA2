package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.Session;
import Empresa.Model.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicoDAO implements InterfaceDAO {
    private Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Servico serv) throws SQLException {
        String sql = "INSERT INTO servico (nome, descricao, preco, idEmp) VALUES(?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, serv.getNome());
        stmt.setString(2, serv.getDescricao());
        stmt.setFloat(3, serv.getPreco());
        stmt.setInt(4, Session.get().getId());
        return stmt.execute();
    }

    public boolean alterar(Servico serv) throws SQLException {
        String sql = "UPDATE servico SET nome=?, descricao=?, preco=? WHERE id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, serv.getNome());
        stmt.setString(2, serv.getDescricao());
        stmt.setFloat(3, serv.getPreco());
        stmt.setInt(4, serv.getId());
        return stmt.execute();
    }

    public boolean remover(Servico serv) throws SQLException {
        String sql = "DELETE FROM servico WHERE id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, serv.getId());

        //remove aptidao
        AptidaoDAO aptidaoDAO = new AptidaoDAO();
        aptidaoDAO.setConnection(connection);
        Apto apto = new Apto();
        apto.setServico(serv);

        return stmt.execute() && aptidaoDAO.remover(apto);
    }

    public Servico buscar(Servico serv) throws SQLException {
        String sql = "SELECT * FROM servico WHERE id=?";
        Servico retorno = new Servico();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, serv.getId());
        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            Servico servico = new Servico();
            List<Funcionario> funcionarios = new ArrayList<>();
            servico.setId(resultado.getInt("id"));
            servico.setNome(resultado.getString("nome"));
            servico.setDescricao(resultado.getString("descricao"));
            servico.setPreco(resultado.getFloat("preco"));

            AptidaoDAO aptidaoDAO = new AptidaoDAO();
            aptidaoDAO.setConnection(connection);

            servico.setAptos(aptidaoDAO.buscarPorServico(servico));

            retorno = servico;
        }
        return retorno;
    }

    /**
     * Busca o último registro inserido no bd através do método inserir()
     *
     * @return o registro
     */
    public Servico buscarUltimo() throws SQLException {
        String sql = "SELECT max(id) FROM servico";
        Servico retorno = new Servico();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            retorno.setId(resultado.getInt("max(id)"));
            return buscar(retorno);
        }
        return retorno;
    }

    public List<Servico> listar() throws SQLException {
        String sql = "SELECT * FROM servico WHERE servico.idEmp=" + Session.get().getId();
        List<Servico> retorno = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            Servico servico = new Servico();
            servico.setId(resultado.getInt("id"));
            retorno.add(buscar(servico));
        }
        return retorno;
    }
}