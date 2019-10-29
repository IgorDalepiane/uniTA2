package Empresa.Model.dao;

import Empresa.Exception.CadastroException;
import Empresa.Exception.LoginInvalidoException;
import Empresa.Model.InterfaceDAO;
import Empresa.Model.Session;
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
    //driver de conexão com o banco
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
     * @param empresa o registro a ser inserido no banco de dados
     * @return true se a operação foi concluída com sucesso, false se não
     * @throws CadastroException se o email já existe no sistema
     */
    public boolean inserir(Empresa empresa) throws CadastroException, SQLException {
        if (buscar(empresa).getEmail() == null) {
            String sql = "INSERT INTO empresa(nome, email, senha) VALUES(?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getEmail());
            stmt.setString(3, empresa.getSenha());
            return stmt.execute();
        } else {
            throw new CadastroException("Email ja existente.");
        }
    }

//    public boolean alterar(Empresa empresa) {
//        return false;
//    }
//
//    public boolean remover(Empresa empresa) {
//        return false;
//    }

    /**
     * Busca um registro no banco de dados
     *
     * @param empresa o registro (incompleto) a ser buscado
     * @return o registro completo com as informações do banco
     */
    public Empresa buscar(Empresa empresa) throws SQLException {
        String sql = "SELECT * FROM empresa WHERE email=?";
        Empresa retorno = new Empresa();
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
        return retorno;
    }


//    public List<Empresa> listar() {
//        String sql = "SELECT * FROM empresa";
//        List<Empresa> retorno = new ArrayList<>();
//        try {
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            ResultSet resultado = stmt.executeQuery();
//            while (resultado.next()) {
//                Empresa empresa = new Empresa();
//                empresa.setId(resultado.getInt("id"));
//                empresa.setNome(resultado.getString("nome"));
//                //cliente.setCpf(resultado.getString("cpf"));
//                //cliente.setTelefone(resultado.getString("telefone"));
//                retorno.add(empresa);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return retorno;
//    }

    /**
     * Acessa o sistema através de email + senha
     *
     * @param empresa a empresa efetuando o login
     * @return true se a operação foi concluída com sucesso, false se não
     * @throws LoginInvalidoException se a combinação email/senha não existe no banco de dados
     */
    public boolean logar(Empresa empresa) throws LoginInvalidoException, SQLException {
        Empresa empBD = buscar(empresa);
        System.out.println(empBD.getSenha());
        System.out.println(empresa.getSenha());
        if (empBD.getEmail() != null) {
            if (empresa.getSenha().equals(empBD.getSenha())) {
                Session.set(empBD);
                return true;
            } else {
                throw new LoginInvalidoException("Senha inválida.");
            }
        } else {
            throw new LoginInvalidoException("Email inexistente.");
        }
    }
}
