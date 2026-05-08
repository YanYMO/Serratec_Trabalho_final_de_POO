package br.com.folhaPagamento.dao;

import br.com.folhaPagamento.model.Funcionario;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FuncionarioDAO {
    private Connection connection;

    public FuncionarioDAO(Connection connection) {
        this.connection = connection;
    }

    //A função "inserir" realiza a inserção dos dados de cada funcionario que a chamar no Banco de Dados.
    public void inserir(Funcionario funcionario) {
        String sql = "insert into matriz.funcionario(idFuncionario, nome, cpf, dataNascimento, salarioBruto, descontoINSS, descontoIR) values (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setObject(1, funcionario.getIdFuncionario());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getCpf());
            stmt.setDate(4, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setDouble(5, funcionario.getSalarioBruto());
            stmt.setDouble(6, funcionario.getDescontoINSS());
            stmt.setDouble(7, funcionario.getDescontoIR());
            stmt.execute();
            stmt.close();

        }  catch (PSQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}