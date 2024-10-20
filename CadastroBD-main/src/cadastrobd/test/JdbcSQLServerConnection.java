package cadastrobd.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

import cadastrobd.model.util.CredentialsLoader;
 
/**
 * This program demonstrates how to establish database connection to Microsoft SQL Server.
 * @author www.codejava.net
 * @source https://www.codejava.net/java-se/jdbc/connect-to-microsoft-sql-server-via-jdbc
 */
public class JdbcSQLServerConnection {
    
    private static final Logger LOGGER = Logger.getLogger(JdbcSQLServerConnection.class.getName());
    
    private final String HOSTNAME;
    private final String DBNAME;
    private final String LOGIN;
    private final String PASSWORD;
    
    public JdbcSQLServerConnection() {
        CredentialsLoader loader = new CredentialsLoader();
        HOSTNAME = loader.getHostname();
        DBNAME = loader.getDbname();
        LOGIN = loader.getLogin();
        PASSWORD = loader.getPassword();
    }
    
    private void run() {
        Connection conn = null;
        try {
            String dbURL = "jdbc:sqlserver://" + HOSTNAME + ":1433;databaseName="
                + DBNAME + ";encrypt=true;trustServerCertificate=true;";
            conn = DriverManager.getConnection(dbURL, LOGIN, PASSWORD);
            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }
    
    public static void main(String[] args) {
        new JdbcSQLServerConnection().run();
    }
 
}
