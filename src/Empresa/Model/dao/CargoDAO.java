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
     * @param cargo o registro a ser inserido
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean inserir(Cargo cargo) throws SQLException {
        String sql = "INSERT INTO cargo (cargo) VALUES(?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cargo.getCargoText());

        return stmt.execute();
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

    /**
     * Busca um registro no banco de dados
     *
     * @param carg o registro (incompleto) a ser buscado
     * @return o registro com todas as informações armazenadas no banco
     */
    public Cargo buscar(Cargo carg) throws SQLException {
        String sql = "SELECT * FROM cargo WHERE id=?";
        Cargo retorno = new Cargo();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, carg.getId());
        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            Cargo cargo = new Cargo();
            cargo.setId(resultado.getInt("id"));
            cargo.setCargo(resultado.getString("cargo"));
            retorno = cargo;
        }
        return retorno;
    }

    /**
     * Busca um registro pelo nome no banco de dados
     *
     * @param carg o registro incompleto a ser buscado
     * @return o registro completo retornado pelo banco de dados
     */
    public Cargo buscarPeloNome(String carg) throws SQLException {
        String sql = "SELECT * FROM cargo WHERE cargo LIKE ?";
        Cargo retorno = null;
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, carg);
        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            Cargo cargo = new Cargo();
            cargo.setId(resultado.getInt("id"));
            cargo.setCargo(resultado.getString("cargo"));
            retorno = cargo;
        }
        return retorno;
    }

    /**
     * Busca o último registro inserido no banco pelo método inserir()
     *
     * @return o registro
     */
    public Cargo buscarUltimoCargo() throws SQLException {
        String sql = "SELECT max(id) FROM cargo";
        Cargo retorno = new Cargo();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            retorno.setId(resultado.getInt("max(id)"));
            return buscar(retorno);
        }
        return null;
    }

//    public List<Celular> listar() {
//        return null;
//    }
//    public List<Celular> listarPorPessoa(Pessoa pessoa) {
//
//    }
}
