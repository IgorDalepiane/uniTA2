package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.Session;
import Empresa.Model.domain.Apto;
import Empresa.Model.domain.Funcionario;
import Empresa.Model.domain.Servico;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AptidaoDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Apto apto) throws MySQLIntegrityConstraintViolationException {

        String sql = "INSERT INTO aptidao(idFunc, idServ) VALUES(?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, apto.getFuncionario().getId());
            stmt.setInt(2, apto.getServico().getId());

            stmt.execute();
            return true;
        } catch (SQLException ex) {
            throw new MySQLIntegrityConstraintViolationException("Funcionário já está apto!");
        }
    }

    public boolean alterar(Apto apto) {
        return false;
    }

    public boolean remover(Apto apto) {
        String sql = "DELETE FROM aptidao WHERE idServ=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, apto.getServico().getId());

            stmt.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public Apto buscar(Apto apto) {
        return null;
    }

    public List<Apto> listar() {
        return null;
    }


    public List<Funcionario> buscarPorServico(Servico servico) {
        String sql = "SELECT * FROM aptidao WHERE idServ=?";
        List<Funcionario> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, servico.getId());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Funcionario funcRetorno = new Funcionario();
                funcRetorno.setId(resultado.getInt("idFunc"));

                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                funcionarioDAO.setConnection(connection);
                Funcionario funcBD = funcionarioDAO.buscar(funcRetorno);

                retorno.add(funcBD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;

    }
}
