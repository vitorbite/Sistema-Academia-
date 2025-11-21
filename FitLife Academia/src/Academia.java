import java.util.HashMap;

import Cadastro.Aluno;
import Cadastro.CadastroAcademico;
import Cadastro.Professor;
import Cadastro.RegistroTreino;

public class Academia {
    private HashMap<String, String> alunos = new HashMap<>();
    private Professor professores[];
    CadastroAcademico cadastroAcademico = new CadastroAcademico();
    
    public void VerHistoricoDeTreinos(Aluno aluno){
        
        System.out.println("--- Histórico de Treinos de " + aluno.getNome() + " ---");
            if (aluno.getHistoricoTreinos().isEmpty()) {
            System.out.println("Nenhum registro de treino encontrado.");
            return;
        }

            for (RegistroTreino registro : aluno.getHistoricoTreinos()) {
        System.out.println("----------------------------------------");
        System.out.println(registro.toString());
        }
        System.out.println("----------------------------------------");
    }
    
}
