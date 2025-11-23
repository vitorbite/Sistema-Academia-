package Menu;

import java.math.BigDecimal;

public class SegurancaCartao {
        public static String mascararCartao(String numeroCartao) {
            return "xxxx xxxx xxxx " + numeroCartao.substring(12);
        }

        public static boolean validarDadosPagamento(PagamentoRequest request) {
        return request != null &&
                request.getValor() != null &&
                request.getValor().compareTo(BigDecimal.ZERO) > 0;
    }
}
