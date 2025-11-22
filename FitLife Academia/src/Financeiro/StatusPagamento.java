package Financeiro;

public enum StatusPagamento {
    PENDENTE, // Fatura gerada, mas ainda não paga.
    PAGO,     // Fatura liquidada.
    ATRASADO; // Fatura vencida.
}
