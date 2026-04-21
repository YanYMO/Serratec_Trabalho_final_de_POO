import java.time.LocalDate;
import java.util.UUID;

public final class FolhaPagamento {
    private UUID codigo;
    private String nomeFuncionario;
    private String cpf;
    private LocalDate data;
    private Double descontoINSS;
    private Double descontoIR;
    private Double salarioLiquido;

    public FolhaPagamento(Funcionario funcionario) {
        this.codigo = UUID.randomUUID();
        this.data = LocalDate.now();
        this.descontoINSS = funcionario.getDescontoINSS();
        this.descontoIR = funcionario.getDescontoIR();
        this.salarioLiquido = calculaSalarioLiquido(funcionario);
    }

    public FolhaPagamento() {
    }

    public Double calculaSalarioLiquido(Funcionario funcionario) {
        return salarioLiquido = funcionario.getSalarioBruto() - funcionario.getDescontoINSS() - funcionario.getDescontoIR();
    }

    public UUID getCodigo() {
        return codigo;
    }

    public LocalDate getLocalDate() {
        return data;
    }

    public Double getDescontoINSS() {
        return descontoINSS;
    }

    public void setDescontoINSS(Double descontoINSS) {
        this.descontoINSS = descontoINSS;
    }

    public Double getDescontoIR() {
        return descontoIR;
    }

    public void setDescontoIR(Double descontoIR) {
        this.descontoIR = descontoIR;
    }

    public LocalDate getData() {
        return data;
    }

    public Double getSalarioLiquido() {
        return salarioLiquido;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }
}