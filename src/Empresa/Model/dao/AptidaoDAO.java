package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Apto;
import Empresa.Model.domain.Funcionario;
import Empresa.Model.domain.Servico;

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

    public boolean inserir(Apto apto) {
        return false;
    }

    public boolean alterar(Apto apto) {
        return false;
    }

    public boolean remover(Apto apto) {
        return false;
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
