package br.com.folhaPagamento.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Pessoa {
    protected String nome;
    protected final String cpf;
    protected LocalDate dataNascimento;
    private static final DateTimeFormatter conversao = DateTimeFormatter.ofPattern("yyyyMMdd");

    public Pessoa(String nome, String cpf, String dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = LocalDate.parse(dataNascimento, conversao);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}