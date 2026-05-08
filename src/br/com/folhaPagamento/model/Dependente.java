package br.com.folhaPagamento.model;

import br.com.folhaPagamento.exception.DependenteException;
import br.com.folhaPagamento.model.enums.Parentesco;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public final class Dependente extends Pessoa {
    private UUID idDependente;
    private Parentesco parentesco;
    private static final Integer idadeMinima = 18;

    public Dependente(String nome, String cpf, String dataNascimento, String parentesco) throws DependenteException {
        super(nome, cpf, dataNascimento);
        this.idDependente = UUID.randomUUID();
        this.parentesco = Parentesco.valueOf(parentesco);

        //Faz o calculo inicial para realizar a validação de idade do
        //dependente pegando a data atual e subtraindo o ano de nascimento.
        LocalDate hoje = LocalDate.now();
        Period periodo = Period.between(this.dataNascimento, hoje);
        int idadeAtual  =  periodo.getYears();

        if (idadeAtual >= idadeMinima) {
            throw new DependenteException("O dependente tem a idade superior ao permitido!");
        }
    }

    public UUID getIdDependente() {
        return idDependente;
    }

    public Parentesco getParentesco() {
        return parentesco;
    }
}