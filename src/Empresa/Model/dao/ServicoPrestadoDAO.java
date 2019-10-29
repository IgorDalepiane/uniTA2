package Empresa.Model.dao;

import Empresa.Model.InterfaceDAO;
import Empresa.Model.Session;
import Empresa.Model.domain.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ServicoPrestadoDAO implements InterfaceDAO {
    private Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ServicoPrestado servP) throws SQLException, ParseException {
        String sql = "INSERT INTO servicoprestado (idCliente, idServ, idFunc, data, horaInicio, horaFim,idEmp) VALUES(?,?,?,?,?,?,"+Session.get().getId()+")";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, servP.getCliente().getId());
        stmt.setInt(2, servP.getServico().getId());
        stmt.setInt(3, servP.getFunc().getId());
        stmt.setDate(4, Date.valueOf(servP.getData()));
        stmt.setTime(5, servP.getHrInicial());
        stmt.setTime(6, servP.getHrFinal());
        return stmt.execute();
    }

    public boolean alterar(ServicoPrestado servP) {
        return false;
    }

    public boolean remover(ServicoPrestado servP) {
        return false;
    }

    public ServicoPrestado buscar(ServicoPrestado servP) {
        return null;
    }

    public List<ServicoPrestado> listar() throws SQLException {
        String sql = "SELECT * FROM servicoprestado WHERE servicoprestado.idEmp=" + Session.get().getId();
        List<ServicoPrestado> retorno = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            ServicoPrestado servicoPrestado = new ServicoPrestado();

            Servico servico = new Servico();
            ServicoDAO servicoDAO = new ServicoDAO();
            servicoDAO.setConnection(connection);

            servico.setId(resultado.getInt("idServ"));
            servicoPrestado.setServico(servicoDAO.buscar(servico));

            Funcionario funcionario = new Funcionario();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            funcionarioDAO.setConnection(connection);

            funcionario.setId(resultado.getInt("idFunc"));
            servicoPrestado.setFunc(funcionarioDAO.buscar(funcionario));

            servicoPrestado.setEmpresa(Session.get());

            Cliente cliente = new Cliente();
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.setConnection(connection);

            cliente.setId(resultado.getInt("idCliente"));
            servicoPrestado.setCliente(clienteDAO.buscar(cliente));

            servicoPrestado.setData(String.valueOf(resultado.getDate("data")));
            servicoPrestado.setHrInicial(resultado.getTime("horaInicio"));
            servicoPrestado.setHrFinal(resultado.getTime("horaFim"));

            Serv_ProdDAO servProdDAO = new Serv_ProdDAO();
            servProdDAO.setConnection(connection);
            servicoPrestado.setProdUtiliz(servProdDAO.buscarPorServicoPrestado(servicoPrestado));
            retorno.add(servicoPrestado);
        }
        return retorno;
    }
}
