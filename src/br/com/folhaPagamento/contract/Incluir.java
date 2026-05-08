package br.com.folhaPagamento.contract;

import br.com.folhaPagamento.model.Funcionario;

public interface Incluir {

    public void adicionarFolhaPagamento(Funcionario funcionario);

    public void adicionarDependente(String nome, String cpf, String dataNascimento, String parentesco);
}