package Menu.Financeiro;

import Cadastro.Aluno;
import Planos.Plano;
import java.time.LocalDate;

public class Fatura {
    private Aluno aluno;
    private Plano planoReferencia;
    private double valor;
    private LocalDate dataEmissao;
    private LocalDate dataVencimento;
    private StatusPagamento status;
    private LocalDate dataPagamento;

    public Fatura(Aluno aluno, Plano plano, LocalDate dataVencimento) {
        this.aluno = aluno;
        this.planoReferencia = plano;
        this.valor = plano.getValor(); // Pega o valor do plano
        this.dataEmissao = LocalDate.now();
        this.dataVencimento = dataVencimento;
        this.status = StatusPagamento.PENDENTE;
    }
    
    // Processa o pagamento e atualiza o status
    public boolean processarPagamento() {
        if (this.status == StatusPagamento.PAGO) {
            System.out.println("Fatura já paga.");
            return false;
        }
        
        this.status = StatusPagamento.PAGO;
        this.dataPagamento = LocalDate.now();
        return true;
    }

    // Verifica se a fatura está atrasada
    public void verificarAtraso() {
        if (this.status == StatusPagamento.PENDENTE && LocalDate.now().isAfter(dataVencimento)) {
            this.status = StatusPagamento.ATRASADO;
        }
    }

    // Getters
    public Aluno getAluno() { return aluno; }
    public double getValor() { return valor; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public StatusPagamento getStatus() { return status; }
    public LocalDate getDataPagamento() { return dataPagamento; }

    @Override
    public String toString() {
        // Exemplo: Aluno (CPF), Valor, Vencimento, Status.
        return String.format("%s (CPF: %s) | R$ %.2f | Vencimento: %s | Status: %s",
                aluno.getNome(), aluno.getCpf(), valor, dataVencimento, status);
    }
}