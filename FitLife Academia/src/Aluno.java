import Planos.Plano;
public class Aluno {
    private String nome;
    private Plano plano;  // plano.eVIP() para receber true ou false
    private int frequencia = 0;
    private Modalidade modalidades[];

    public Aluno(String nome, Plano plano){
        this.nome = nome;
        this.plano = plano;
    }

    public void inscreverEmModalidade(Modalidade modalidade){
        // adicionar modalidade em uma lista
    }

    public void alertaDeFrequenciaBaixa(){
        if (frequencia / plano.getDuracao_em_dias() <= 0.5) {
            System.out.println("Frequência Baixa!\n Bora Treinar!");
        }
    }
    //adicionar Getters e Setters necessários

}
