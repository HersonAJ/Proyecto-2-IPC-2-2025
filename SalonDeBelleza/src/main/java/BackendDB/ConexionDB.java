/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 *
 * @author herson
 */
/*
public class ConexionDB {
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/Salon_De_Belleza";
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    private static Connection connection;
    
    private ConexionDB(){}
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || isClosed(connection)) {
            synchronized (ConexionDB.class){
                if (connection == null || isClosed(connection)) {
                    try{
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        System.out.println("intentando conectar a la base de datos...");
                        connection = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
                        System.out.println("conectado a la base de datos.");
                        System.out.println("Base de datos: " + connection.getCatalog());
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error: No se encontro el controlador JDBC");
                        e.printStackTrace();
                    } catch (SQLException e) {
                        System.out.println("Error al conectar a la Base de Datos");
                        throw e;
                    }
                }
            }
        }
        return connection;
    }
    private static boolean isClosed(Connection connection) {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
    
}
*/


public class ConexionDB {
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/Salon_De_Belleza";
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    private static DataSource dataSource;

    // Inicialización del pool de conexiones
    static {
        try {
            PoolProperties p = new PoolProperties();
            p.setUrl(URL_MYSQL);
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            p.setUsername(USER);
            p.setPassword(PASSWORD);
            p.setJmxEnabled(true);
            p.setTestWhileIdle(false);
            p.setTestOnBorrow(true);
            p.setValidationQuery("SELECT 1");
            p.setTestOnReturn(false);
            p.setValidationInterval(30000);
            p.setTimeBetweenEvictionRunsMillis(30000);
            p.setMaxActive(200);
            p.setInitialSize(10);
            p.setMaxWait(10000);
            p.setRemoveAbandonedTimeout(60);
            p.setMinEvictableIdleTimeMillis(30000);
            p.setMinIdle(10);
            p.setLogAbandoned(true);
            p.setRemoveAbandoned(true);
            p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer"
            );
            dataSource = new DataSource();
            dataSource.setPoolProperties(p);
        } catch (Exception e) {
            System.out.println("Error al configurar el pool de conexiones");
            e.printStackTrace();
        }
    }

    private ConexionDB() {}

    // Método estático para obtener una conexión
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
