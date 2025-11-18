import Planos.Plano;
import java.util.ArrayList; // Importe ArrayList
import java.util.List; // Importe List

public class Aluno extends Pessoa {
    private Plano plano;
    private int frequencia = 0;
    private Modalidade modalidades[];

    private static final int MAX_MODALIDADES = 10;
    
    // NOVO: Histórico de Treinos
    private List<RegistroTreino> historicoTreinos;

    public Aluno(String nome, String cpf, Plano plano) {
        super(nome, cpf);
        this.plano = plano;
        this.modalidades = new Modalidade[MAX_MODALIDADES];
        this.historicoTreinos = new ArrayList<>();
    }

    @Override
    public String getTipo() {
        return plano.eVIP() ? "ALUNO VIP" : "ALUNO";
    }

    public void inscreverEmModalidade(Modalidade modalidade) {

        if (modalidade == null)
            return;

        for (int i = 0; i < modalidades.length; i++) {
            if (modalidades[i] == null) {
                modalidades[i] = modalidade;
                return;
            }
        }

        System.out.println("Não foi possível inscrever: limite de modalidades atingido.");
    }

    // NOVO: Adicionar um novo registro de treino
    public void adicionarRegistroTreino(RegistroTreino registro) {
        this.historicoTreinos.add(registro);
        this.frequencia++; // Incrementa a frequência a cada novo registro
    }

    public void alertaDeFrequenciaBaixa() {
        int duracao = plano.getDuracao_em_dias();

        if (duracao <= 0) {
            System.out.println("Plano com duração inválida.");
            return;
        }

        double media = (double) frequencia / duracao;

        if (media <= 0.5) {
            System.out.println("Frequência Baixa!\nBora Treinar!");
        }
    }

    // NOVO: Getter para o histórico de treinos
    public List<RegistroTreino> getHistoricoTreinos() {
        return historicoTreinos;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public int getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }

    public Modalidade[] getModalidades() {
        return modalidades;
    }

    public boolean isVip() {
        return plano.eVIP();
    }

}