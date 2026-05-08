package br.com.folhaPagamento.model;

import br.com.folhaPagamento.exception.DependenteException;
import br.com.folhaPagamento.contract.Impostos;
import br.com.folhaPagamento.contract.Incluir;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Funcionario extends Pessoa implements Impostos, Incluir {
    private UUID idFuncionario;
    private Double salarioBruto;
    private Double descontoINSS;
    private Double descontoIR;
    private Double descontoD;
    private List<Dependente> dependente;
    private List<FolhaPagamento> folhaPagamento;

    public Funcionario(String nome, String cpf, String dataNascimento, String salarioBruto) {
        super(nome, cpf, dataNascimento);
        this.idFuncionario = UUID.randomUUID();
        this.salarioBruto = Double.valueOf(salarioBruto);
        this.descontoINSS = 0.0;
        this.descontoD = 0.0;
        this.descontoIR = 0.0;
        this.dependente = new ArrayList<>();
        this.folhaPagamento = new ArrayList<>();
    }

    //A função "adicionarDependente" faz a verificação do CPF e caso seja único, preenche o
    //construtor de br.com.FolhaPagamento.model.Dependente e adiciona na lista de dependentes do funcionario que a chamou.
    @Override
    public void adicionarDependente(String nome, String cpf, String dataNascimento, String parentesco) {

        //Verifica se existe algum dependente com o mesmo CPF na lista de dependentes
        //e caso sim, pula esse dependente e passa para o próximo da lista, caso exista.
        if (dependente.stream().anyMatch(dependente -> dependente.getCpf().equals(cpf))) {
            throw new DependenteException("O dependente possui CPF idêntico a outro já cadastrado!");
        }
        Dependente dependente = new Dependente(nome, cpf, dataNascimento, parentesco);
        this.dependente.add(dependente);
    }

    //A função "adicionarFolhaPagamento" chama a função "calculaImpostos", cria uma folhaPagamento,
    //passando o nome do funcionario que a chamou, chama a função "calculaSalarioLiquido" também
    //passando o funcionario que a chamou e por fim adiciona a folhaPagamento na lista de
    //folhaPagamento do mesmo funcionario.
    @Override
    public void adicionarFolhaPagamento(Funcionario funcionario) {
        funcionario.calculaImpostos();
        FolhaPagamento folhaPagamento = new FolhaPagamento(funcionario);
        folhaPagamento.calculaSalarioLiquido(funcionario);
        this.folhaPagamento.add(folhaPagamento);
    }

    //A função "calculaImpostos" reune todas as funções
    //de calculo de impostos e as executa na ordem correta.
    @Override
    public void calculaImpostos() {
        calculaINSS();
        calculaD();
        calculaIR();
    }

    //A função "calculaINSS" faz o calculo do desconto de INSS do funcionario.
    @Override
    public void calculaINSS() {
        if (salarioBruto <= 1518.0){
            descontoINSS = (salarioBruto * 0.075);
        } else if (salarioBruto > 1518.0 && salarioBruto <= 2793.88){
            descontoINSS = (salarioBruto * 0.09) - 22.77;
        } else if (salarioBruto > 2793.88 && salarioBruto <= 4190.83){
            descontoINSS = (salarioBruto * 0.12) - 109.60;
        } else if (salarioBruto > 4190.83 && salarioBruto <= 8157.41){
            descontoINSS = (salarioBruto * 0.14) - 190.42;
        } else {
            descontoINSS = 951.62;
        }
    }

    //A função "calculaD" faz o calculo do desconto de Dependentes do funcionario, caso tenha.
    @Override
    public void calculaD() {
        this.descontoD = dependente.size() * 189.59;
    }

    //A função "calculaIR" faz o calculo do desconto de Imposto de Renda do funcionario.
    @Override
    public void calculaIR() {
        double baseDeCalculoIR = salarioBruto - descontoD - descontoINSS;

        if ((baseDeCalculoIR) <= 2259.0){
           descontoIR = 0.0;
        } else if ((baseDeCalculoIR) > 2259.0 && (baseDeCalculoIR) <= 2826.65) {
            descontoIR = ((baseDeCalculoIR) * 0.075) - 169.44;
        } else if ((baseDeCalculoIR) > 2826.65 && (baseDeCalculoIR) <= 3751.05) {
            descontoIR = ((baseDeCalculoIR) * 0.15) - 381.44;
        } else if ((baseDeCalculoIR) > 3751.05 && (baseDeCalculoIR) <= 4664.68) {
            descontoIR = ((baseDeCalculoIR) * 0.225) - 662.77;
        } else if ((baseDeCalculoIR) > 4664.68) {
            descontoIR = ((baseDeCalculoIR) * 0.275) - 896.0;
        }
    }

    public UUID getIdFuncionario() {
        return idFuncionario;
    }

    public Double getSalarioBruto() {
        return salarioBruto;
    }

    public Double getDescontoINSS() {
        return descontoINSS;
    }

    public Double getDescontoIR() {
        return descontoIR;
    }

    public Double getDescontoD() {
        return descontoD;
    }

    public List<Dependente> getDependente() {
        return dependente;
    }

    public List<FolhaPagamento> getFolhaPagamento() {
        return folhaPagamento;
    }
}