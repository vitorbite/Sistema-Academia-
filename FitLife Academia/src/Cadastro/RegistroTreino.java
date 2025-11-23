package Cadastro;
import java.time.LocalDate;
import Modalidades.Modalidade;

public class RegistroTreino {
    private LocalDate data;
    private Modalidade modalidade;
    private String detalhesDoTreino; 
    private String progressoObservado; 

    public RegistroTreino(Modalidade modalidade, String detalhesDoTreino, String progressoObservado) {
        this.data = LocalDate.now();
        this.modalidade = modalidade;
        this.detalhesDoTreino = detalhesDoTreino;
        this.progressoObservado = progressoObservado;
    }

    public LocalDate getData() {
        return data;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public String getDetalhesDoTreino() {
        return detalhesDoTreino;
    }

    public String getProgressoObservado() {
        return progressoObservado;
    }

    @Override
    public String toString() {
        return "Data: " + data + 
               " | Modalidade: " + modalidade.getClass().getSimpleName() + 
               "\nDetalhes: " + detalhesDoTreino + 
               "\nProgresso: " + progressoObservado;
    }
}
