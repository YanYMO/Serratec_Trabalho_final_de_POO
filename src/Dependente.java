import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public final class Dependente extends Pessoa {
    private UUID idDependente;
    private Funcionario funcionario;
    private Parentesco parentesco;
    private static final Integer idadeMinima = 18;

    public Dependente(String nome, String cpf, String dataNascimento, Parentesco parentesco) {
        super(nome, cpf, dataNascimento);
        this.idDependente = UUID.randomUUID();
        this.parentesco = Parentesco.valueOf(parentesco);

        LocalDate hoje = LocalDate.now();
        Period periodo = Period.between(this.dataNascimento, hoje);
        int idadeAtual  =  periodo.getYears();

        if (idadeAtual > idadeMinima) {
            throw new DependenteException("O dependente tem a idade superior ao permitido!");
        }
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Parentesco getParentesco() {
        return parentesco;
    }
}
