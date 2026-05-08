package br.com.folhaPagamento.dao;

import br.com.folhaPagamento.model.Dependente;
import br.com.folhaPagamento.model.Funcionario;
import org.postgresql.util.PGobject;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DependenteDAO {
    private Connection connection;

    public DependenteDAO(Connection connection) {
        this.connection = connection;
    }

    //A função "inserir" realiza a inserção dos dados de cada dependente que a chamar
    //no Banco de Dados, incluindo a chave extrangeiro do funcionario que for dependente.
    public void inserir(Funcionario funcionario, Dependente dependente) {
        String sql = "insert into matriz.dependente(idDependente, nome, cpf, dataNascimento, parentesco, idFuncionario) values (?, ?, ?, ?, ?, ?)";

        try {
            //Faz com que o tipo ENUM do Banco de Dados e o Tipo ENUM do JAVA, tenham valores compatíveis.
            PGobject parentesco = new PGobject();
            parentesco.setType("matriz.parentesco"); // Nome do tipo no banco
            parentesco.setValue(dependente.getParentesco().name());

            PreparedStatement stmt = null;

            stmt = connection.prepareStatement(sql);

            stmt.setObject(1, dependente.getIdDependente());
            stmt.setString(2, dependente.getNome());
            stmt.setString(3, dependente.getCpf());
            stmt.setDate(4, Date.valueOf(dependente.getDataNascimento()));
            stmt.setObject(5, parentesco);
            stmt.setObject(6, funcionario.getIdFuncionario());
            stmt.execute();
            stmt.close();

        } catch (PSQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
