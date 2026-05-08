package br.com.folhaPagamento.model;

import java.time.LocalDate;
import java.util.UUID;

public final class FolhaPagamento {
    private UUID codigo;
    private LocalDate data;
    private Double descontoINSS;
    private Double descontoIR;
    private Double salarioLiquido;

    public FolhaPagamento(Funcionario funcionario) {
        this.codigo = UUID.randomUUID();
        this.data = LocalDate.now();
        this.descontoINSS = funcionario.getDescontoINSS();
        this.descontoIR = funcionario.getDescontoIR();
        this.salarioLiquido = 0.0;
    }

    //A funcão "calculaSalarioLiquido" faz o cálculo do salário liquido
    //após receber como parâmetro o funcionario ao qual será calculado,
    //retornando um valor tipo Double diretamente no construtor.
    public Double calculaSalarioLiquido(Funcionario funcionario) {
        return this.salarioLiquido = funcionario.getSalarioBruto() - funcionario.getDescontoINSS() - funcionario.getDescontoIR();
    }

    public UUID getCodigo() {
        return codigo;
    }

    public Double getDescontoINSS() {
        return descontoINSS;
    }

    public Double getDescontoIR() {
        return descontoIR;
    }

    public LocalDate getData() {
        return data;
    }

    public Double getSalarioLiquido() {
        return salarioLiquido;
    }
}