package Financeiro;

import Cadastro.Aluno;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorFinanceiro {
    private static GestorFinanceiro instance;
    private List<Fatura> faturas;

    private GestorFinanceiro() {
        this.faturas = new ArrayList<>();
    }

    public static GestorFinanceiro getInstance() {
        if (instance == null) {
            instance = new GestorFinanceiro();
        }
        return instance;
    }

    // Gera uma nova fatura com base no plano e duração do aluno
    public Fatura gerarNovaFatura(Aluno aluno) {
        // Vencimento baseado na duração do plano (ex: 30 dias para Mensal, 365 para Anual)
        LocalDate dataVencimento = LocalDate.now().plusDays(aluno.getPlano().getDuracao_em_dias());
        Fatura novaFatura = new Fatura(aluno, aluno.getPlano(), dataVencimento);
        faturas.add(novaFatura);
        aluno.adicionarFatura(novaFatura); 
        return novaFatura;
    }

    // Registra o pagamento de uma fatura
    public boolean registrarPagamento(Fatura fatura) {
        // **Aqui seria o ponto de integração com um serviço de pagamento real.**
        return fatura.processarPagamento();
    }
    
    // Busca todas as faturas da academia
    public List<Fatura> getFaturas() {
        // Antes de retornar, verifica o status de atraso de todas
        for(Fatura f : faturas) {
            f.verificarAtraso();
        }
        return faturas;
    }
}