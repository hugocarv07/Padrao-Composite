package padroesprojeto.composite.hospital;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Departamento implements Unidade {

    private String nome;
    private List<Unidade> filhos = new ArrayList<>();

    public Departamento(String nome) {
        this.nome = nome;
    }

    public void adicionar(Unidade unidade) {
        if (unidade == this) {
            throw new IllegalArgumentException("Departamento não pode conter a si mesmo");
        }
        filhos.add(unidade);
    }

    public void remover(Unidade unidade) {
        filhos.remove(unidade);
    }

    public List<Unidade> getFilhos() {
        return Collections.unmodifiableList(filhos);
    }

    @Override
    public float calcularCusto() {
        float total = 0;
        for (Unidade filho : filhos) {
            total += filho.calcularCusto();
        }
        return total;
    }

    @Override
    public int contarFuncionarios() {
        int total = 0;
        for (Unidade filho : filhos) {
            total += filho.contarFuncionarios();
        }
        return total;
    }

    @Override
    public String exibir(int nivel) {
        StringBuilder saida = new StringBuilder("  ".repeat(nivel) + nome + "/");
        for (Unidade filho : filhos) {
            saida.append("\n").append(filho.exibir(nivel + 1));
        }
        return saida.toString();
    }
}
