public class Academia {
    private Aluno alunos[];
    private Professor professores[];

    public void VerHistoricoDeTreinos(){
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
