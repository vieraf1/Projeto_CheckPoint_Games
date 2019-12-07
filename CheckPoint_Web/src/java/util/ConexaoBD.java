package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String host = "localhost";
            String porta = "1433";
            String databaseName = "CHECKPOINT_GAMES";
            String url = "jdbc:sqlserver://" + host + ":" + porta + ";databaseName=" + databaseName;
            String usuario = "sa";
            String senha = "Alpha_One1";

            return DriverManager.getConnection(url, usuario, senha);

        } catch (SQLException erro1) {
            System.err.println("\nErro SQL Exception ao Tentar se Conectar com o Banco\n");
            return null;
        } catch (ClassNotFoundException erro2) {
            System.err.println("\nErro Null Pointer Exception ao tentar encontrar driver de conexão SQL Server\n");
            return null;
        }
    }

}
