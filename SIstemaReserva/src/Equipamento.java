public class Equipamento {
    private static int cont = 1;

    private int id;
    private String nome;
    private String desc;
    private boolean disponivel = true;

    public Equipamento(String nome, String desc) {
        this.id = cont++;
        this.nome = nome;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDisponivel(boolean d) {
        this.disponivel = d;
    }

    public String toString() {
        return id 
        + " - " 
        + nome 
        + " (" + desc + ")";
    }
}
