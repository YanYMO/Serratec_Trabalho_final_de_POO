package br.com.folhaPagamento.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private String url = "jdbc:postgresql://localhost:5432/folhapagamento";
    private String usuario = "postgres";
    private String senha = "deumaoito";
    private Connection connection;

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url, usuario, senha);
            return connection;

        } catch (SQLException e) {
            System.err.println("Não foi possivel conectar ao banco de dados!");
            throw new RuntimeException(e.getMessage());
        }
    }
}