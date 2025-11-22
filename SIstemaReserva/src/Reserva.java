import java.time.LocalDateTime;

public class Reserva {
    private int idEquip;
    private String cpfAluno;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public Reserva(int idEquip, String cpfAluno, LocalDateTime inicio, LocalDateTime fim) {
        this.idEquip = idEquip;
        this.cpfAluno = cpfAluno;
        this.inicio = inicio;
        this.fim = fim;
    }

    public int getIdEquip() {
        return idEquip;
    }

    public String getCpfAluno() {
        return cpfAluno;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public boolean conflita(Reserva r) {
        if (r == null) return false;
        if (r.idEquip != this.idEquip) return false;
        return !(this.fim.isBefore(r.inicio) || this.inicio.isAfter(r.fim));
    }

    public String toString() {
        return "Equipamento " 
        + idEquip 
        + " reservado por " 
        + cpfAluno 
        + " de " 
        + inicio 
        + " até " 
        + fim;
    }
}
