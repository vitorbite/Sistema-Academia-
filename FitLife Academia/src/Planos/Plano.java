package Planos;

public class Plano {
    protected double valor;
    protected int duracao_em_dias;
    protected boolean VIP = false;

    public boolean eVIP() {
        return this.VIP;
    }

}