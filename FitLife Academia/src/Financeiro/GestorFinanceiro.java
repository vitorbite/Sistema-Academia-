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
        return gerarNovaFatura(aluno, false);
    }

    /**
     * Gera nova fatura para um aluno.
     * Se adminManual for true, a nova fatura terá vencimento 1 mês após a última fatura do aluno (ou daqui a 1 mês se não houver).
     * Se adminManual for false, o vencimento é baseado na duração do plano (em dias).
     */
    public Fatura gerarNovaFatura(Aluno aluno, boolean adminManual) {
        if (aluno == null || aluno.getPlano() == null) {
            throw new IllegalArgumentException("Aluno ou plano inválido para geração de fatura.");
        }

        LocalDate dataVencimento;
        if (adminManual) {
            // procura última fatura do aluno
            Fatura ultima = null;
            for (int i = faturas.size() - 1; i >= 0; i--) {
                Fatura f = faturas.get(i);
                if (f.getAluno() != null && f.getAluno().getCpf().equals(aluno.getCpf())) {
                    ultima = f;
                    break;
                }
            }
            if (ultima != null && ultima.getDataVencimento() != null) {
                dataVencimento = ultima.getDataVencimento().plusMonths(1);
            } else {
                dataVencimento = LocalDate.now().plusMonths(1);
            }
        } else {
            // Vencimento baseado na duração do plano (ex: 30 dias para Mensal, 365 para Anual)
            dataVencimento = LocalDate.now().plusDays(aluno.getPlano().getDuracao_em_dias());
        }

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