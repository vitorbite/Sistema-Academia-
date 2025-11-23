package Menu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Cadastro.*;
import Cadastro.CadastroAcademico;


public class MenuProfessor {

    private final Professor professor;       
    private final CadastroAcademico cadastro; 

    private Map<String, List<Aluno>> alunosPorHorario = new HashMap<>();
    private int aulasCanceladas = 0;

    public MenuProfessor(Professor professor, CadastroAcademico cadastro) {
        this.professor = professor;
        this.cadastro = cadastro;
    }

    public void adicionarHorario(String horario) {
        if (horario == null || horario.isEmpty()) {
            System.out.println("Horário inválido.");
            return;
        }

        professor.adicionarHorario(horario);
        alunosPorHorario.putIfAbsent(horario, new ArrayList<>());
    }

    public void removerHorario(String horario) {
        if (horario == null || horario.isEmpty()) {
            System.out.println("Horário inválido.");
            return;
        }

        professor.getHorarios().remove(horario);
        alunosPorHorario.remove(horario);
    }

    public void listarHorarios() {
        System.out.println("Horários do professor " + professor.getNome() + ":");
        for (String h : professor.getHorarios()) {
            System.out.println(" - " + h);
        }
    }
  
    public void matricularAlunoEmHorario(String horario, Aluno aluno) {
        if (horario == null || horario.isEmpty() || aluno == null) {
            System.out.println("Horário ou aluno inválido.");
            return;
        }

        if (!professor.getHorarios().contains(horario)) {
            System.out.println("Horário não pertence ao professor.");
            return;
        }

        alunosPorHorario.putIfAbsent(horario, new ArrayList<>());
        List<Aluno> alunosDoHorario = alunosPorHorario.get(horario);

        if (!alunosDoHorario.contains(aluno)) {
            alunosDoHorario.add(aluno);
        }
    }

    public void listarAlunosPorHorario(String horario) {
        if (horario == null || horario.isEmpty()) {
            System.out.println("Horário inválido.");
            return;
        }

        List<Aluno> alunosDoHorario = alunosPorHorario.get(horario);

        if (alunosDoHorario == null || alunosDoHorario.isEmpty()) {
            System.out.println("Nenhum aluno matriculado no horário " + horario + ".");
            return;
        }

        System.out.println("Alunos do horário " + horario + ":");
        for (Aluno a : alunosDoHorario) {
            System.out.println(" - " + a.getNome() + " (CPF: " + a.getCpf() + ")");
        }
    }

    public void registrarPresenca(String horario, String cpfAluno) {
        if (horario == null || horario.isEmpty() || cpfAluno == null) {
            System.out.println("Horário ou CPF inválidos.");
            return;
        }

        Aluno aluno = cadastro.buscarAlunoPorCpf(cpfAluno);
        if (aluno == null) {
            System.out.println("Aluno com CPF " + cpfAluno + " não encontrado.");
            return;
        }

        List<Aluno> alunosDoHorario = alunosPorHorario.get(horario);

        if (alunosDoHorario == null || !alunosDoHorario.contains(aluno)) {
            System.out.println("Aluno não está matriculado no horário " + horario + ".");
            return;
        }

        aluno.setFrequencia(aluno.getFrequencia() + 1);

        System.out.println("Presença registrada para " + aluno.getNome() + " no horário " + horario + ".");
    }
    public void cancelarAula(String horario) {
        if (horario == null || horario.isEmpty()) {
            System.out.println("Horário inválido.");
            return;
        }

        if (!professor.getHorarios().contains(horario)) {
            System.out.println("Horário não pertence ao professor.");
            return;
        }

        aulasCanceladas++;
        System.out.println("Aula no horário " + horario + " foi cancelada. Total de aulas canceladas: " + aulasCanceladas);
    }
}

