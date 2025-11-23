package Menu;

import Cadastro.CadastroAcademico;
import Cadastro.Professor;
import javax.swing.*;
import java.awt.*;

public class ProfessorDashboardPanel extends JPanel {
    private FitLifeApp app;
    private JLabel lblInfo;

    public ProfessorDashboardPanel(FitLifeApp app, CadastroAcademico cadastro) {
        this.app = app;
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Área do Professor", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        lblInfo = new JLabel("", JLabel.CENTER);
        lblInfo.setVerticalAlignment(SwingConstants.TOP);
        add(lblInfo, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSair = new JButton("Sair");
        actions.add(btnSair);
        add(actions, BorderLayout.SOUTH);

        btnSair.addActionListener(e -> app.showScreen("login"));
    }

    public void refresh() {
        Professor p = app.getCurrentProfessor();
        if (p == null) {
            lblInfo.setText("Nenhum professor logado.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(p.getNome()).append("<br>");
        sb.append("CPF: ").append(p.getCpf()).append("<br>");
        sb.append("Especialidades: ");
        if (p.getTiposExercicio() != null && !p.getTiposExercicio().isEmpty()) {
            for (int i=0;i<p.getTiposExercicio().size();i++) {
                sb.append(p.getTiposExercicio().get(i));
                if (i < p.getTiposExercicio().size()-1) sb.append(", ");
            }
        } else sb.append("(nenhuma)");
        sb.append("<br>Horários: ");
        if (p.getHorarios() != null && !p.getHorarios().isEmpty()) {
            for (int i=0;i<p.getHorarios().size();i++) {
                sb.append(p.getHorarios().get(i));
                if (i < p.getHorarios().size()-1) sb.append(", ");
            }
        } else sb.append("(nenhum)");

        lblInfo.setText("<html>" + sb.toString() + "</html>");
    }
}
