package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Cargo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CargoDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Cargo cargo) {
        String sql = "INSERT INTO cargo(cargo) VALUES(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cargo.getCargoText());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
//
//    public boolean alterar(Celular celular) {
//        return false;
//    }
//
//    public boolean remover(Celular celular) {
//        return false;
//    }
//
    public Cargo buscar(Cargo carg) {
        String sql = "SELECT * FROM cargo WHERE id=?";
        Cargo retorno = new Cargo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, carg.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Cargo cargo = new Cargo();
                cargo.setId(resultado.getInt("id"));
                cargo.setCargo(resultado.getString("cargo"));

                retorno = cargo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    public Cargo buscarPeloNome(String carg) {
        String sql = "SELECT * FROM cargo WHERE cargo LIKE ?";
        Cargo retorno = null;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, carg);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Cargo cargo = new Cargo();
                cargo.setId(resultado.getInt("id"));
                cargo.setCargo(resultado.getString("cargo"));

                retorno = cargo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    public Cargo buscarUltimoCargo() {
        String sql = "SELECT max(id) FROM cargo";
        Cargo retorno = new Cargo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                retorno.setId(resultado.getInt("max(id)"));
                return buscar(retorno);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

//    public List<Celular> listar() {
//        return null;
//    }
//    public List<Celular> listarPorPessoa(Pessoa pessoa) {
//
//    }
}
