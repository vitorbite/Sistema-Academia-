package Menu;

import Cadastro.CadastroAcademico;
import Cadastro.Aluno;
import javax.swing.*;
import java.awt.*;

public class StudentDashboardPanel extends JPanel {
    private FitLifeApp app;
    private CadastroAcademico cadastro;
    private JLabel lblInfo;

    public StudentDashboardPanel(FitLifeApp app, CadastroAcademico cadastro) {
        this.app = app;
        this.cadastro = cadastro;
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Área do Aluno", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        lblInfo = new JLabel("", JLabel.CENTER);
        lblInfo.setVerticalAlignment(SwingConstants.TOP);
        add(lblInfo, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnEditar = new JButton("Editar Cadastro");
        JButton btnSair = new JButton("Sair");
        actions.add(btnEditar);
        actions.add(btnSair);
        add(actions, BorderLayout.SOUTH);

        btnEditar.addActionListener(e -> app.showScreen("alunoForm"));
        btnSair.addActionListener(e -> app.showScreen("login"));
    }

    public void refresh() {
        Aluno a = app.getCurrentAluno();
        if (a == null) {
            lblInfo.setText("Nenhum aluno logado.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(a.getNome()).append("<br>");
        sb.append("CPF: ").append(a.getCpf()).append("<br>");
        sb.append("Plano: ").append(a.getPlano() != null ? a.getPlano().getClass().getSimpleName() : "(nenhum)").append("<br>");
        sb.append("Modalidades: ");
        if (a.getModalidades() != null && !a.getModalidades().isEmpty()) {
            for (int i=0;i<a.getModalidades().size();i++) {
                sb.append(a.getModalidades().get(i).getClass().getSimpleName());
                if (i < a.getModalidades().size()-1) sb.append(", ");
            }
        } else sb.append("(nenhuma)");
        lblInfo.setText("<html>" + sb.toString() + "</html>");
    }
}
