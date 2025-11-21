package Cadastro;
// FitLife Academia/src/RegistroTreino.java

import java.time.LocalDate;

public class RegistroTreino {
    private LocalDate data;
    private Modalidade modalidade;
    private String detalhesDoTreino; // Ex: Exercícios, séries, repetições, carga
    private String progressoObservado; // Ex: Aumento de peso, melhora na performance, notas do professor

    public RegistroTreino(Modalidade modalidade, String detalhesDoTreino, String progressoObservado) {
        this.data = LocalDate.now(); // Define a data automaticamente
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