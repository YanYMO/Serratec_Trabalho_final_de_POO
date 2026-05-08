package br.com.folhaPagamento.exception;

public class FuncionarioException extends RuntimeException {
    private String mensagem;

    public FuncionarioException(String mensagem) {
        super(mensagem);
    }
}
