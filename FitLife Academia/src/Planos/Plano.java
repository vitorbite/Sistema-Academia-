package Planos;

public class Plano {
    protected double valor;
    protected int duracao_em_dias;
    protected boolean VIP = false;

    public boolean eVIP() {
        return this.VIP;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public int getDuracao_em_dias() {
        return duracao_em_dias;
    }
    public void setDuracao_em_dias(int duracao_em_dias) {
        this.duracao_em_dias = duracao_em_dias;
    }

}