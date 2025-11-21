package Cadastro;
import java.util.ArrayList;
import java.util.List;

public class Professor extends Pessoa {

    private List<String> tiposExercicio;
    private List<String> horarios;

    public Professor(String nome, String cpf) {
        super(nome, cpf);
        this.tiposExercicio = new ArrayList<>();
        this.horarios = new ArrayList<>();
    }

    @Override
    public String getTipo() {
        return "professor";
    }

    public void adicionarTipoExercicio(String tipo) {
        if (tipo != null && !tipo.isEmpty()) {
            tiposExercicio.add(tipo);
        }
    }

    public void adicionarHorario(String horario) {
        if (horario != null && !horario.isEmpty()) {
            horarios.add(horario);
        }
    }

    public List<String> getTiposExercicio() {
        return tiposExercicio;
    }

    public List<String> getHorarios() {
        return horarios;
    }
}
