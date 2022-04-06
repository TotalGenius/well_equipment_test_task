package wellequipment.app.connection;

import java.sql.*;

public class DBConnection {
    private static final String DB_DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:test.db";


    //Устанавливаем связь с базой данных
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            //Если таблицы не были созданы ранее, то создаем таблицы
            initializeTableIfNotExists(connection);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //Создаем 2 таблицы: well и equipment
    private void initializeTableIfNotExists(Connection connection) {
        Statement statementWell = null;
        Statement statementEquipment = null;
        try {
            statementWell = connection.createStatement();
            statementEquipment = connection.createStatement();
            statementWell.execute("CREATE TABLE IF NOT EXISTS  well" +
                    "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name VARCHAR (32) NOT NULL UNIQUE )" +
                    "");
            statementEquipment.execute("CREATE TABLE IF NOT EXISTS  equipment" +
                    "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " name VARCHAR (32) NOT NULL UNIQUE , " +
                    "well_id INTEGER NOT NULL , " +
                    "FOREIGN KEY (well_id) REFERENCES well (id) ON DELETE CASCADE " +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
