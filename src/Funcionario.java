
import java.util.ArrayList;
import java.util.List;

public final class Funcionario extends Pessoa {
    private Double salarioBruto;
    private Double descontoINSS;
    private Double descontoIR;
    private Double descontoD;
    private List<Dependente> dependente;

    public Funcionario(String nome, String cpf, String dataNascimento, Double salarioBruto) {
        super(nome, cpf, dataNascimento);
        this.salarioBruto = salarioBruto;
        this.descontoINSS = 0.0;
        this.descontoD = 0.0;
        this.descontoIR = 0.0;
        this.dependente = new ArrayList<>();
    }

    public List<Dependente> getDependente() {
        return dependente;
    }

    public void setDependente(List<Dependente> dependente) {
        this.dependente = dependente;
    }
    public Double getSalarioBruto() {
        return salarioBruto;
    }

    public void setSalarioBruto(Double salarioBruto) {
        this.salarioBruto = salarioBruto;
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

    public Double getDescontoD() {
        return descontoD;
    }

    public void setDescontoD(Double descontoD) {
        this.descontoD = descontoD;
    }
}
