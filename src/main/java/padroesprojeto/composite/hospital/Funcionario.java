package padroesprojeto.composite.hospital;

public class Funcionario implements Unidade {

    private String nome;
    private String cargo;
    private float salario;

    public Funcionario(String nome, String cargo, float salario) {
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
    }

    @Override
    public float calcularCusto() {
        return salario;
    }

    @Override
    public int contarFuncionarios() {
        return 1;
    }

    @Override
    public String exibir(int nivel) {
        return "  ".repeat(nivel) + nome + " (" + cargo + ")";
    }
}
