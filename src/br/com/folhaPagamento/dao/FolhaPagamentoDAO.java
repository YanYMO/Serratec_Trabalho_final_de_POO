package br.com.folhaPagamento.dao;

import br.com.folhaPagamento.model.Contracheque;
import br.com.folhaPagamento.model.FolhaPagamento;
import br.com.folhaPagamento.model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FolhaPagamentoDAO {
    private Connection connection;

    public FolhaPagamentoDAO(Connection connection) {
        this.connection = connection;
    }

    //A função "inserir" realiza a inserção dos dados de cada folhaDePagamento que a chamar
    //no Banco de Dados, inserindo a chave extrangeira do funcionario a que pertencer.
    public void inserir(Funcionario funcionario, FolhaPagamento folhaPagamento) {
        String sql = "insert into matriz.folhaPagamento(codigo, dataPagamento, descontoINSS, descontoIR, salarioLiquido, idFuncionario) values (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setObject(1, folhaPagamento.getCodigo());
            stmt.setDate(2, Date.valueOf(folhaPagamento.getData()));
            stmt.setDouble(3, folhaPagamento.getDescontoINSS());
            stmt.setDouble(4, folhaPagamento.getDescontoIR());
            stmt.setDouble(5, folhaPagamento.getSalarioLiquido());
            stmt.setObject(6, funcionario.getIdFuncionario());
            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Devolve uma lista formatada atraves de uma busca no Banco de Dados, que será armazenada na lista br.com.FolhaPagamento.model.Contracheque da Classe br.com.FolhaPagamento.model.Contracheque.
    public List<Contracheque> listar() {
        String sql = "select nome, cpf, mfol.descontoINSS, mfol.descontoIR, salarioLiquido from matriz.folhaPagamento mfol inner join matriz.funcionario mfun on mfun.idFuncionario = mfol.idfuncionario";
        List<Contracheque> contracheques = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Contracheque contracheque = new Contracheque(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("descontoINSS"),
                        rs.getString("descontoIR"),
                        rs.getString("salarioLiquido"));

                        contracheques.add(contracheque);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return contracheques;
    }
}