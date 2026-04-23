import br.com.FolhaPagamento.dao.DependenteDAO;
import br.com.FolhaPagamento.dao.FolhaPagamentoDAO;
import br.com.FolhaPagamento.dao.FuncionarioDAO;
import br.com.FolhaPagamento.exception.DependenteException;
import br.com.FolhaPagamento.exception.FuncionarioException;
import br.com.FolhaPagamento.infra.ConnectionFactory;
import br.com.FolhaPagamento.model.Contracheque;
import br.com.FolhaPagamento.model.Dependente;
import br.com.FolhaPagamento.model.FolhaPagamento;
import br.com.FolhaPagamento.model.Funcionario;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        //Cria uma conexão com o Banco de Dados que será reutilizada durante a execução.
        Connection connection = new ConnectionFactory().getConnection();

        //Lista de funcionarios, para caso sejam inseridos vários na mesma execução.
        List<Funcionario> funcionarios = new ArrayList<>();

        //Menu inicial do programa.
        System.out.println("---------------------------------------------------------------------");
        System.out.println("               Sistema de calculo de Folha de Pagamento              ");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Para calcular a folha de pagamento, informe o local, nome e extenção\n" +
                           "do arquivo que contenha os dados dos funcionários e seus dependentes.");

        //While para repetir a solicitação ao usuário caso o arquivo não seja encontrado.
        while (true) {
            System.out.print("Arquivo = ");
            String arquivoEntrada = scan.nextLine();
            System.out.println();

            //Faz a tentativa de leitura do arquivo escrito pelo usuário.
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivoEntrada)))) {
                String linha;

                //Realiza a leitura de cada linha e atribui aos construtores. Levando em consideração as exceções definidas.
                while ((linha = br.readLine()) != null) {
                    try {
                        String[] dadosF = linha.trim().split(";");

                        //Verifica se já existe algum funcionario com o mesmo CPF e caso sim lança uma exceção
                        //que consome todos os seus possíveis dependentes e pula para um novo funcionario.
                        if (funcionarios.stream().anyMatch(funcionario -> funcionario.getCpf().equals(dadosF[1]))) {
                            throw new FuncionarioException("O funcionario possui CPF idêntico a outro já cadastrado!");
                        }
                        Funcionario funcionario = new Funcionario(dadosF[0], dadosF[1], dadosF[2], dadosF[3]);

                        while ((linha = br.readLine()) != null) {
                            try {
                                if (linha.isBlank()) {
                                   break;
                                }
                                String[] dadosD = linha.trim().split(";");
                                funcionario.adicionarDependente(dadosD[0], dadosD[1], dadosD[2], dadosD[3]);

                            } catch (DependenteException ex) {
                                System.out.println("Erro ao cadastrar dependente: " + ex.getMensagem());
                            }
                        }
                        funcionario.adicionarFolhaPagamento(funcionario);
                        funcionarios.add(funcionario);

                    } catch (FuncionarioException ex) {
                        System.out.println("Erro no cadastro de funcionario: " + ex.getMensagem());
                        while ((linha = br.readLine()) != null) {
                            if (linha.isBlank()) {
                                break;
                            }
                        }
                    }
                }
                br.close();
                break;

            } catch (FileNotFoundException e) {
                System.out.printf("Erro na leitura de arquivo: " + e.getMessage() + " tente novamente.\n");
            } catch (UnsupportedEncodingException e) {
                System.out.println("Erro na leitura de arquivo: " + e.getMessage() + " tente novamente.\n");
            } catch (IOException e) {
                System.out.println("Erro na leitura de arquivo: " + e.getMessage() + " tente novamente.\n");
            }
        }

        // Adiciona os dados validados ao banco de dados.
        try {
            //Cria os objetos de cada Classe DAO.
            FuncionarioDAO dao = new FuncionarioDAO(connection);
            DependenteDAO dao2 = new DependenteDAO(connection);
            FolhaPagamentoDAO dao3 = new FolhaPagamentoDAO(connection);

            //Faz a inserção das informações ao percorrer as
            //listas de br.com.FolhaPagamento.model.Funcionario, br.com.FolhaPagamento.model.Dependente e br.com.FolhaPagamento.model.FolhaPagamento.
            for (Funcionario fun : funcionarios) {
                dao.inserir(fun);

                for (Dependente den : fun.getDependente()) {
                    dao2.inserir(fun, den);
                }
                for (FolhaPagamento fol : fun.getFolhaPagamento()) {
                    dao3.inserir(fun, fol);
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

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSaida + ".csv"))) {
            //Cria o Objeto da Classe folhaPagamentoDAO.
            FolhaPagamentoDAO dao3 = new FolhaPagamentoDAO(connection);

            //Recebe a lista formatada do Banco de Dados.
            List<Contracheque> contracheque = dao3.listar();

            for (int i = 0; i < contracheque.size(); i++) {
                bw.write(String.join(";",
                        contracheque.get(i).getNome(),
                        contracheque.get(i).getCpf(),
                        String.format("%.2f", contracheque.get(i).getDescontoINSS()),
                        String.format("%.2f", contracheque.get(i).getDescontoIR()),
                        String.format("%.2f", contracheque.get(i).getSalarioLiquido())));
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            System.out.println("Erro na escrita de arquivo: " + e.getMessage());
        }
        System.out.println("Arquivo exportado com sucesso!");

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão com o Banco de Dados: " + e.getMessage());
        }
    }
}