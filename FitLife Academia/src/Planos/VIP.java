package Planos;

public class VIP extends Plano{
    private boolean VIP = true;
    
    public VIP(){
        this.valor = 100.50;
    }
    
    public boolean eVIP(){
        return VIP;
    }


}
