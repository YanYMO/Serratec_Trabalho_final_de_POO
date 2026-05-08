package br.com.folhaPagamento.exception;

public class DependenteException extends RuntimeException {
    private String mensagem;

    public DependenteException(String mensagem) {
        super(mensagem);
    }
}