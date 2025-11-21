package Cadastro;

import java.util.ArrayList;
import java.util.List;

public class CadastroAcademico {

    private List<Aluno> alunos;
    private List<Professor> professores;

    public CadastroAcademico() {
        this.alunos = new ArrayList<>();
        this.professores = new ArrayList<>();
    }

    public void cadastrarAluno(Aluno aluno) {
        if (aluno != null) {
            alunos.add(aluno);
        }
    }

    public void cadastrarProfessor(Professor professor) {
        if (professor != null) {
            professores.add(professor);
        }
    }

    public Aluno buscarAlunoPorCpf(String cpf) {
        if (cpf == null) return null;

        for (Aluno a : alunos) {
            if (cpf.equals(a.getCpf())) {
                return a;
            }
        }
        return null;
    }

    public Professor buscarProfessorPorCpf(String cpf) {
        if (cpf == null) return null;

        for (Professor p : professores) {
            if (cpf.equals(p.getCpf())) {
                return p;
            }
        }
        return null;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public List<Professor> getProfessores() {
        return professores;
    }
}

