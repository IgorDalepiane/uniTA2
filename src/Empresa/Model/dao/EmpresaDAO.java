package Empresa.Model.dao;

import Empresa.Exception.CadastroException;
import Empresa.Exception.LoginInvalidoException;
import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmpresaDAO implements InterfaceDAO {
    private Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Empresa empresa) throws CadastroException {
        if (buscar(empresa).getEmail()==null) {
            String sql = "INSERT INTO empresa(nome, email, senha) VALUES(?,?,?)";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, empresa.getNome());
                stmt.setString(2, empresa.getEmail());
                stmt.setString(3, empresa.getSenha());
                stmt.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }else{
            throw new CadastroException("Email ja existente.");
        }
    }

    public boolean alterar(Empresa empresa) {
        return false;
    }

    public boolean remover(Empresa empresa) {
        return false;
    }

    public Empresa buscar(Empresa empresa) {
        String sql = "SELECT * FROM empresa WHERE email=?";
        Empresa retorno = new Empresa();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, empresa.getEmail());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Empresa empRetorno = new Empresa();
                empRetorno.setId(resultado.getInt("id"));
                empRetorno.setNome(resultado.getString("nome"));
                empRetorno.setEmail(resultado.getString("email"));
                empRetorno.setSenha(resultado.getString("senha"));
                retorno = empRetorno;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;

    }

    public List<Empresa> listar() {
        String sql = "SELECT * FROM empresa";
        List<Empresa> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Empresa empresa = new Empresa();
                empresa.setId(resultado.getInt("id"));
                empresa.setNome(resultado.getString("nome"));
                //cliente.setCpf(resultado.getString("cpf"));
                //cliente.setTelefone(resultado.getString("telefone"));
                retorno.add(empresa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public boolean login(Empresa empresa) throws LoginInvalidoException {
        Empresa empBD = buscar(empresa);
        System.out.println(empBD.getSenha());
        System.out.println(empresa.getSenha());
        if (empBD.getEmail() != null) {
            if (empresa.getSenha().equals(empBD.getSenha())) {
                return true;
            } else {
                throw new LoginInvalidoException("Senha inválida.");
            }
        } else {
            throw new LoginInvalidoException("Email inexistente.");
        }
    }


}
