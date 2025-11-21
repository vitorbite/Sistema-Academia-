package Menu;

public class RegistroTreino {
    private String descricao;
    private String data;

    public RegistroTreino(String descricao, String data) {
        this.descricao = descricao;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data: " + data + " - " + descricao;
    }
}
