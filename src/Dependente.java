public final class Dependente extends Pessoa {
    private Funcionario funcionario;
    private Parentesco parentesco;

    public Dependente(String nome, String cpf, String dataNascimento, Parentesco parentesco) {
        super(nome, cpf, dataNascimento);
        this.parentesco = parentesco;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Parentesco getParentesco() {
        return parentesco;
    }
}
