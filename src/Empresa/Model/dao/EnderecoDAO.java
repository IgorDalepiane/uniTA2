package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.domain.Endereco;
import Empresa.Model.domain.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnderecoDAO implements InterfaceDAO {
    private Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Endereco end) {
        return false;
    }

    public boolean alterar(Endereco end) {
        return false;
    }

    public boolean remover(Endereco end) {
        return false;
    }

    public Endereco buscar(Endereco end) {
        String sql = "SELECT * FROM endereco WHERE id=?";
        Endereco retorno = new Endereco();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, end.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Endereco endereco = new Endereco();
                endereco.setId(resultado.getInt("id"));
                endereco.setLogradouro(resultado.getString("logradouro"));
                endereco.setNumero(resultado.getInt("numero"));
                if (!(resultado.getString("complemento") == null)){
                    endereco.setComplemento(resultado.getString("complemento"));
                }else{
                    endereco.setComplemento("N/I");
                }
                endereco.setBairro(resultado.getString("bairro"));
                endereco.setCidade(resultado.getString("cidade"));
                endereco.setEstado(resultado.getString("estado"));
                if (!(resultado.getString("CEP") == null)){
                    endereco.setCEP(resultado.getString("CEP"));
                }else{
                    endereco.setCEP("N/I");
                }

                retorno = endereco;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public List<Endereco> listar() {
        return null;
    }
}
