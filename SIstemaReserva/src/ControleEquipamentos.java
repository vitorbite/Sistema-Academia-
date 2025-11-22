import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControleEquipamentos {

    private List<Equipamento> equipamentos = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();

    public Equipamento addEquipamento(String nome, String desc) {
        Equipamento e = new Equipamento(nome, desc);
        equipamentos.add(e);
        return e;
    }

    public boolean removerEquipamento(int id) {
        for (Reserva r : reservas) {
            if (r.getIdEquip() == id && r.getFim().isAfter(LocalDateTime.now())) {
                return false;
            }
        }
        for (int i = 0; i < equipamentos.size(); i++) {
            if (equipamentos.get(i).getId() == id) {
                equipamentos.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public Equipamento getEquip(int id) {
        for (Equipamento e : equipamentos) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    public boolean reservar(int idEquip, String cpf, LocalDateTime ini, LocalDateTime fim) {
        if (ini == null || fim == null || !fim.isAfter(ini)) return false;

        Equipamento e = getEquip(idEquip);
        if (e == null || !e.isDisponivel()) return false;

        Reserva nova = new Reserva(idEquip, cpf, ini, fim);

        for (Reserva r : reservas) {
            if (r.conflita(nova)) return false;
        }

        reservas.add(nova);
        return true;
    }

    public boolean cancelar(int idEquip, String cpf, LocalDateTime ini, LocalDateTime fim) {
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getIdEquip() == idEquip && r.getCpfAluno().equals(cpf)
                    && r.getInicio().equals(ini) && r.getFim().equals(fim)) {
                reservas.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public List<Reserva> getReservasAluno(String cpf) {
        List<Reserva> lista = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getCpfAluno().equals(cpf)) lista.add(r);
        }
        return lista;
    }

    public List<Reserva> getReservasEquip(int idEquip) {
        List<Reserva> lista = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getIdEquip() == idEquip) lista.add(r);
        }
        return lista;
    }

    public boolean alunoExiste(CadastroAcademico cad, String cpf) {
        for (Aluno a : cad.getAlunos()) {
            if (a.getCpf().equals(cpf)) return true;
        }
        return false;
    }
}
