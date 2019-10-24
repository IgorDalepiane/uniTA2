package Empresa.Model;

import java.sql.Connection;

public interface InterfaceDAO {
    public Connection getConnection();
    public void setConnection(Connection connection);
}
