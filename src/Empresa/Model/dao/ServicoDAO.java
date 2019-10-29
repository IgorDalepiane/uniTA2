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

    public boolean inserir(Servico serv) {
        String sql = "INSERT INTO servico (nome, descricao, preco, idEmp) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, serv.getNome());
            stmt.setString(2, serv.getDescricao());
            stmt.setFloat(3, serv.getPreco());
            stmt.setInt(4, Session.get().getId());

            stmt.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Servico serv) {
        String sql = "UPDATE servico SET nome=?, descricao=?, preco=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, serv.getNome());
            stmt.setString(2, serv.getDescricao());
            stmt.setFloat(3, serv.getPreco());
            stmt.setInt(4, serv.getId());

            stmt.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean remover(Servico serv) {
        String sql = "DELETE FROM servico WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, serv.getId());

            //remove aptidao
            AptidaoDAO aptidaoDAO = new AptidaoDAO();
            aptidaoDAO.setConnection(connection);
            Apto apto = new Apto();
            apto.setServico(serv);

            aptidaoDAO.remover(apto);

            stmt.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Servico buscar(Servico serv) {
        String sql = "SELECT * FROM servico WHERE id=?";
        Servico retorno = new Servico();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, serv.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Servico servico = new Servico();
                List<Funcionario> funcionarios= new ArrayList<>();
                servico.setId(resultado.getInt("id"));
                servico.setNome(resultado.getString("nome"));
                servico.setDescricao(resultado.getString("descricao"));
                servico.setPreco(resultado.getFloat("preco"));

                AptidaoDAO aptidaoDAO = new AptidaoDAO();
                aptidaoDAO.setConnection(connection);

                servico.setAptos(aptidaoDAO.buscarPorServico(servico));

                retorno=servico;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;

    }

    public List<Servico> listar() {
        String sql = "SELECT * FROM servico WHERE servico.idEmp=" + Session.get().getId();
        List<Servico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Servico servico = new Servico();
                servico.setId(resultado.getInt("id"));
                retorno.add(buscar(servico));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}