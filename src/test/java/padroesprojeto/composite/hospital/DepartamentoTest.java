package padroesprojeto.composite.hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartamentoTest {

    Departamento hospital;
    Departamento enfermagem;
    Departamento administrativo;

    @BeforeEach
    void setUp() {
        hospital = new Departamento("Hospital");
        enfermagem = new Departamento("Enfermagem");
        administrativo = new Departamento("Administrativo");

        enfermagem.adicionar(new Funcionario("Ana", "Enfermeira", 3000.0f));
        enfermagem.adicionar(new Funcionario("Bia", "Técnica", 3500.0f));
        administrativo.adicionar(new Funcionario("Caio", "Recepcionista", 2500.0f));

        hospital.adicionar(enfermagem);
        hospital.adicionar(administrativo);
        hospital.adicionar(new Funcionario("Dr. Paulo", "Diretor", 10000.0f));
    }

    private float custoDe(Unidade unidade) {
        return unidade.calcularCusto();
    }

    @Test
    void funcionarioDeveCustarOSeuSalario() {
        Funcionario funcionario = new Funcionario("Ana", "Enfermeira", 3000.0f);

        assertEquals(3000.0f, funcionario.calcularCusto(), 0.01f);
        assertEquals(1, funcionario.contarFuncionarios());
    }

    @Test
    void departamentoVazioNaoDeveTerCustoNemFuncionarios() {
        Departamento vazio = new Departamento("Vazio");

        assertEquals(0.0f, vazio.calcularCusto(), 0.01f);
        assertEquals(0, vazio.contarFuncionarios());
    }

    @Test
    void departamentoDeveSomarOCustoDosFuncionarios() {
        assertEquals(6500.0f, enfermagem.calcularCusto(), 0.01f);
        assertEquals(2500.0f, administrativo.calcularCusto(), 0.01f);
    }

    @Test
    void hierarquiaDeveSomarRecursivamenteTodosOsNiveis() {
        assertEquals(19000.0f, hospital.calcularCusto(), 0.01f);
        assertEquals(4, hospital.contarFuncionarios());
    }

    @Test
    void deveTratarFolhaERamoDoMesmoJeitoPelaInterface() {
        Unidade folha = new Funcionario("Dr. Paulo", "Diretor", 10000.0f);
        Unidade ramo = enfermagem;
        Unidade arvore = hospital;

        assertEquals(10000.0f, custoDe(folha), 0.01f);
        assertEquals(6500.0f, custoDe(ramo), 0.01f);
        assertEquals(19000.0f, custoDe(arvore), 0.01f);
    }

    @Test
    void removerUmDepartamentoDeveReduzirOCustoDoPai() {
        hospital.remover(administrativo);

        assertEquals(16500.0f, hospital.calcularCusto(), 0.01f);
        assertEquals(3, hospital.contarFuncionarios());
    }

    @Test
    void alterarUmDepartamentoDeveAparecerNoCustoDoPai() {
        enfermagem.adicionar(new Funcionario("Duda", "Enfermeira", 3000.0f));

        assertEquals(22000.0f, hospital.calcularCusto(), 0.01f);
        assertEquals(5, hospital.contarFuncionarios());
    }

    @Test
    void deveExibirAHierarquiaComIndentacao() {
        String esperado = "Hospital/\n"
                + "  Enfermagem/\n"
                + "    Ana (Enfermeira)\n"
                + "    Bia (Técnica)\n"
                + "  Administrativo/\n"
                + "    Caio (Recepcionista)\n"
                + "  Dr. Paulo (Diretor)";

        assertEquals(esperado, hospital.exibir(0));
    }

    @Test
    void deveExibirDepartamentoVazio() {
        assertEquals("Vazio/", new Departamento("Vazio").exibir(0));
    }

    @Test
    void naoDevePermitirQueUmDepartamentoContenhaASiMesmo() {
        try {
            hospital.adicionar(hospital);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Departamento não pode conter a si mesmo", e.getMessage());
        }
    }

    @Test
    void deveExporOsFilhosDiretosSemPermitirAlteracaoExterna() {
        assertEquals(3, hospital.getFilhos().size());
        assertThrows(UnsupportedOperationException.class,
                () -> hospital.getFilhos().add(new Funcionario("X", "Y", 1.0f)));
    }
}
