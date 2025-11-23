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

    
    public Fatura gerarNovaFatura(Aluno aluno) {
        LocalDate dataVencimento = LocalDate.now().plusDays(aluno.getPlano().getDuracao_em_dias());
        Fatura novaFatura = new Fatura(aluno, aluno.getPlano(), dataVencimento);
        faturas.add(novaFatura);
        aluno.adicionarFatura(novaFatura); 
        return novaFatura;
    }
    
    public boolean registrarPagamento(Fatura fatura) {
        return fatura.processarPagamento();
    }
    
    public List<Fatura> getFaturas() {
        for(Fatura f : faturas) {
            f.verificarAtraso();
        }
        return faturas;
    }
}
