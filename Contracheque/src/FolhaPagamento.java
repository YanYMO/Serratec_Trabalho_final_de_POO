import java.time.LocalDate;
import java.util.Date;

public final class FolhaPagamento implements Contracheque{
    private Integer codigo;
    private Funcionario funcionario;
    private Date LocalDate;
    private Double descontoINSS;
    private Double descontoIR;
    private Double salarioLiquido;

    public FolhaPagamento(Integer codigo, Funcionario funcionario) {
        this.codigo = codigo;
        this.funcionario = funcionario;
        this.LocalDate = LocalDate;
        this.descontoINSS = funcionario.getDescontoINSS();
        this.descontoIR = funcionario.getDescontoIR();
        this.salarioLiquido = 0.0;
    }


    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Date getLocalDate() {
        return LocalDate;
    }

    public void setLocalDate(Date localDate) {
        LocalDate = localDate;
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
}
