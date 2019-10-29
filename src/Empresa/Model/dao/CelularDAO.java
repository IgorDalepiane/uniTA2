package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Celular;
import Empresa.Model.domain.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CelularDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Celular celular) {
        String sql = "INSERT INTO celular(num, isFixo, idPessoa) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, celular.getNum());
            stmt.setBoolean(2, celular.isFixo());
            stmt.setInt(3, celular.getIdPessoa());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CelularDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean alterar(Celular celular) {
        String sql = "UPDATE celular SET num=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, celular.getNum());
            stmt.setInt(2, celular.getIdPessoa());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Celular celular) {
        return false;
    }

    public Celular buscar(Celular celular) {return null;}

    public List<Celular> listar() {
        return null;
    }
    public List<Celular> listarPorPessoa(Pessoa pessoa) {
        String sql = "SELECT * FROM celular WHERE idPessoa=?";
        List<Celular> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, pessoa.getId());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Celular celular = new Celular();
                celular.setId(resultado.getInt("id"));
                celular.setNum(resultado.getString("num"));
                celular.setIsFixo(resultado.getBoolean("isFixo"));
                celular.setIdPessoa(resultado.getInt("idPessoa"));

                retorno.add(celular);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CelularDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


}
