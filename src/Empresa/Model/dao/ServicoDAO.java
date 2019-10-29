package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.Session;
import Empresa.Model.domain.Cargo;
import Empresa.Model.domain.Funcionario;
import Empresa.Model.domain.Pessoa;
import Empresa.Model.domain.Servico;

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

    public boolean inserir(ServicoDAO serv) {
        return false;
    }

    public boolean alterar(ServicoDAO serv) {
        return false;
    }

    public boolean remover(ServicoDAO serv) {
        return false;
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