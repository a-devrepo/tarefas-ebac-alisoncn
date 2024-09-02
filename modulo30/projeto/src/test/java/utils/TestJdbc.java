package utils;

import com.sudy.projeto.config.ConnectionFactory;
import com.sudy.projeto.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestJdbc {
    public static void executeDelete(String sql) throws RuntimeException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir registros" + e.getMessage());
        } finally {
            try {
                ConnectionFactory.closeConnection(connection, statement, null);
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar conexão" + e.getMessage());
            }
        }
    }
}
