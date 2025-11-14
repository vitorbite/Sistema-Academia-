import Planos.Plano;
import java.util.ArrayList; // Importe ArrayList
import java.util.List; // Importe List

public class Aluno {
    private String nome;
    private Plano plano;  // plano.eVIP() para receber true ou false
    private int frequencia = 0; // Contagem de visitas (pode ser atualizada)
    private Modalidade modalidades[]; // Lista de modalidades inscritas
    
    // NOVO: Histórico de Treinos
    private List<RegistroTreino> historicoTreinos;

    public Aluno(String nome, Plano plano){
        this.nome = nome;
        this.plano = plano;
        this.historicoTreinos = new ArrayList<>(); // Inicializa a lista
    }

    public void inscreverEmModalidade(Modalidade modalidade){
        // adicionar modalidade em uma lista
    }
    
    // NOVO: Adicionar um novo registro de treino
    public void adicionarRegistroTreino(RegistroTreino registro) {
        this.historicoTreinos.add(registro);
        this.frequencia++; // Incrementa a frequência a cada novo registro
    }

    public void alertaDeFrequenciaBaixa(){
        if (frequencia / plano.getDuracao_em_dias() <= 0.5) {
            System.out.println("Frequência Baixa!\n Bora Treinar!");
        }
    }
    
    // NOVO: Getter para o histórico de treinos
    public List<RegistroTreino> getHistoricoTreinos() {
        return historicoTreinos;
    }
    
    //adicionar Getters e Setters necessários
    // ... (Outros getters e setters aqui, incluindo getNome())
    public String getNome() {
        return nome;
      }
    }