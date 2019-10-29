package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.Session;
import Empresa.Model.domain.Cargo;
import Empresa.Model.domain.Funcionario;
import Empresa.Model.domain.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuncionarioDAO implements InterfaceDAO {
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
     * @param func o objeto a ser inserido como registro
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean inserir(Funcionario func) {
        String sql = "INSERT INTO funcionario(valorHora, idCargo, idEmp, idPessoa) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, func.getValorHora());
            stmt.setInt(2, func.getCargo().getId());
            stmt.setInt(3, Session.get().getId());
            stmt.setInt(4, func.getId());

            stmt.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Altera um registro do banco de dados
     * @param func o registro a ser alterado
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean alterar(Funcionario func) {
        String sql = "UPDATE funcionario SET valorHora=?, idCargo=?, idEmp=? WHERE idPessoa=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, func.getValorHora());
            stmt.setInt(2, func.getCargo().getId());
            stmt.setInt(3, Session.get().getId());
            stmt.setInt(4, func.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Remove um registro do banco de dados
     * @param func o registro a ser removido
     * @return true se a operação foi concluída com sucesso, false se não
     */
    public boolean remover(Funcionario func) {
        String sql = "DELETE FROM funcionario WHERE idPessoa=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, func.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Busca um registro no banco de dados
     * @param func o registro (incompleto) a ser buscado
     * @return o registro com todas as informações armazenadas no banco de dados
     */
    public Funcionario buscar(Funcionario func) {
        String sql = "SELECT * FROM funcionario WHERE idPessoa=?";
        Funcionario retorno = new Funcionario();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, func.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Pessoa pessoa;
                Cargo cargo = new Cargo();
                Funcionario funcionario = new Funcionario();
                //parte do funcionario
                funcionario.setId(resultado.getInt("idPessoa"));
                funcionario.setValorHora(resultado.getFloat("valorHora"));

                cargo.setId(resultado.getInt("idCargo"));
                CargoDAO cargoDAO = new CargoDAO();
                cargoDAO.setConnection(connection);

                funcionario.setCargo(cargoDAO.buscar(cargo));

                //parte da pessoa
                PessoaDAO pessoaDAO = new PessoaDAO();
                pessoaDAO.setConnection(connection);
                pessoa = pessoaDAO.buscar(funcionario);

                //seta os dados de pessoa
                funcionario.setNome(pessoa.getNome());
                funcionario.setCPF(pessoa.getCPF());
                funcionario.setEmail(pessoa.getEmail());
                funcionario.setRG(pessoa.getRG());
                funcionario.setEndereco(pessoa.getEndereco());
                funcionario.setCelular(pessoa.getCelular());
                funcionario.setResidencial(pessoa.getResidencial());
                retorno = funcionario;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return retorno;
    }

    /**
     * Lista todos os registros da tabela do banco de dados
     * @return List com os registros
     */
    public List<Funcionario> listar() {
        String sql = "SELECT * FROM funcionario WHERE funcionario.idEmp=" + Session.get().getId();
        List<Funcionario> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                //parte do funcionario
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultado.getInt("idPessoa"));
                retorno.add(buscar(funcionario));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return retorno;
    }
}
