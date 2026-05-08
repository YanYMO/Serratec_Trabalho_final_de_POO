package br.com.folhaPagamento.service;

import br.com.folhaPagamento.exception.DependenteException;
import br.com.folhaPagamento.exception.FuncionarioException;
import br.com.folhaPagamento.model.Contracheque;
import br.com.folhaPagamento.model.Funcionario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {

    private File validarArquivo(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);

        if (!arquivo.exists()) {
            throw new IllegalArgumentException("Arquivo não encontrado: " + caminhoArquivo);
        }
        if (!arquivo.isFile()) {
            throw new IllegalArgumentException("O caminho informado não é um arquivo: " + caminhoArquivo);
        }
        if (!arquivo.canRead()) {
            throw new IllegalArgumentException("Sem permissão para ler o arquivo: " + caminhoArquivo);
        }
        if (!caminhoArquivo.endsWith(".csv")) {
            throw new IllegalArgumentException("O arquivo deve ser do tipo .csv: " + caminhoArquivo);
        }
        return arquivo;
    }

    public List<Funcionario> lerArquivo(String arquivoEntrada) {
        File arquivo = validarArquivo(arquivoEntrada);
        List<Funcionario> funcionarios = new ArrayList<>();

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
                            System.out.println("Erro ao cadastrar dependente: " + ex.getMessage());
                        }
                    }
                    funcionario.adicionarFolhaPagamento(funcionario);
                    funcionarios.add(funcionario);

                } catch (FuncionarioException ex) {
                    System.out.println("Erro no cadastro de funcionario: " + ex.getMessage());
                    while ((linha = br.readLine()) != null) {
                        if (linha.isBlank()) {
                            break;
                        }
                    }
                }
            }
        } catch (RuntimeException | IOException e) {
            throw new IllegalArgumentException(e.getMessage() + " tente novamente.\n");
        }
        return  funcionarios;
    }

    public void escreverArquivo(String arquivoSaida, List<Contracheque> contracheque) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSaida + ".csv"))) {

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
            throw new IllegalArgumentException("Erro na escrita de arquivo: " + e.getMessage());
        }
        System.out.println("Arquivo exportado com sucesso!");
    }
}
