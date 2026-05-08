package br.com.folhaPagamento;

import br.com.folhaPagamento.dao.DependenteDAO;
import br.com.folhaPagamento.dao.FolhaPagamentoDAO;
import br.com.folhaPagamento.dao.FuncionarioDAO;
import br.com.folhaPagamento.infra.ConnectionFactory;
import br.com.folhaPagamento.model.Contracheque;
import br.com.folhaPagamento.model.Dependente;
import br.com.folhaPagamento.model.FolhaPagamento;
import br.com.folhaPagamento.model.Funcionario;
import br.com.folhaPagamento.service.Arquivo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        //Cria uma conexão com o Banco de Dados que será reutilizada durante a execução.
        Connection connection = new ConnectionFactory().getConnection();
        //Cria os objetos de cada Classe DAO.
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);
        DependenteDAO dependenteDAO = new DependenteDAO(connection);
        FolhaPagamentoDAO folhaPagamentoDAO = new FolhaPagamentoDAO(connection);

        //Cria uma instancia de arquivo que será usada para leitura e escrita.
        Arquivo arquivo = new Arquivo();

        //Menu inicial do programa.
        System.out.println("---------------------------------------------------------------------");
        System.out.println("               Sistema de calculo de Folha de Pagamento              ");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Para calcular a folha de pagamento, informe o local, nome e extenção\n" +
                           "do arquivo que contenha os dados dos funcionários e seus dependentes.");

        //While para repetir a solicitação ao usuário caso o arquivo não seja encontrado.
        List<Funcionario> funcionarios = null;
        while (funcionarios == null) {
            System.out.print("Arquivo = ");
            String arquivoEntrada = scan.nextLine();
            try {
                funcionarios = arquivo.lerArquivo(arquivoEntrada);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + ", tente novamente.");
            }
        }

        // Adiciona os dados validados ao banco de dados.
        try {
            //Faz a inserção das informações ao percorrer as
            //listas de br.com.FolhaPagamento.model.Funcionario, br.com.FolhaPagamento.model.Dependente e br.com.FolhaPagamento.model.FolhaPagamento.
            for (Funcionario fun : funcionarios) {
                funcionarioDAO.inserir(fun);

                for (Dependente den : fun.getDependente()) {
                    dependenteDAO.inserir(fun, den);
                }
                for (FolhaPagamento fol : fun.getFolhaPagamento()) {
                    folhaPagamentoDAO.inserir(fun, fol);
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Na inserção de dados no Banco de Dados houve um " + e.getMessage());
        }
        System.out.println("\nLeitura do arquivo concluída!");
        System.out.println("Dados inseridos no Banco de Dados!\n");

        //Solicita ao usuário um nome para o arquivo .csv que será exportado e realiza a escrita.
        //Não é necessário colocar a extenção do arquivo.
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Digite o nome do arquivo da folha de pagamento.");
        System.out.print("Arquivo = ");
        String arquivoSaida = scan.nextLine();
        System.out.println();

        Boolean concluido = false;
        while (!concluido) {
            try {
                List<Contracheque> contracheque = folhaPagamentoDAO.listar();
                arquivo.escreverArquivo(arquivoSaida, contracheque);
                concluido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + ", tente novamente.");
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão com o Banco de Dados: " + e.getMessage());
        }
    }
}