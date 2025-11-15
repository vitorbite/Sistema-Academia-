import Planos.Mensal;
import Planos.Plano;
import Planos.VIP;

public class Main{
    public static void main(String[] args) {
        
        //Planos e Modalidades
        Plano planoMensal = new Mensal();
        Modalidade musculacao = new Musculacao();
        Modalidade pilates = new Pilates();

        //Criar Aluno
        Aluno aluno1 = new Aluno("Arthur Brito", planoMensal);
        
        //Criar e Adicionar Registros de Treino
        RegistroTreino reg1 = new RegistroTreino(
            musculacao, 
            "Treino A - Peito e Tríceps: Supino 3x10 ( 10 kg), Tríceps Pulley 3x12 (20kg)", 
            "Aumento de 5kg na carga do supino."
        );
        
        RegistroTreino reg2 = new RegistroTreino(
            pilates, 
            "Treino B - Pilátes, Foco em estabilidade do core.", 
            "Melhora na execução do exercício 'Hundred'."
        );

        aluno1.adicionarRegistroTreino(reg1);
        aluno1.adicionarRegistroTreino(reg2);
        
        //Utilizar a Academia para ver o Histórico
        Academia fitlife = new Academia();
        fitlife.VerHistoricoDeTreinos(aluno1);
        
        //Testar alerta de frequência
        aluno1.alertaDeFrequenciaBaixa();
    }
}