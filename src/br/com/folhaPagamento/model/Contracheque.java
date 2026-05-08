package br.com.folhaPagamento.model;

public class Contracheque {
    private String nome;
    private String cpf;
    private Double descontoINSS;
    private Double descontoIR;
    private Double salarioLiquido;

    public Contracheque(String nome, String cpf, String descontoINSS, String descontoIR, String salarioLiquido) {
        this.nome = nome;
        this.cpf = cpf;
        this.descontoINSS = Double.valueOf(descontoINSS);
        this.descontoIR = Double.valueOf(descontoIR);
        this.salarioLiquido = Double.valueOf(salarioLiquido);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Double getDescontoINSS() {
        return descontoINSS;
    }

    public Double getDescontoIR() {
        return descontoIR;
    }

    public Double getSalarioLiquido() {
        return salarioLiquido;
    }
}
