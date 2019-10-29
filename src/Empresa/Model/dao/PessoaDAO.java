package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Celular;
import Empresa.Model.domain.Endereco;
import Empresa.Model.domain.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PessoaDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Pessoa pess) {
        String sql = "INSERT INTO pessoa(nome, email, RG, CPF,idEnd) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, pess.getNome());
            stmt.setString(2, pess.getEmail());
            stmt.setString(3, pess.getRG());
            stmt.setString(4, pess.getCPF());
            stmt.setInt(5, pess.getEndereco().getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Pessoa pess) {
        String sql = "UPDATE pessoa SET nome=?, email=?, RG=?, CPF=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, pess.getNome());
            stmt.setString(2, pess.getEmail());
            stmt.setString(3, pess.getRG());
            stmt.setString(4, pess.getCPF());
            stmt.setInt(5, pess.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Pessoa pess) {
        return false;
    }

    public Pessoa buscar(Pessoa pess) {
        String sql = "SELECT * FROM pessoa WHERE id=?";
        Pessoa retorno = new Pessoa();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, pess.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Pessoa pessoa = new Pessoa();
                Endereco endereco = new Endereco();
                List<Celular> celulares = new ArrayList();
                pessoa.setId(resultado.getInt("id"));
                pessoa.setRG(resultado.getString("RG"));
                pessoa.setCPF(resultado.getString("CPF"));
                pessoa.setNome(resultado.getString("nome"));
                pessoa.setEmail(resultado.getString("email"));

                endereco.setId(resultado.getInt("idEnd"));
                EnderecoDAO enderecoDAO = new EnderecoDAO();
                enderecoDAO.setConnection(connection);
                endereco = enderecoDAO.buscar(endereco);
                pessoa.setEndereco(endereco);

                CelularDAO celularDAO = new CelularDAO();
                celularDAO.setConnection(connection);
                celulares = celularDAO.listarPorPessoa(pessoa);
                pessoa.setCelulares(celulares);

                retorno = pessoa;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;


    }

    public List<Pessoa> listar() {
        return null;
    }

    public Pessoa buscarUltimaPess() {
        String sql = "SELECT max(id) FROM pessoa";
        Pessoa retorno = new Pessoa();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                retorno.setId(resultado.getInt("max(id)"));
                return buscar(retorno);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
